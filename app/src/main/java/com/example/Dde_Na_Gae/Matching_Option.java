package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Matching_Option extends AppCompatActivity {

    ImageView img_back;
    TextView gender_male;
    TextView gender_female;

    LinearLayout small_size;
    LinearLayout medium_size;
    LinearLayout large_size;

    TextView poss_car;
    TextView no_poss_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_option);

        img_back = (ImageView)findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Matching.class);
                startActivity(intent);
            }
        });

        //네비게이션 바 코드 작성해야됨

        gender_male = (TextView)findViewById(R.id.gender_male);
        gender_female = (TextView)findViewById(R.id.gender_Female);

        // 클릭하게되면 텍스트 색 변경을 통해 등록 확인인
       gender_male.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    gender_male.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    gender_male.setBackgroundColor(Color.WHITE);
            }
        });

        gender_female.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    gender_female.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    gender_female.setBackgroundColor(Color.WHITE);
            }
        });

        small_size = (LinearLayout)findViewById(R.id.small_size);
        medium_size = (LinearLayout)findViewById(R.id.medium_size);
        large_size = (LinearLayout)findViewById(R.id.large_size);

        small_size.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    small_size.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    small_size.setBackgroundColor(Color.WHITE);
            }
        });

        medium_size.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    medium_size.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    medium_size.setBackgroundColor(Color.WHITE);
            }
        });

        large_size.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    large_size.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    large_size.setBackgroundColor(Color.WHITE);
            }
        });

        poss_car = (TextView)findViewById(R.id.poss_car);
        no_poss_car = (TextView)findViewById(R.id.no_poss_car);

        poss_car.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    poss_car.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    poss_car.setBackgroundColor(Color.WHITE);
            }
        });

        no_poss_car.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 0)
                    no_poss_car.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    no_poss_car.setBackgroundColor(Color.WHITE);
            }
        });

        // Spinner 및 지도 보기 추가해야됨
        // 매칭목록방 추가 에정


    }
}
