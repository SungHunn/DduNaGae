package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Matching extends AppCompatActivity {

    ImageView img_back;
    Button btn_private_match;
    Button btn_make_match;
    Button btn_my_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        img_back = (ImageView)findViewById(R.id.img_back);
        btn_private_match = (Button)findViewById(R.id.btn_match);
        btn_make_match = (Button)findViewById(R.id.btn_matchmake);
        btn_my_group = (Button)findViewById(R.id.btn_my_group);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
            }
        });

        btn_private_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_make_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Matching_Option.class);
                startActivity(intent);
            }
        });
    }
}
