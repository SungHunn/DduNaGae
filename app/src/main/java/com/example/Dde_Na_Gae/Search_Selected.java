package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Search_Selected extends AppCompatActivity {

    TextView selected_item_name;
    TextView selected_item_name_main;
    TextView selected_item_desciption;

    ImageView selected_map;

    ViewPager2 viewPager2;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected);

        selected_item_name = findViewById(R.id.selected_name);
        selected_item_name.setText(getIntent().getStringExtra("NAME"));
        selected_item_name_main = findViewById(R.id.selected_name_main);
        selected_item_name_main.setText(getIntent().getStringExtra("NAME"));

        viewPager2 = findViewById(R.id.selected_img);
        ViewpagerAdapter adapter = new ViewpagerAdapter(setItem());
        viewPager2.setAdapter(adapter);

        selected_map = findViewById(R.id.selected_map);

        selected_item_desciption = findViewById(R.id.selected_item_desciption);
        selected_item_desciption.setText(getIntent().getStringExtra("DES"));

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

                    case R.id.reservation:
                        //Intent intent3 = new Intent(getApplicationContext(), );
                        //startActivity(intent3);
                        break;
                }
                return false;
            }
        });
    }

    protected ArrayList setItem() {
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("page 1");
        itemList.add("page 2");
        itemList.add("page 3");

        return itemList;
    }
}
