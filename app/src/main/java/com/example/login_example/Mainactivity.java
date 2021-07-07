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

        String new_id;
        String new_pw;
        String new_animal;
        String animal_info;

        new_id = getIntent().getStringExtra("NEW_ID");
        new_pw = getIntent().getStringExtra("NEW_PW");
        new_animal = getIntent().getStringExtra("ANIMAL_INFO");
        animal_info = getIntent().getStringExtra("ANIMAL_MORE_INFO");

        System.out.println(new_id);
        System.out.println(new_pw);
        System.out.println(new_animal);
        System.out.println(animal_info);
    }
}
