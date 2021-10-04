package com.example.Dde_Na_Gae;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Notice extends AppCompatActivity{

    TextView notice_button;
    TextView event_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);

        final String[] notice_list = {"7월 19일 업데이트","7월 16일 업데이트",};
        final String[] event_list = {"6월 20일 할인 이벤트","7월 16일 예약 이벤트"};

        // 이벤트버튼을 누르면 공지 리스트에 있는 빈칸이 생기는데 그걸 없애야 함

        ArrayAdapter notice_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notice_list);
        ArrayAdapter event_adapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1, event_list);

        notice_button = (TextView)findViewById(R.id.notice_button);
        event_button  = (TextView)findViewById(R.id.event_button);

        ListView notice_listview = findViewById(R.id.notice_list);
        ListView event_listview = findViewById(R.id.event_list);

        notice_listview.setAdapter(notice_adapter);
        event_listview.setAdapter(event_adapter);

        notice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                event_listview.setVisibility(View.INVISIBLE);
                notice_listview.setVisibility(View.VISIBLE);
            }
        });

        event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notice_listview.setVisibility(View.INVISIBLE);
                event_listview.setVisibility(View.VISIBLE);
            }
        });


    }

}