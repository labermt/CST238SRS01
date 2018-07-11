package com.example.dixon.cst238srs01;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;

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


        final TextView e_count = (TextView) findViewById(R.id.COUNTER);

        e_count.setText("0");
        Button buttonSave = (Button) findViewById(R.id.SAVE);
        Button buttonClear = (Button) findViewById(R.id.CLEAR);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cool = people.size();
                e_count.setText(Integer.toString(validateThenSave()));
                if (cool < people.size()) {
                    e_name.setText("");
                    e_month.setText("");
                    e_date.setText("");
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e_count.setText(Integer.toString(clearAll()));
                e_name.setText("");
                e_month.setText("");
                e_date.setText("");
            }
        });

        e_name = (EditText) findViewById(R.id.NameBox);
        e_month = (EditText) findViewById(R.id.MonthBox);
        e_date = (EditText) findViewById(R.id.DateBox);

    }

    protected int clearAll() {
        people.clear();
        return people.size();
    }

    protected int validateThenSave() {

        Person person = new Person();
        person.name = e_name.getText().toString();
        person.month = e_month.getText().toString();
        person.month = person.month.trim();
        person.name = person.name.trim();
        if (person.name.length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Name!", Toast.LENGTH_SHORT);
            toast.show();
            return people.size();
        }
        if (person.month.equals("January") ||
                person.month.equals("February") ||
                person.month.equals("March") ||
                person.month.equals("April") ||
                person.month.equals("May") ||
                person.month.equals("June") ||
                person.month.equals("July") ||
                person.month.equals("August") ||
                person.month.equals("September") ||
                person.month.equals("October") ||
                person.month.equals("November") ||
                person.month.equals("December")) {
            person.month = e_month.getText().toString();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Month!", Toast.LENGTH_SHORT);
            toast.show();
            return people.size();
        }
        if (person.month.equals("September") ||
                person.month.equals("April") ||
                person.month.equals("June") ||
                person.month.equals("November")) {
            if ((Integer.parseInt(e_date.getText().toString())) > 0 &&
                    (Integer.parseInt(e_date.getText().toString())) <= 30) {
                person.date = Integer.parseInt(e_date.getText().toString());
                people.add(person);
                Toast toast = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid Date!", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        else if (person.month.equals("February")) {
            if ((Integer.parseInt(e_date.getText().toString())) > 0 &&
                    (Integer.parseInt(e_date.getText().toString())) <= 29) {
                person.date = Integer.parseInt(e_date.getText().toString());
                people.add(person);
                Toast toast = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid Date!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            if ((Integer.parseInt(e_date.getText().toString())) > 0 &&
                    (Integer.parseInt(e_date.getText().toString())) <= 31) {
                person.date = Integer.parseInt(e_date.getText().toString());
                people.add(person);
                Toast toast = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid Date!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        return people.size();
    }
}
