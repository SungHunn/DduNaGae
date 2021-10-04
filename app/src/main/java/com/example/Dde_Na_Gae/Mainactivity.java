package com.example.Dde_Na_Gae;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

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
import java.util.List;

public class Mainactivity extends AppCompatActivity {

    TextView category1;
    TextView category2;
    TextView category3;
    TextView category4;
    TextView category5;
    TextView category6;

    DatabaseReference mDatabase;


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
    LinearLayout my_page;
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


    private RecyclerView today_recyclerView;
    private ArrayList<Today_RecyclerViewItem> todayList;
    private Today_RecyclerViewAdapter today_recyclerViewAdapter;

    ImageView to_day_place1;
    TextView to_day_place1_txt;
    ImageView to_day_place2;
    TextView to_day_place2_txt;

    TextView best_walk1_txt;
    TextView best_walk2_txt;

    TextView best_tour1_txt;
    TextView best_tour2_txt;

    TextView region_walk;
    TextView region_travel;

    ImageView profile_photo;
    TextView my_nickname;
    TextView unlogin;
    LinearLayout layout_account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile_photo = (ImageView) findViewById(R.id.profile_photo);
        listview = findViewById(R.id.navi_list);
        layout_account = (LinearLayout) findViewById(R.id.my_account);
        unlogin = (TextView) findViewById(R.id.my_page_unlogin);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저의 정보 가져오기


        // 오늘의 place 사진 및 텍스트 설정
//        to_day_place1 = (ImageView)findViewById(R.id.to_day_place1);
//        to_day_place1_txt = (TextView)findViewById(R.id.to_day_place1_txt);
//        to_day_place2 = (ImageView)findViewById(R.id.to_day_place2);
//        to_day_place2_txt = (TextView)findViewById(R.id.to_day_place2_txt);

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
//        Glide.with(this).load(Today_FirstImage.get(0)).into(to_day_place1);
//        to_day_place1_txt.setText(Today_Title.get(0));
//        Glide.with(this).load(Today_FirstImage.get(1)).into(to_day_place2);
//        to_day_place2_txt.setText(Today_Title.get(1));
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


        Sub_FirstImage = sub_api.getSub_images();
        Sub_Title = sub_api.getSub_titles();
        Sub_ConId = sub_api.getSub_contentids();

        Glide.with(this).load(Sub_FirstImage.get(0)).into(best_walk1);
        best_walk1_txt.setText(Sub_Title.get(0));
        Glide.with(this).load(Sub_FirstImage.get(1)).into(best_walk2);
        best_walk2_txt.setText(Sub_Title.get(1));
//
//        region_travel = (TextView)findViewById(R.id.region_travel);
//
//        region_walk = (TextView)findViewById(R.id.region_walk);


        ////////////////////////////////////////////////////////
//        todayInit();
//        for (int i=0; i<7; i++){
//            today_addItem(Today_FirstImage.get(i), Today_Title.get(i), Today_ConId.get(i));
//        }

//        today_recyclerViewAdapter = new Today_RecyclerViewAdapter(todayList);

//        today_recyclerView.setAdapter(today_recyclerViewAdapter);
//        today_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

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

                    case R.id.freeboard:
                        Intent intent3 = new Intent(getApplicationContext(), Freeboard_Activity.class);
                        startActivity(intent3);
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

        List<String> list = new ArrayList<>();
        list.add("공지사항");
        list.add("이벤트");
        list.add("고객센터");
        list.add("설정");
        list.add("로그인");

        FirebaseAuth aAuth = FirebaseAuth.getInstance();

        String uid = user != null ? user.getUid() : null; // 로그인한 유저의 고유 uid 가져오기

        listview = findViewById(R.id.navi_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            list.set(4, "로그아웃");

            layout_account.setVisibility(View.VISIBLE);
            my_nickname = (TextView) findViewById(R.id.my_page_login);
            String myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").child(myuid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    Glide.with(Mainactivity.this)
                            .load(userModel.imageUri)
                            .apply(new RequestOptions().circleCrop())
                            .into(profile_photo);
                    my_nickname.setText(userModel.nickname);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
            my_nickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), My_Page.class);
                    startActivity(intent);
                }
            });

        } else {
            unlogin.setVisibility(View.VISIBLE);
            unlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Login_New_Page.class);
                    startActivity(intent);
                }
            });
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.
                simple_list_item_1, list);

        listview.setAdapter(adapter);






        mDatabase = FirebaseDatabase.getInstance().getReference(); // 파이어베이스 realtime database 에서 정보 가져오기

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
                        Intent intent_setting = new Intent(getApplicationContext(), Setting.class);
                        startActivity(intent_setting);
                        break;

                    case 4:
                        if (list.get(7).equals("로그아웃")) {
                            //로그아웃 이벤트
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Login_New_Page.class);
                            startActivity(intent);
                        }
                        break;

                }
            }
        });

        my_page = findViewById(R.id.my_account);
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


        toDayPlaceClick();
        bestPlaceClick();
        bestWalkClick();
//        addMenuClick();
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

    public String str;
    private void main_search() {
        final EditText main_search = findViewById(R.id.main_search);
        str = main_search.getText().toString();
        main_search.getText().clear();

        main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                str = main_search.getText().toString();
                switch (actionId) {
                    case IME_ACTION_SEARCH:
                        Intent intent = new Intent(getApplicationContext(), Category.class);
                        intent.putExtra("SEARCH", str);

                        startActivity(intent);
                }
                return true;
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
    }

//
//    private void addMenuClick() {
//        add_menu1 = (ImageView) findViewById(R.id.add_menu1);
//        add_menu1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
//                startActivity(intent);
//            }
//        });
//        add_menu2 = (ImageView) findViewById(R.id.add_menu2);
//        add_menu2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
//                startActivity(intent);
//            }
//        });
//    }

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

    public void todayInit(){
//        today_recyclerView = (RecyclerView)findViewById(R.id.today_recyclerView);
        todayList = new ArrayList<>();
    }

    public void today_addItem(String url, String title, String conId){
        Today_RecyclerViewItem today_item = new Today_RecyclerViewItem();

        today_item.setUrl(url);
        today_item.setTitle(title);
        today_item.setConId(conId);

        todayList.add(today_item);
    }
}