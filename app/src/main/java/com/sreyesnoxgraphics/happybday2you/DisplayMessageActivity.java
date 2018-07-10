package com.sreyesnoxgraphics.happybday2you;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get resources
        SharedPreferences preferences = this.getSharedPreferences("BirthdayPreferences", Context.MODE_PRIVATE);
        int count = preferences.getInt("Count", 0);
        String storedName = preferences.getString("StoredName", null);
        String checkedName = preferences.getString("CheckedName", null);
        boolean birthdayMatch = preferences.getBoolean("Flag", false);

        // Capture the layout's text view and set the string as text

        if (!birthdayMatch) {
            TextView textView = findViewById(R.id.textView);
            String message = "Birthday Entries Count: " + count;
            textView.setText(message);
        }
        else {
            TextView textView = findViewById(R.id.textView);
            String message = "Matched Birthdays: " + storedName + " and " + checkedName + "\n" + "\nBirthday Entries Count: " + count;
            textView.setText(message);
        }
    }
}
