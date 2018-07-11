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
                e_count.setText(Integer.toString(validateThenSave()));
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e_count.setText(Integer.toString(clearAll()));
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
                e_month.getText().toString().equals("December")) {
            person.month = e_month.getText().toString();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Month!", Toast.LENGTH_SHORT);
            toast.show();
            return people.size();
        }
        if (e_month.getText().toString().equals("September") ||
                e_month.getText().toString().equals("April") ||
                e_month.getText().toString().equals("June") ||
                e_month.getText().toString().equals("November")) {
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
        else if (e_month.getText().toString().equals("February")) {
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
