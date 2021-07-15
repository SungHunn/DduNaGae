package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Matching_Option extends AppCompatActivity {

    ImageView img_back;
    TextView gender_male;
    TextView gender_female;

    LinearLayout small_size;
    LinearLayout medium_size;
    LinearLayout large_size;

    TextView poss_car;
    TextView no_poss_car;

    BottomNavigationView bottomNavigationView;

    DrawerLayout drawerLayout;
    View drawerView;
    ListView listview = null;
    TextView my_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_option);

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

        // 네비게이션 바(햄버거) 클릭 이벤트 삽입 구간
        drawerLayout = findViewById(R.id.drawlayout);
        drawerView = findViewById(R.id.drawer);

        ImageButton nvg_open = findViewById(R.id.nvg_open);
        nvg_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        ImageButton nvg_close = findViewById(R.id.nvg_close);
        nvg_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        final String[] items = {"공지사항", "이벤트", "예약내역", "매칭방 목록", "좋아요 표시한 목록", "고객센터", "설정", "로그인"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        listview = findViewById(R.id.navi_list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        break;

                    case 2:
                        break;

                    case 3:
                        break;

                    case 4:
                        break;

                    case 5:
                        break;

                    case 6:
                        break;

                    case 7:
                        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        my_page = findViewById(R.id.my_page);
        my_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Page.class);
                startActivity(intent);
            }
        });

        img_back = (ImageView)findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Matching.class);
                startActivity(intent);
            }
        });

        gender_male = (TextView)findViewById(R.id.gender_male);
        gender_female = (TextView)findViewById(R.id.gender_Female);

        // 클릭하게되면 텍스트 색 변경을 통해 등록 확인인
       gender_male.setOnClickListener(new View.OnClickListener() {
            private int num = 0;
            @Override
            public void onClick(View v) {
                num++;
                if(num % 2 == 1)
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
                if(num % 2 == 1)
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
                if(num % 2 == 1)
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
                if(num % 2 == 1)
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
                if(num % 2 == 1)
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
                if(num % 2 == 1)
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
                if(num % 2 == 1)
                    no_poss_car.setBackgroundColor(Color.parseColor("#FF7F00"));
                else
                    no_poss_car.setBackgroundColor(Color.WHITE);
            }
        });

        // Spinner 및 지도 보기 추가해야됨
        // 매칭목록방 추가 에정


    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}
