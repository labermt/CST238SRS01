/*************************************************************
 * Author:          Silverio Reyes
 * Filename:        MainActivity.java
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
 **************************************************************/
package com.sreyesnoxgraphics.happybday2you;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Variables instantiated to handle UI resources
    EditText etFirstname, etLastname;
    Button btnRegister;
    Spinner spBirthMonth, spBirthMonthDays;

    private String monthSelection, daySelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables for the UI elements and connects them to the XML layout
        etFirstname = (EditText)findViewById(R.id.etFirstname);
        etLastname = (EditText)findViewById(R.id.etLastname);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        spBirthMonth = (Spinner) findViewById(R.id.spMonths_staticSpinner);
        spBirthMonthDays = (Spinner) findViewById(R.id.spDaysInMonth_staticSpinner);

        // Get the default theme text color for text views
        final ColorStateList defaultThemeColor = etFirstname.getTextColors();


        // Disable the register button until all fields are not empty
        btnRegister.setEnabled(false);

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

        etLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isFieldsEmpty();
                etLastname.setTextColor(defaultThemeColor);
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

        // Interface implementation call
        BirthMonthSelectionHandler();

        // Registration call
        ValidateFormRegistration();
    }

    private void ValidateFormRegistration()
    {
        //*********************************************************************
        // * Purpose: This function handles a click on the "Register" button
        // * and sends the user data to the server to connect to the database in
        // * order to store user information.
        // *
        // * Pre-condition: User does not register (click not handled).
        // *
        // * Post-condition: User is registered (click handled).
        // ************************************************************************/
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
                if (!etLastname.getText().toString().matches("[\\w]+")) {
                    Toast.makeText(MainActivity.this, "Last name must be only letters and/or numbers",
                            Toast.LENGTH_LONG).show();
                    etLastname.setTextColor(Color.RED);
                    passes = false;
                }
                if (monthSelection.equals("Months")) {
                    Toast.makeText(MainActivity.this, "Please select a month",
                            Toast.LENGTH_LONG).show();
                    spBirthMonth.setBackgroundColor(Color.RED);
                    passes = false;
                }
                if (daySelection.equals("Days")) {
                    Toast.makeText(MainActivity.this, "Please select a number for days",
                            Toast.LENGTH_LONG).show();
                    spBirthMonthDays.setBackgroundColor(Color.RED);
                    passes = false;
                }
                if (passes) {
                    // Save values to persistent storage
                    // Pass intent
                    // Reset input fields
                    etFirstname.getText().clear();
                    etLastname.getText().clear();
                    spBirthMonth.setSelection(0);
                    spBirthMonthDays.setSelection(0);
                }
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
        if (!etFirstname.getText().toString().isEmpty() && !etLastname.getText().toString().isEmpty()){
            btnRegister.setEnabled(true);
        }
        else {
            btnRegister.setEnabled(false);
        }
    }

    private void BirthMonthSelectionHandler () {
        spBirthMonth.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                // Save user selection
                monthSelection = (String)parent.getItemAtPosition(pos);
                Toast.makeText(MainActivity.this, "The month you selected is " + monthSelection, Toast.LENGTH_SHORT).show();

                if (parent.getItemAtPosition(pos).equals("February")){
                    Toast.makeText(MainActivity.this, "This Month has 28 days", Toast.LENGTH_SHORT).show();
                    GenerateBirthMonthDaysArrayAdapter(R.array.months28Days_array);
                }
                else if (parent.getItemAtPosition(pos).equals("April") || parent.getItemAtPosition(pos).equals("June") ||
                         parent.getItemAtPosition(pos).equals("September") || parent.getItemAtPosition(pos).equals("November")) {
                    Toast.makeText(MainActivity.this, "This Month has 30 days", Toast.LENGTH_SHORT).show();
                    GenerateBirthMonthDaysArrayAdapter(R.array.months30Days_array);
                }
                else{
                    Toast.makeText(MainActivity.this, "This Month has 31 days", Toast.LENGTH_SHORT).show();
                    GenerateBirthMonthDaysArrayAdapter(R.array.months31Days_array);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                // Interface callback
            }
        });
    }

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
                Toast.makeText(MainActivity.this, "The day you selected is " + daySelection, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                // Interface callback
            }
        });
    }

}
