/*************************************************************
 * Author:          Silverio Reyes
 * Filename:        MainActivity.java
 * Organization:    Oregon Institute of Technology
 * Class:           CST238 GUI
 *
 * Date Created:    6/26/18 - Mitch Besser-Laber Created .gitignore,
 *                            README.md, and REPORT.md files
 *
 * Date Modified:   6/29/18 - Created project and
 *                            added dependencies
 *
 *                  7/01/18 - Created UI layout and static
 *                            array adapter that generates
 *                            a drop down feature for displaying
 *                            months and days to user.
 *
 *                  7/02/18 - Created method to validate form
 *                            registration
 *
 *                            Created method to handle empty fields
 *
 *                            Created method that handles generating
 *                            the days within a given month when
 *                            selected by the user
 *
 *                            Created Helper function for
 *                            generating days in a list view
 *
 *                            Test Driven development: as of
 *                            7/02/18 SRS displays unnecessary
 *                            test output validation.
 *                            This will be removed upon completion
 *                            of SRS
 *
 *                  7/05/18 - Began logic for File I/0 for writing
 *
 *                            Began logic and brainstorming for
 *                            serializing object and writing
 *                            to file.
 *
 *                  7/09/18 - Serialized and De-serialized
 *                            birthday entries using various
 *                            methods.
 *
 *                            Implemented File I/O for reading
 *                            and writing Json to persistent
 *                            storage using the devices internal
 *                            storage.
 *
 *                  7/10/18 - Implemented methods to handle
 *                            birthday entries passed as an
 *                            intent to the display message
 *                            activity.
 *
 *                            Designed and implemented the
 *                            display message activity.
 *
 *                            Removed unnecessary data entry
 *                            as per SRS01 Spec
 *
 *                            Removed unnecessary code fragments
 *
 *                            Recommendation: Refactor and clean
 *                            code up. Investigate patterns that
 *                            are more efficient for Serializing
 *                            and De-serializing array objects
 *                            to json format.
 **************************************************************/
package com.sreyesnoxgraphics.happybday2you;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variables instantiated to handle UI resources
    EditText etFirstname;
    Button btnRegister;
    Spinner spBirthMonth, spBirthMonthDays;

    private String monthSelection, daySelection;

    // Variables to create file path directory for birthday entries
    protected ContextWrapper cw;
    private File directory;
    private File directoryPath;
    private ColorStateList defaultThemeColor;

    // Shared preferences
    public static final String sharedPrefs = "BirthdayPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables for the UI elements and connects them to the XML layout
        etFirstname = (EditText)findViewById(R.id.etFirstname);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        spBirthMonth = (Spinner) findViewById(R.id.spMonths_staticSpinner);
        spBirthMonthDays = (Spinner) findViewById(R.id.spDaysInMonth_staticSpinner);

        // Get the default theme text color for text views
        defaultThemeColor = etFirstname.getTextColors();

        // Disable the register button until all fields are not empty
        btnRegister.setEnabled(false);

        // directoryPath will equal the directory and file name
        cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir("birthdayEntriesDir", Context.MODE_APPEND);
        directoryPath = new File(directory, "entries.txt");

        etFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isFieldsEmpty();
                etFirstname.setTextColor(defaultThemeColor);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Create an array adapter that will use the string array resources for birth Months
        // It will also create from existing resource from design layout
        ArrayAdapter<CharSequence> birthMonthAdapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);

        // Display the list of choices in drop down column
        birthMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spBirthMonth.setAdapter(birthMonthAdapter);

        GenerateBirthMonthDaysArrayAdapter(R.array.months31Days_array);

        // Interface implementation call
        BirthMonthSelectionHandler();

        // Registration call
        ValidateFormRegistration();
    }

    private boolean FileExist(String parentPath) {
        File file = new File(parentPath, "entries.txt");
        return file.toString().equals(directoryPath.toString());
    }

    //*********************************************************************
    // * Purpose: This function handles a click on the "Register" button
    // * and stores the entry into persistent storage.
    // *
    // * Pre-condition: User does not register (click not handled).
    // *
    // * Post-condition: User is registered (click handled).
    // ************************************************************************/
    private void ValidateFormRegistration()
    {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean passes = true;

                if (!etFirstname.getText().toString().matches("[\\w]+")) {
                    Toast.makeText(MainActivity.this, "First name must be only letters and/or numbers",
                            Toast.LENGTH_LONG).show();
                    etFirstname.setTextColor(Color.RED);
                    passes = false;
                }

                if (monthSelection.equals("Months")) {
                    Toast.makeText(MainActivity.this, "Please select a month",
                            Toast.LENGTH_LONG).show();
                    // Set to red background. Use text view of xml file and not the actual spinner
                    ((TextView)spBirthMonth.getSelectedView()).setTextColor(Color.RED);
                    passes = false;
                }
                if (daySelection.equals("Days")) {
                    Toast.makeText(MainActivity.this, "Please select a number for days",
                            Toast.LENGTH_LONG).show();
                    ((TextView)spBirthMonthDays.getSelectedView()).setTextColor(Color.RED);
                    passes = false;
                }
                if (monthSelection.equals("February"))
                {
                    if (daySelection.equals("Days")){
                        Toast.makeText(MainActivity.this, "Please select a number for days",
                                Toast.LENGTH_LONG).show();
                        ((TextView)spBirthMonthDays.getSelectedView()).setTextColor(Color.RED);
                        passes = false;
                    }
                   else if (Integer.parseInt(daySelection) > 29)
                    {
                        Toast.makeText(MainActivity.this, "Please select a valid number. " + monthSelection + " has only 29 days",
                                Toast.LENGTH_LONG).show();
                        ((TextView)spBirthMonthDays.getSelectedView()).setTextColor(Color.RED);
                        passes = false;
                    }
                }
                if (monthSelection.equals("April") || monthSelection.equals("June") || monthSelection.equals("September") || monthSelection.equals("November"))
                {
                    if (daySelection.equals("Days")){
                        Toast.makeText(MainActivity.this, "Please select a number for days",
                                Toast.LENGTH_LONG).show();
                        ((TextView)spBirthMonthDays.getSelectedView()).setTextColor(Color.RED);
                        passes = false;
                    }

                    else if (Integer.parseInt(daySelection) > 30)
                    {
                        Toast.makeText(MainActivity.this, "Please select a valid number. " + monthSelection + " has only 30 days",
                                Toast.LENGTH_LONG).show();
                        ((TextView)spBirthMonthDays.getSelectedView()).setTextColor(Color.RED);
                        passes = false;
                    }
                }

                if (passes) {
                    // check if directory file path exist
                    if (FileExist(directory.toString())) {
                        // if file exist good!, if it doesnt we already created one!, this can just be a check
                        //Toast.makeText(MainActivity.this, "File Exist!", Toast.LENGTH_SHORT).show();

                        // Check if file is blank
                        File file = new File(directoryPath.toString());
                        if (file.length() == 0) {

                            // Output stream access outside of try catch
                            FileOutputStream outputStream = null;
                            try {
                                // Begin serialization
                                JSONObject jsonObject = new JSONObject();
                                JSONObject innerObject = new JSONObject();
                                JSONArray bEntries = new JSONArray();
                                innerObject.put("Name", etFirstname.getText().toString());
                                innerObject.put("BirthMonth", monthSelection);
                                innerObject.put("BirthDay", daySelection);

                                // Add birthday entry to array and wrap it as a json array with objects
                                bEntries.put(innerObject);
                                jsonObject.put("BirthdayEntries", bEntries);

                                // Open and write serialized content to file, then close file.
                                outputStream = new FileOutputStream(directoryPath);
                                outputStream.write(jsonObject.toString().getBytes());
                                outputStream.close();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            // Initialize count for bday entries and used shared preference for easy access.
                            int numberOfBirthdayEntries = 0;
                            SharedPreferences.Editor editor = getSharedPreferences(sharedPrefs, MODE_PRIVATE).edit();
                            editor.putInt("Count", numberOfBirthdayEntries);
                            editor.apply();

                            HandleEntriesCountToNextIntent();
                        }
                        else {
                            // Resources outside of try catch for accessibility
                            FileInputStream inputStream = null;
                            String jsonStr = null;

                            HashMap<String, List<String>> parsedData = new HashMap<String, List<String>>();
                            List<String> Names = new ArrayList<>();
                            List<String> Birthmonths = new ArrayList<>();
                            List<String> Birthdays = new ArrayList<>();
                            
                            try{
                                // Open file input stream to directory path
                                inputStream = new FileInputStream(directoryPath);

                                // Channel used for reading, writing, mapping, and manipulating a file.
                                FileChannel fc = inputStream.getChannel();

                                // This maps a region of the channels file directly to memory
                                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                                // Charset is a named mapping between characters and bytes.
                                // This is where we get our json content from file
                                jsonStr = Charset.defaultCharset().decode(bb).toString();
                                inputStream.close();

                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                            try {
                                // Pass json str to json object and then create a json array and pull out the array from content file.
                                JSONObject jsonObj = new JSONObject(jsonStr);
                                JSONArray data = jsonObj.getJSONArray("BirthdayEntries");

                                // Iterate through the array and parse the values in a hash map
                                for (int i = 0; i < data.length(); i++){
                                    JSONObject obj = data.getJSONObject(i);

                                    Names.add(obj.getString("Name"));
                                    Birthmonths.add(obj.getString("BirthMonth"));
                                    Birthdays.add(obj.getString("BirthDay"));

                                    parsedData.put("Name", Names);
                                    parsedData.put("BirthMonth", Birthmonths);
                                    parsedData.put("BirthDay", Birthdays);
                                }

                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            // Check if there is a match
                            boolean matchFound = false;
                            for (int i = 0; i < Birthmonths.size(); i++){
                                String month = Birthmonths.get(i);
                                String day = Birthdays.get(i);
                                String name = Names.get(i);
                                if (month.equals(monthSelection) && day.equals(daySelection)){
                                    //Toast.makeText(MainActivity.this, "We have a bday match!", Toast.LENGTH_SHORT).show();

                                    HandleEntryMatchToNextIntent(name);
                                    matchFound = true;
                                    break;
                                }
                            }

                            if (!matchFound) {
                                //Toast.makeText(MainActivity.this, "Add to map!", Toast.LENGTH_SHORT).show();
                                // If there isn't a birthday match, then Add the birthday entry
                                Names.add(etFirstname.getText().toString());
                                Birthmonths.add(monthSelection);
                                Birthdays.add(daySelection);

                                parsedData.put("Name", Names);
                                parsedData.put("BirthMonth", Birthmonths);
                                parsedData.put("BirthDay", Birthdays);

                                // Resources outside of try catch for accessibility
                                JSONObject jsonObject = new JSONObject();
                                FileOutputStream outputStream = null;
                                JSONArray bEntries = new JSONArray();

                                // Iterate through the list arrays
                                for (int i = 0; i < Names.size(); i++) {
                                    try {
                                        // Begin serialization process
                                        JSONObject innerObject = new JSONObject();
                                        innerObject.put("Name", Names.get(i));
                                        innerObject.put("BirthMonth", Birthmonths.get(i));
                                        innerObject.put("BirthDay", Birthdays.get(i));

                                        // Wrap json array with key/value pare objects
                                        bEntries.put(innerObject);

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                try {
                                    // Wrap json array as a json object
                                    jsonObject.put("BirthdayEntries", bEntries);

                                    // Open and write serialized content to file, then close file.
                                    outputStream = new FileOutputStream(directoryPath);
                                    outputStream.write(jsonObject.toString().getBytes());
                                    outputStream.close();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                HandleEntriesCountToNextIntent();
                            }
                        }
                    }
                }
                // Start off with focus request
                etFirstname.requestFocus();
            }
        });
    }
    /**********************************************************************
     * Purpose: This method checks if the users registrations fields
     * are empty or not
     *
     * Pre-condition: Registration button not active.
     *
     * Post-condition: If fields are all populated, registration button is active
     * else registration button is not active.
     ************************************************************************/
    private void isFieldsEmpty() {
        if (!etFirstname.getText().toString().isEmpty()){
            btnRegister.setEnabled(true);
        }
        else {
            btnRegister.setEnabled(false);
        }
    }
    //*********************************************************************
    // * Purpose: This function handles in clearing the input fields
    // *          if the users decides to unwind the stack or close it out
    // *
    // * Pre-condition: Fields are either empty or loaded.
    // *
    // * Post-condition: Fields are clear from any previous known input
    // ************************************************************************/
    private void ClearInputFields(){
        etFirstname.getText().clear();
        spBirthMonth.setSelection(0);
        //spBirthMonthDays.setSelection(0);
    }
    //*********************************************************************
    // * Purpose: This function handles in clearing the input entries and
    // *          incrementing entry count which will be passed as an intent
    // *          to the display message activity.
    // *
    // * Pre-condition: Fields are not valid or no user submission for entry
    // *
    // * Post-condition: Update entry count, clear input fields and pass
    // *                 intent to display message activity.
    // ************************************************************************/
    private void HandleEntriesCountToNextIntent(){
        // Get resources from shared preferences
        SharedPreferences preferences = this.getSharedPreferences("BirthdayPreferences", Context.MODE_PRIVATE);
        int count = preferences.getInt("Count", 0);

        // Update entry count
        count++;

        // Edit and save entry count
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt("Count", count);
        editor.putBoolean("Flag", false);
        editor.apply();

        ClearInputFields();

        // Pass intent to next activity
        Intent EntriesCountIntent = new Intent(MainActivity.this, DisplayMessageActivity.class);
        startActivity(EntriesCountIntent);
    }

    //*********************************************************************
    // * Purpose: This function handles in clearing the input entries,
    // *          update the entry count, and send the values of
    // *          the users name who have a birthday match
    // *          that will be passed as an intent to the
    // *          display message activity.
    // *
    // * Pre-condition: Fields are not valid or no user submission for entry or
    // *                birthday match found.
    // *
    // * Post-condition: Update entry count, clear input fields, save values of
    // *                 the users name and pass
    // *                 intent to display message activity.
    // ************************************************************************/
    private void HandleEntryMatchToNextIntent(String name){

        // Get resources from shared preferences
        SharedPreferences preferences = this.getSharedPreferences("BirthdayPreferences", Context.MODE_PRIVATE);
        int count = preferences.getInt("Count", 0);

        // Update entry count
        count++;

        // Edit and save entry count
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt("Count", count);
        editor.putString("StoredName", name);
        editor.putString("CheckedName", etFirstname.getText().toString());
        editor.putBoolean("Flag", true);
        editor.apply();

        ClearInputFields();

        // Pass intent to next activity
        if (DeleteFile()) {
            Intent EntriesMatchCountIntent = new Intent(MainActivity.this, DisplayMessageActivity.class);
            startActivity(EntriesMatchCountIntent);
        }
    }

    //*********************************************************************
    // * Purpose: This function handles in deleting the birthday entries,
    // *          from persistent storage. Basically it nukes the file.
    // *
    // * Pre-condition: File not ready for deletion
    // *
    // * Post-condition: File deleted
    // ************************************************************************/
    private boolean DeleteFile(){
        File file = new File(directoryPath.toString());
       boolean deleted = false;

        deleted = file.delete();
        return deleted;
    }

    //*********************************************************************
    // * Purpose: This function handles the list of days within a given
    // *          month if the user happens to select a month first
    // *
    // * Pre-condition: Month is not handled
    // *
    // * Post-condition: User selects month (click handled).
    // ************************************************************************/
    private void BirthMonthSelectionHandler () {
        spBirthMonth.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

                // Save user selection
                monthSelection = (String)parent.getItemAtPosition(pos);

                // Reset to default background. Use text view of xml file and not the actual spinner
                ((TextView)spBirthMonth.getSelectedView()).setTextColor(Color.BLACK);

                //Toast.makeText(MainActivity.this, "The month you selected is " + monthSelection, Toast.LENGTH_SHORT).show();

                if (parent.getItemAtPosition(pos).equals("Months") && daySelection != null && (daySelection.equals("Days") ||
                        (Integer.parseInt(daySelection) >= 1 && Integer.parseInt(daySelection) <= 31))){
                    // Default to 31 days if user re-selects months during run time
                    // The professor might not like that you reset the days if user selects the default value month, if this is
                    // the case you can simply remove the date range
                    GenerateBirthMonthDaysArrayAdapter(R.array.months31Days_array);
                }
                if (parent.getItemAtPosition(pos).equals("February") && daySelection.equals("Days")){
                    //Toast.makeText(MainActivity.this, "This Month has 29 days", Toast.LENGTH_SHORT).show();
                        GenerateBirthMonthDaysArrayAdapter(R.array.months29Days_array);
                }
                else if ((parent.getItemAtPosition(pos).equals("April") || parent.getItemAtPosition(pos).equals("June") ||
                          parent.getItemAtPosition(pos).equals("September") || parent.getItemAtPosition(pos).equals("November")) &&
                          daySelection.equals("Days")) {
                    //Toast.makeText(MainActivity.this, "This Month has 30 days", Toast.LENGTH_SHORT).show();
                    GenerateBirthMonthDaysArrayAdapter(R.array.months30Days_array);
                }
                else if (((parent.getItemAtPosition(pos).equals("January") || parent.getItemAtPosition(pos).equals("March") ||
                          parent.getItemAtPosition(pos).equals("May") || parent.getItemAtPosition(pos).equals("July")) ||
                          parent.getItemAtPosition(pos).equals("August") || parent.getItemAtPosition(pos).equals("October") ||
                          parent.getItemAtPosition(pos).equals("December")) && daySelection.equals("Days")/* ||
                          (Integer.parseInt(daySelection) >= 1 && Integer.parseInt(daySelection) <= 30))*/){
                    //Toast.makeText(MainActivity.this, "This Month has 31 days", Toast.LENGTH_SHORT).show();
                    GenerateBirthMonthDaysArrayAdapter(R.array.months31Days_array);

                    // Need to be able to keep user selection if they have a number between 1 and 30
                    //spBirthMonthDays.setSelection(Integer.parseInt(daySelection));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                // Interface callback
            }
        });
    }

    //*********************************************************************
    // * Purpose: This function is used to generate a list of days
    // *
    // * Pre-condition: list days for month is not handled
    // *
    // * Post-condition: list populated with days (click handled).
    // ************************************************************************/
    private void GenerateBirthMonthDaysArrayAdapter(int monthsDays_arrayID)
    {
        // Create an array adapter that will use the string array resources for days within a given month
        // It will also create from existing resource from design layout
        ArrayAdapter<CharSequence> birthMonthDaysAdapter = ArrayAdapter.createFromResource(this, monthsDays_arrayID, android.R.layout.simple_spinner_item);

        // Display the list of choices in drop down column
        birthMonthDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spBirthMonthDays.setAdapter(birthMonthDaysAdapter);

        // Handle click listener event
        spBirthMonthDays.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                // Save user selection
                daySelection = (String)parent.getItemAtPosition(pos);

                // Reset to default background. Use text view of xml file and not the actual spinner
                ((TextView)spBirthMonthDays.getSelectedView()).setTextColor(Color.BLACK);

                //Toast.makeText(MainActivity.this, "The day you selected is " + daySelection, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                // Interface callback
            }
        });
    }
}
