package com.example.capti.happybirthday2you;

import android.content.Intent;
import android.os.VibrationEffect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void callMonthSelect(View view)
    {
        Intent intent = new Intent(this, MonthSelection.class);
        EditText editText = (EditText) findViewById(R.id.editText);


        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void callDayRangeSelection(View view)
    {
        Intent intent;
        intent = new Intent(this, DayRangeSelection.class);
        startActivity(intent);
    }
}
