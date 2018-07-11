package com.example.dixon.cst238srs01;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.time.Month;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     class Person {
        String name;
        String month;
        Number date;
    }
    EditText e_name, e_month, e_date;
    ArrayList people = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e_name = (EditText) findViewById(R.id.NameBox);
        e_month = (EditText) findViewById(R.id.MonthBox);
        e_date = (EditText) findViewById(R.id.DateBox);
    }

    protected void validateThenSave() {

        Person person = new Person();
        person.name = e_name.getText().toString();
        if (e_month.getText().toString().equals("January") ||
                e_month.getText().toString().equals("February") ||
                e_month.getText().toString().equals("March") ||
                e_month.getText().toString().equals("April") ||
                e_month.getText().toString().equals("May") ||
                e_month.getText().toString().equals("June") ||
                e_month.getText().toString().equals("July") ||
                e_month.getText().toString().equals("August") ||
                e_month.getText().toString().equals("September") ||
                e_month.getText().toString().equals("October") ||
                e_month.getText().toString().equals("November") ||
                e_month.getText().toString().equals("Decenber")) {
            person.month = e_month.getText().toString();
        } else {
            return;
        }
        if (e_month.getText().toString().equals("September") ||
                e_month.getText().toString().equals("April") ||
                e_month.getText().toString().equals("June") ||
                e_month.getText().toString().equals("November")) {
            if ((Integer.parseInt(e_date.getText().toString())) > 0 ||
                    (Integer.parseInt(e_date.getText().toString())) <= 30) {
                person.date = Integer.parseInt(e_date.getText().toString());
                people.add(person);
            }

        }
        else if (e_month.getText().toString().equals("February")) {
            if ((Integer.parseInt(e_date.getText().toString())) > 0 ||
                    (Integer.parseInt(e_date.getText().toString())) <= 29) {
                person.date = Integer.parseInt(e_date.getText().toString());
                people.add(person);
            }
        }
        else {
            if ((Integer.parseInt(e_date.getText().toString())) > 0 ||
                    (Integer.parseInt(e_date.getText().toString())) <= 31) {
                person.date = Integer.parseInt(e_date.getText().toString());
                people.add(person);
            }
        }
    }
}
