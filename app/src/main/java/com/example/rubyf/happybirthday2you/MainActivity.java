package com.example.rubyf.happybirthday2you;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public EditText name;
    public EditText month;
    public EditText day;
    public Button add_me;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R. id.name);
        month = (EditText) findViewById(R. id.month);
        day = (EditText) findViewById(R. id.day);

        final FileOutputStream outputStream;
        String fileName = "feederTxtFile";


        Button add_me = (Button) findViewById(R. id.add_me);
        add_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    outputStream = openFileOutput(fileName,CONTEXT.MOOD_PRIVATE);
                    outputStream.write(String.getBytes());
                    outputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    public void submitBirthday(View view) {

    }
}
