package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class My_Page extends AppCompatActivity {
    LinearLayout my_info;
    LinearLayout reservation;
    LinearLayout my_review;
    LinearLayout question;
    LinearLayout kakao_question;
    LinearLayout affiliate;

    BottomNavigationView bottomNavigationView;

    ImageView my_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        myinfo_page();
        reservation_page();
        my_review_page();
        question_page();
        kakao_question_page();
        affiliate_page();
        bottomNavigationBar();
        my_picture_change();

        System.out.println("asdfasdf");
    }

    private void my_picture_change() {
        my_picture = findViewById(R.id.my_picture);
    }

    private void myinfo_page() {
        my_info = findViewById(R.id.my_info);
    }

    private void reservation_page() {
        reservation = findViewById(R.id.reservation);
    }

    private void my_review_page() {
        my_review = findViewById(R.id.my_review);
    }

    private void question_page() {
        question = findViewById(R.id.question);
    }

    private void kakao_question_page() {
        kakao_question = findViewById(R.id.kakao_question);
    }

    private void affiliate_page() {
        affiliate = findViewById(R.id.affiliate);
    }

    private void bottomNavigationBar() {
        // 바텀네비게이션바 클릭 이벤트 삽입 구간
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.matching:
                        Intent intent = new Intent(getApplicationContext(), Matching.class);
                        startActivity(intent);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.mylocation:
                        //Intent intent3 = new Intent(getApplicationContext(), );
                        //startActivity(intent3);
                        break;
                }
                return false;
            }
        });


    }
}
