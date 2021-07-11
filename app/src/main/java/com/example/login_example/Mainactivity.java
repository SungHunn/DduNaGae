package com.example.login_example;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        bestWlakClick();
        addMenuClick();

        System.out.println("asdfasdf");
    }

    private void addMenuClick() {
        add_menu1 = (ImageView)findViewById(R.id.add_menu1);
        add_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                startActivity(intent);
            }
        });
        add_menu2 = (ImageView)findViewById(R.id.add_menu2);
        add_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                startActivity(intent);
            }
        });
    }

    private void bestWlakClick() {
        best_walk1 = (ImageView)findViewById(R.id.best_walk1);
        best_walk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                startActivity(intent);
            }
        });
        best_walk2 = (ImageView)findViewById(R.id.best_walk2);
        best_walk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestWalk.class);
                startActivity(intent);
            }
        });
    }

    public void onTextViewClick() {
        category1 = (TextView)findViewById(R.id.category_1);
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category1.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("CATEGORY", getcategory1);

                startActivity(intent);
            }
        });
        category2 = (TextView)findViewById(R.id.category_2);
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category2.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("CATEGORY", getcategory1);

                startActivity(intent);
            }
        });
        category3 = (TextView)findViewById(R.id.category_3);
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category3.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("CATEGORY", getcategory1);

                startActivity(intent);
            }
        });
        category4 = (TextView)findViewById(R.id.category_4);
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category4.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("CATEGORY", getcategory1);

                startActivity(intent);
            }
        });
        category5 = (TextView)findViewById(R.id.category_5);
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category5.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("CATEGORY", getcategory1);

                startActivity(intent);
            }
        });
        category6 = (TextView)findViewById(R.id.category_6);
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getcategory1;
                getcategory1 = category6.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Category.class);
                intent.putExtra("CATEGORY", getcategory1);

                startActivity(intent);
            }
        });
    }

    public void toDayPlaceClick() {
        today_place1 = (ImageView)findViewById(R.id.to_day_place1);
        today_place1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodayPlace.class);
                startActivity(intent);
            }
        });
        today_place2 = (ImageView)findViewById(R.id.to_day_place2);
        today_place2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TodayPlace.class);
                startActivity(intent);
            }
        });
    }

    public void bestPlaceClick() {
        best_tour1 = (ImageView)findViewById(R.id.best_tour1);
        best_tour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestPlace.class);
                startActivity(intent);
            }
        });
        best_tour2 = (ImageView)findViewById(R.id.best_tour2);
        best_tour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BestPlace.class);
                startActivity(intent);
            }
        });
    }
}
