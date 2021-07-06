package com.example.login_example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Mainactivity extends AppCompatActivity {

    TextView textView_id;
    TextView textView_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_id = (TextView)findViewById(R.id.send_id);
        textView_pw = (TextView)findViewById(R.id.send_pw);

        Intent intent = getIntent();

        String test_id;
        test_id = getIntent().getStringExtra("LOGIN_ID");
        String test_pw;
        test_pw = getIntent().getStringExtra("LOGIN_PW");

        textView_id.setText(test_id);
        textView_pw.setText(test_pw);
    }
}
