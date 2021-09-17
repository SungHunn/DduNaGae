package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.log4j.chainsaw.Main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class Mainactivity extends AppCompatActivity {

    TextView category1;
    TextView category2;
    TextView category3;
    TextView category4;
    TextView category5;
    TextView category6;

    ImageView today_place1;
    ImageView today_place2;

    ImageView best_tour1;
    ImageView best_tour2;

    ImageView best_walk1;
    ImageView best_walk2;

    ImageView add_menu1;
    ImageView add_menu2;

    //네비게이션바
    DrawerLayout drawerLayout;
    View drawerView;
    ListView listview = null;
    TextView my_page;
    //네비게이션바

    BottomNavigationView bottomNavigationView;

    ArrayList<String> Today_FirstImage = new ArrayList<>();
    ArrayList<String> Today_Title = new ArrayList<>();
    ArrayList<String> Today_ConId = new ArrayList<>();

    ArrayList<String> Main_FirstImage = new ArrayList<>();
    ArrayList<String> Main_Title = new ArrayList<>();
    ArrayList<String> Main_ConId = new ArrayList<>();

    ArrayList<String> Sub_FirstImage = new ArrayList<>();
    ArrayList<String> Sub_Title = new ArrayList<>();
    ArrayList<String> Sub_ConId = new ArrayList<>();


    ImageView to_day_place1;
    TextView to_day_place1_txt;
    ImageView to_day_place2;
    TextView to_day_place2_txt;

    TextView best_walk1_txt;
    TextView best_walk2_txt;

    TextView best_tour1_txt;
    TextView best_tour2_txt;

    TextView region_walk;
    TextView region_travle;

//    Calendar cal = Calendar.getInstance();
//    int today = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 오늘의 place 사진 및 텍스트 설정
        to_day_place1 = (ImageView)findViewById(R.id.to_day_place1);
        to_day_place1_txt = (TextView)findViewById(R.id.to_day_place1_txt);
        to_day_place2 = (ImageView)findViewById(R.id.to_day_place2);
        to_day_place2_txt = (TextView)findViewById(R.id.to_day_place2_txt);

//        Today_FirstImage = getIntent().getStringArrayListExtra("Today_Image");
        Today_api today_api = new Today_api();
        Thread today_thread = new Thread(today_api);
        Main_api main_api = new Main_api();
        Thread main_thread = new Thread(main_api);
        Sub_api sub_api = new Sub_api();
        Thread sub_thread = new Thread(sub_api);
        try {
            today_thread.start();
            today_thread.join();

            main_thread.start();
            main_thread.join();

            sub_thread.start();
            sub_thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Today_FirstImage = today_api.getToday_images();
        Today_Title = today_api.getToday_titles();
        Today_ConId = today_api.getToday_contentids();
        Glide.with(this).load(Today_FirstImage.get(0)).into(to_day_place1);
        to_day_place1_txt.setText(Today_Title.get(0));
        Glide.with(this).load(Today_FirstImage.get(1)).into(to_day_place2);
        to_day_place2_txt.setText(Today_Title.get(1));
        /////////////////////////

        best_tour1 = (ImageView) findViewById(R.id.best_tour1);
        best_tour1_txt = (TextView)findViewById(R.id.best_tour1_txt);
        best_tour2 = (ImageView) findViewById(R.id.best_tour2);
        best_tour2_txt = (TextView)findViewById(R.id.best_tour2_txt);

        Main_FirstImage = main_api.getMain_images();
        Main_Title = main_api.getMain_titles();
        Main_ConId = main_api.getMain_contentids();

        Glide.with(this).load(Main_FirstImage.get(0)).into(best_tour1);
        best_tour1_txt.setText(Main_Title.get(0));
        Glide.with(this).load(Main_FirstImage.get(1)).into(best_tour2);
        best_tour2_txt.setText(Main_Title.get(1));

        best_walk1 = (ImageView) findViewById(R.id.best_walk1);
        best_walk1_txt = (TextView)findViewById(R.id.best_walk1_txt);
        best_walk2 = (ImageView) findViewById(R.id.best_walk2);
        best_walk2_txt = (TextView)findViewById(R.id.best_walk2_txt);

        ////////////////////////////////////////////////////////////////

        Sub_FirstImage = sub_api.getSub_images();
        Sub_Title = sub_api.getSub_titles();
        Sub_ConId = sub_api.getSub_contentids();

        Glide.with(this).load(Sub_FirstImage.get(0)).into(best_walk1);
        best_walk1_txt.setText(Sub_Title.get(0));
        Glide.with(this).load(Sub_FirstImage.get(1)).into(best_walk2);
        best_walk2_txt.setText(Sub_Title.get(1));

        region_travle = (TextView)findViewById(R.id.region_travel);
//        region_travle.setText(getRegionCode((today % 8) + 1) + " 추천 여행지");

        region_walk = (TextView)findViewById(R.id.region_walk);
//        region_walk.setText(getRegionCode((today % 9) + 31) + " 추천 여행지");


        // 바텀네비게이션바 클릭 이벤트 삽입 구간
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
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

        //네비게이션바(햄버거) 클릭 이벤트 삽입 구간
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
                        Intent intent_notice =
                                new Intent(getApplicationContext(), Notice.class);
                        startActivity(intent_notice);
                        break;

                    case 1:
                        // 공지사항이랑 이벤트는 같은 페이지임 나중에 이벤트 클릭된 상태로 넘어가게 하면됨
                        Intent intent_event =
                                new Intent(getApplicationContext(), Notice.class);
                        startActivity(intent_event);
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
                        Intent intent_setting = new Intent(getApplicationContext(), Setting.class);
                        startActivity(intent_setting);
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

        //네비게이션바

        Intent intent = getIntent();

        String new_id;
        String new_pw;
        String new_animal;
        String animal_info;

        new_id = getIntent().getStringExtra("NEW_ID");
        new_pw = getIntent().getStringExtra("NEW_PW");
        new_animal = getIntent().getStringExtra("ANIMAL_INFO");
        animal_info = getIntent().getStringExtra("ANIMAL_MORE_INFO");


//        onTextViewClick();
        toDayPlaceClick();
        bestPlaceClick();
        bestWalkClick();
        addMenuClick();
        main_search();

    }


    //네비게이션바
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
    //네비게이션바

// serach box
    private void main_search(){

        EditText main_search = findViewById(R.id.main_search);
        final String str;
        str = main_search.toString();

        main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Category.class);
                intent.putExtra("SEARCH", str);
                startActivity(intent);
            }
        });
    }

    private void addMenuClick() {
        add_menu1 = (ImageView) findViewById(R.id.add_menu1);
        add_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                startActivity(intent);
            }
        });
        add_menu2 = (ImageView) findViewById(R.id.add_menu2);
        add_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                startActivity(intent);
            }
        });
    }

    // 오늘의 place 클릭 이벤트
    public void toDayPlaceClick() {
        today_place1 = (ImageView) findViewById(R.id.to_day_place1);
        today_place1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_Selected.class);
                intent.putExtra("Image", Today_FirstImage.get(0));
                intent.putExtra("Title", Today_Title.get(0));
                intent.putExtra("ConId", Today_ConId.get(0));
                startActivity(intent);
            }
        });

        today_place2 = (ImageView) findViewById(R.id.to_day_place2);
        today_place2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_Selected.class);
                intent.putExtra("Image", Today_FirstImage.get(1));
                intent.putExtra("Title", Today_Title.get(1));
                intent.putExtra("ConId", Today_ConId.get(1));
                startActivity(intent);
            }
        });
    }

    public void bestPlaceClick() {
        best_tour1 = (ImageView) findViewById(R.id.best_tour1);
        best_tour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_Selected.class);
                intent.putExtra("Image", Main_FirstImage.get(0));
                intent.putExtra("Title", Main_Title.get(0));
                intent.putExtra("ConId", Main_ConId.get(0));
                startActivity(intent);
            }
        });

        best_tour2 = (ImageView) findViewById(R.id.best_tour2);
        best_tour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_Selected.class);
                intent.putExtra("Image", Main_FirstImage.get(1));
                intent.putExtra("Title", Main_Title.get(1));
                intent.putExtra("ConId", Main_ConId.get(1));
                startActivity(intent);
            }
        });
    }

    private void bestWalkClick() {

        best_walk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_Selected.class);
                intent.putExtra("Image", Sub_FirstImage.get(0));
                intent.putExtra("Title", Sub_Title.get(0));
                intent.putExtra("ConId", Sub_ConId.get(0));
                startActivity(intent);
            }
        });

        best_walk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_Selected.class);
                intent.putExtra("Image", Sub_FirstImage.get(1));
                intent.putExtra("Title", Sub_Title.get(1));
                intent.putExtra("ConId", Sub_ConId.get(1));
                startActivity(intent);
            }
        });
    }
//
//    public String getRegionCode(int code){
//        String region="";
//
//        if (code == 1)
//            region = "서울";
//        else if (code == 2)
//            region = "인천";
//        else if (code == 3)
//            region = "대전";
//        else if (code == 4)
//            region = "대구";
//        else if (code == 5)
//            region = "광주";
//        else if (code == 6)
//            region = "부산";
//        else if (code == 7)
//            region = "울산";
//        else if (code == 8)
//            region = "세종특별자치시";
//        else if (code == 31)
//            region = "경기도";
//        else if (code == 32)
//            region = "강원도";
//        else if (code == 33 || code == 34)
//            region = "충청도";
//        else if (code == 35 || code == 36)
//            region = "경상도";
//        else if (code == 37 || code == 38)
//            region = "전라도";
//        else if (code == 39)
//            region = "제주도";
//
//        return region;
//    }

}