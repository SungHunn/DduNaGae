package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
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

    TextView selected_item_desciption;
    String overview;

    TextView selected_name;
    TextView selected_name_main;

    ImageView selected_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected);

        new Thread() {
            @Override
            public void run() {
                conId = getIntent().getStringExtra("ConId");
                String urlAdress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" +
                        "?ServiceKey=" + key +
                        "&MobileOS=AND" +
                        "&MobileApp=TestApp" +
                        "&numOfRows=1" +
                        "&contentid" + conId +
                        "&contentTypeId=12" +
                        "&addrinfoYN=Y" +
                        "&overviewYN=Y" +
                        "&_type=json";

                try {
                    URL url = new URL(urlAdress);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = br.readLine();

                    while (line != null) {
                        buffer.append(line + "\n");
                        line = br.readLine();
                    }

                    String jsonData = buffer.toString();

                    System.out.println(conId);
                    System.out.println(jsonData);

                    JSONObject obj = new JSONObject(jsonData);
                    JSONObject
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        selected_name = findViewById(R.id.selected_name);
//        selected_item_name.setText(getIntent().getStringExtra("NAME"));
        selected_name_main = findViewById(R.id.selected_name_main);
//        selected_item_name_main.setText(getIntent().getStringExtra("NAME"));

        img = getIntent().getStringExtra("Image");
        selected_img = (ImageView)findViewById(R.id.selected_img);
        Glide.with(this).load(img).into(selected_img);

        title = getIntent().getStringExtra("Title");
        selected_name.setText(title);
        selected_name_main.setText(title);

        selected_item_desciption = findViewById(R.id.selected_item_desciption);
        selected_item_desciption.setText(overview);

//        viewPager2 = findViewById(R.id.selected_img);
//        ViewpagerAdapter adapter = new ViewpagerAdapter(setItem());
//        viewPager2.setAdapter(adapter);

        selected_map = findViewById(R.id.selected_map);


        // 상세 정보


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
                Intent intent = new Intent(getApplicationContext(), OpenApi.class);
                startActivity(intent);
            }
        });

    }

}
