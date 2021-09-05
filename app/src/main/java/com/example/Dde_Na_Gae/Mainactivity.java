package com.example.Dde_Na_Gae;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

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

    ArrayList<String> FirstImage = new ArrayList<>();
    ArrayList<String> Title = new ArrayList<>();

    TextView best_walk1_txt;
    TextView best_walk2_txt;
    TextView best_tour1_txt;
    TextView best_tour2_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        best_walk1 = (ImageView) findViewById(R.id.best_walk1);
        best_walk2 = (ImageView) findViewById(R.id.best_walk2);
        best_tour1 = (ImageView) findViewById(R.id.best_tour1);
        best_tour2 = (ImageView) findViewById(R.id.best_tour2);

        best_walk1_txt = (TextView)findViewById(R.id.best_walk1_txt);
        best_walk2_txt = (TextView)findViewById(R.id.best_walk2_txt);
        best_tour1_txt = (TextView)findViewById(R.id.best_tour1_txt);
        best_tour2_txt = (TextView)findViewById(R.id.best_tour2_txt);

        FirstImage = getIntent().getStringArrayListExtra("Image");
        Title = getIntent().getStringArrayListExtra("Title");

        Glide.with(this).load(FirstImage.get(0)).into(best_walk1);
        best_walk1_txt.setText(Title.get(0));

        Glide.with(this).load(FirstImage.get(1)).into(best_walk2);
        best_walk2_txt.setText(Title.get(1));

        Glide.with(this).load(FirstImage.get(2)).into(best_tour1);
        best_tour1_txt.setText(Title.get(2));

        Glide.with(this).load(FirstImage.get(3)).into(best_tour2);
        best_tour2_txt.setText(Title.get(3));



        System.out.println(FirstImage);
        System.out.println(Title);


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


        onTextViewClick();
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
    /// ------------------------------------------------------------ url

    private void bestWalkClick() {

        best_walk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getApplicationContext(), BestWalk.class);

                Intent intent = new Intent(getApplicationContext(), Pay.class); // 결제 실험
                startActivity(intent);
            }
        });

        // ----------------------------------------------------------- 여기서 실험

        TextView result_txt = (TextView) findViewById(R.id.best_walk2_txt);
        best_walk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                    startActivity(intent);
                }
        });
    }

    public void onTextViewClick() {
        category1 = (TextView) findViewById(R.id.category_1);
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category1.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("SEARCH", getcategory1);

                startActivity(intent);
            }
        });
        category2 = (TextView) findViewById(R.id.category_2);
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category2.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("SEARCH", getcategory1);

                startActivity(intent);
            }
        });
        category3 = (TextView) findViewById(R.id.category_3);
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category3.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("SEARCH", getcategory1);

                startActivity(intent);
            }
        });
        category4 = (TextView) findViewById(R.id.category_4);
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category4.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("SEARCH", getcategory1);

                startActivity(intent);
            }
        });
        category5 = (TextView) findViewById(R.id.category_5);
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category5.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("SEARCH", getcategory1);

                startActivity(intent);
            }
        });
        category6 = (TextView) findViewById(R.id.category_6);
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category6.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("SEARCH", getcategory1);

                startActivity(intent);
            }
        });
    }

    public void toDayPlaceClick() {
        today_place1 = (ImageView) findViewById(R.id.to_day_place1);
        today_place1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodayPlace.class);
                startActivity(intent);
            }
        });
        today_place2 = (ImageView) findViewById(R.id.to_day_place2);
        today_place2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodayPlace.class);
                startActivity(intent);
            }
        });
    }

    public void bestPlaceClick() {
        best_tour1 = (ImageView) findViewById(R.id.best_tour1);
        best_tour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestPlace.class);
                startActivity(intent);
            }
        });
        best_tour2 = (ImageView) findViewById(R.id.best_tour2);
        best_tour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestPlace.class);
                startActivity(intent);
            }
        });
    }

}