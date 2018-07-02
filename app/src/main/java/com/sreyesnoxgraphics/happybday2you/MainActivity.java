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
 **************************************************************/
package com.sreyesnoxgraphics.happybday2you;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    EditText etFirstname, etLastname;
    Button btnRegister;
    Spinner spBirthMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables for the UI elements and connects them to the XML layout
        etFirstname = (EditText)findViewById(R.id.etFirstname);
        etLastname = (EditText)findViewById(R.id.etLastname);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        spBirthMonth = (Spinner) findViewById(R.id.spMonths_staticSpinner);

        // Create an array adapter that will use the string array resources for birth Months
        // It will also create from existing resource from design layout
        ArrayAdapter<CharSequence> birthMonthAdapter = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);

        // Display the list of choices in drop down column
        birthMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spBirthMonth.setAdapter(birthMonthAdapter);

        // Interface implementation call
        BirthMonthSelectionHandler();

        // Spinner for days
        Spinner spBirthMonthDays = (Spinner) findViewById(R.id.spDaysInMonth_staticSpinner);
        String[] monthsDays_array = new String[]{"28", "30", "31"};
        ArrayAdapter<String> birthMonthDaysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthsDays_array);
        spBirthMonthDays.setAdapter(birthMonthDaysAdapter);

        spBirthMonthDays.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.v("item",(String)adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void BirthMonthSelectionHandler () {
        spBirthMonth.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                if (parent.getItemAtPosition(pos).equals("February")){
                    Toast.makeText(MainActivity.this, "This Month has 28 days", Toast.LENGTH_SHORT).show();
                }
                else if (parent.getItemAtPosition(pos).equals("April") || parent.getItemAtPosition(pos).equals("June") ||
                         parent.getItemAtPosition(pos).equals("September") || parent.getItemAtPosition(pos).equals("November")) {
                    Toast.makeText(MainActivity.this, "This Month has 30 days", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "This Month has 31 days", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                // Interface callback
            }
        });
    }
}
