package com.Dde_Na_Gae_final.Dde_Na_Gae;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Review extends AppCompatActivity {
    public String category;
    public String date;
    public String address;
    public String title;
    public String contents;

    public EditText get_category;
    public EditText get_date;
    public EditText get_address;
    public EditText get_title;
    public EditText get_content;

    public Button btn_save_review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        btn_save_review = findViewById(R.id.review_Save);
        btn_save_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        saveReviewToDatabase();
    }

    public void saveReviewToDatabase() {
        get_category = findViewById(R.id.review_Category);
        get_date = findViewById(R.id.review_Date);
        get_address = findViewById(R.id.review_Address);
        get_title = findViewById(R.id.title);
        get_content = findViewById(R.id.review_Contents);

        category = get_category.getText().toString();
        date = get_date.getText().toString();
        address = get_address.getText().toString();
        title = get_title.getText().toString();
        contents = get_content.getText().toString();
    }
}
