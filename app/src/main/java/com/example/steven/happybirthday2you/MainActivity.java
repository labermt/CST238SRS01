package com.example.steven.happybirthday2you;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String username;
    String usermonth;
    String userdate;
    String formError = "";
    //List<String> months30 = new ArrayList<String>();
    int usertotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);


        final Spinner dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        String[] dates = new String[]{"Enter Date Here","1","2","3","4","5","6","7","8","9","10","11","12",
                "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, dates);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter1);

        final EditText nameText = findViewById(R.id.editText);

        nameText.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nameText.getText().toString().length() <= 0) {
                    nameText.setError("You must enter a name");
                } else {
                    nameText.setError(null);
                    username = nameText.getText().toString();
                }
            }
        });



        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstRun = true;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (parentView.getItemAtPosition(position).toString().equals("Select Month") && !firstRun) {
                    monthSpinner.requestFocus();
                    ((TextView)monthSpinner.getSelectedView()).setError("You must select a month");
                } else {
                    firstRun = false;
                    usermonth = monthSpinner.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstRunDate = true;
            @Override
            public void onItemSelected(AdapterView<?> d_parentView, View d_selectedItemView, int d_Position, long d_id) {
                if (d_parentView.getItemAtPosition(d_Position).toString().equals("Enter Date Here") && !firstRunDate) {
                    dateSpinner.requestFocus();
                    ((TextView)dateSpinner.getSelectedView()).setError("You must select a date");
                } else {
                    firstRunDate = false;
                    userdate = dateSpinner.getItemAtPosition(d_Position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> d_parentView) {
            }
        });

        final Button confirmBtn = findViewById(R.id.enterBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ValidateForm())
                {
                    confirmBtn.setError(null);
                    confirmBtn.setBackgroundColor(Color.GREEN);
                }
                else
                {
                    confirmBtn.getBackground().clearColorFilter();
                    confirmBtn.requestFocus();
                    confirmBtn.setError(formError);
                }
            }
        });


    }

    private boolean ValidateForm(){

        // check name, month, and date here.
        if(username == null) {
            formError = "Please enter a name";
            return false;
        }
        if(usermonth == null || usermonth.equals("Select Month")) {
            formError = "Please enter a month";
            return false;
        }
        if(userdate == null || userdate.equals("Enter Date Here")) {
            formError = "Please enter a date";
            return false;
        }

        // check date and month for 30 day months
        if(Arrays.asList("Apr", "Jun", "Sep", "Nov").contains(usermonth) &&  (Integer.parseInt(userdate) > 30))
        {
            formError = usermonth + " " + userdate + "is invalid.";
            return false;
        }

        // check date and month for Feb
        if(usermonth.equals("Feb") &&  Integer.parseInt(userdate) > 29)
        {
            formError = usermonth + " " + userdate + "is invalid.";
            return false;
        }

        return true;
    }
}
