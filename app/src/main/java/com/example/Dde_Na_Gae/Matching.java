package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Matching extends AppCompatActivity{

    ImageView img_back;
    Button btn_private_match;
    Button btn_group_match;
    Button btn_my_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        img_back = (ImageView)findViewById(R.id.img_back);
        btn_private_match = (Button)findViewById(R.id.btn_private_match);
        btn_group_match = (Button)findViewById(R.id.btn_group_match);
        btn_my_group = (Button)findViewById(R.id.btn_my_group);


        // 홈으로 이동
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
            }
        });

         // 1ㄷ1 매칭으로 이동
        btn_private_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Matching_Option.class);
                startActivity(intent);
            }
        });

//        // 그룹 매칭으로 이동
//        btn_group_match.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), );
//            }
//        });
//
//        // 내 그룹방으로 이동 (채팅방)
//        btn_group_match.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), );
//            }
//        });
    }
}
