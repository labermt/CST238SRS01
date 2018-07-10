package com.example.steven.happybirthday2you;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String username;
    Set <String> users;
    String usermonth;
    String userdate;
    Set <String> birthdays;
    String formError = "";
    boolean match = false;
    int usertotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        usertotal = sharedPref.getInt("saved_total", 0);
        users = new HashSet<String>(sharedPref.getStringSet("saved_users", new HashSet<String>()));
        birthdays = new HashSet<String>(sharedPref.getStringSet("saved_birthdays", new HashSet<String>()));

        TextView totalText = findViewById(R.id.textView3);
        final String text = "Total entries: " + Integer.toString(usertotal);
        totalText.setText(text);


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
            public void onClick(View v) {
                if (ValidateForm()) {
                    confirmBtn.setError(null);
                    // Check for match! if so, reset
                    int index = 0;
                    String matchName;
                    for (String s : birthdays) {
                        if (s.equals(usermonth + userdate)) {
                            matchName = users.toArray()[index].toString();
                            match = true;
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Success!");
                            alertDialog.setMessage(username + " and " + matchName + " share the same birthday on: " + s + "! Number of entries: " + usertotal);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // clear things
                                            dialog.dismiss();
                                            editor.clear();
                                            editor.apply();
                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                        }
                                    });
                            alertDialog.show();
                        }
                        index++;
                    }
                    if (!match) {
                        // Save entry count
                        int newTotal = ++usertotal;
                        editor.putInt("saved_total", newTotal);
                        editor.commit();
                        // Save name
                        users.add(username);
                        Set<String> hat = new HashSet<String>(users);
                        editor.putStringSet("saved_users", hat);
                        editor.commit();
                        String birthday = usermonth + userdate;
                        birthdays.add(birthday);
                        Set<String> hat2 = new HashSet<String>(birthdays);
                        editor.putStringSet("saved_birthdays", hat2);
                        // Save birthday 'Jan15'
                        editor.commit();
                        confirmBtn.setBackgroundColor(Color.GREEN);

                        // clear and update
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Success!");
                        alertDialog.setMessage("Information confirmed.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // clear things
                                        dialog.dismiss();
                                        //recreate();
                                        //nameText.getText().clear();
                                        //monthSpinner.setSelection(0);
                                        //dateSpinner.setSelection(0);
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();
                    }
                }else {
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
