 package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class  Search_Selected extends AppCompatActivity {
    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";


    ImageView img_back;

    ImageView selected_map;

    BottomNavigationView bottomNavigationView;

    String img;
    String title;
    String conId;

    String homepage;
    String overview;
    String addr1;

    TextView selected_item_desciption;
    TextView selected_item_hompage;
    TextView selected_item_addr;

    TextView selected_name;
    TextView selected_name_main;

    ImageView selected_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected);

        conId = getIntent().getStringExtra("ConId");
        Detail_api detail_api = new Detail_api(conId);
        Thread detail_thread = new Thread(detail_api);

        try {
            detail_thread.start();
            detail_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        homepage = stripHtml(detail_api.getHomepage());
        overview = stripHtml(detail_api.getOverview());
        addr1 = stripHtml(detail_api.getAddr1());

        selected_item_hompage = findViewById(R.id.selected_item_hompage);
        selected_item_desciption = findViewById(R.id.selected_item_desciption);
        selected_item_addr = findViewById(R.id.selected_item_addr);

        selected_item_hompage.setText(homepage);
        selected_item_desciption.setText(overview);
        selected_item_addr.setText(addr1);

        selected_name = findViewById(R.id.selected_name);
//        selected_name.setText(getIntent().getStringExtra("NAME"));
        selected_name_main = findViewById(R.id.selected_name_main);
//        selected_name_main.setText(getIntent().getStringExtra("NAME"));

        title = getIntent().getStringExtra("Title");
        selected_name.setText(title);
        selected_name_main.setText(title);

        img = getIntent().getStringExtra("Image");
        selected_img = (ImageView)findViewById(R.id.selected_img);
        Glide.with(this).load(img).into(selected_img);


//        viewPager2 = findViewById(R.id.selected_img);
//        ViewpagerAdapter adapter = new ViewpagerAdapter(setItem());
//        viewPager2.setAdapter(adapter);

        selected_map = findViewById(R.id.selected_map);


        // 클릭 이벤트
        selected_item_hompage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(homepage));
                startActivity(intent);
                System.out.println(conId);
            }
        });

        // 바텀 네비
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.love_it:
                        //Intent intent = new Intent(getApplicationContext(), Matching.class);
                        //startActivity(intent);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.more_room_activity:
                        Intent intent = new Intent(getApplicationContext(), Search_Selected_More.class);
                        intent.putExtra("More_info", getIntent().getStringExtra("NAME"));
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        img_back = (ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
            }
        });

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

    }

    public String stripHtml(String html)
    { return Html.fromHtml(html).toString(); }
}
