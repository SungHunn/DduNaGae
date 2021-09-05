package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity{

    TextView getcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        getcategory = (TextView)findViewById(R.id.test_catecory);

        Intent intent = getIntent();

        getcategory.setText(getIntent().getStringExtra("CATEGORY"));
    }
}
