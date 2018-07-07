package com.example.steven.happybirthday2you;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.Adapter;
import java.util.Arrays;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);


        Spinner dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
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
                    // TODO: Store name in string here.
                }
            }
        });



        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstRun = true;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (parentView.getItemAtPosition(position).toString().equals("Select Month") && !firstRun) {
                    ((TextView)monthSpinner.getSelectedView()).setError("You must select a month");
                } else {
                    firstRun = false;
                    // TODO: Store month in string here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
/*
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstRunDate = true;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (parentView.getItemAtPosition(position).toString().equals("Enter Date Here") && !firstRunDate) {
                    //((TextView)dateSpinner.getSelectedView()).setError("You must select a date");
                    // TODO: fix this
                } else {
                    firstRunDate = false;
                    // TODO: Store month in string here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        */
    }
}
