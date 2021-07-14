package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_up extends AppCompatActivity {

    /*데이터 타입 정의하는 클래스를 만들까 고민중*/
    EditText getNew_id;
    EditText getNew_pw;
    EditText getNew_animal;
    EditText getNew_animal_info;

    String new_id;
    String new_pw;
    String new_animal;
    String animal_info;

    Button btn_sign_up_complite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        btn_sign_up_complite = (Button)findViewById(R.id.sign_up_complite);
        btn_sign_up_complite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSignCreate();
            }
        });

    }

    private void newSignCreate() {
        getNew_id = (EditText)findViewById(R.id.new_people_id);
        getNew_pw = (EditText)findViewById(R.id.new_people_pw);
        getNew_animal = (EditText)findViewById(R.id.animal_info);
        getNew_animal_info = (EditText)findViewById(R.id.animal_more_info);

        new_id = getNew_id.getText().toString();
        new_pw = getNew_pw.getText().toString();
        new_animal = getNew_animal.getText().toString();
        animal_info = getNew_animal_info.getText().toString();

        /* 이 인텐트는 추후 데이터 베이스 연동으로 바꿀 예정*/

        Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
        intent.putExtra("NEW_ID", new_id);
        intent.putExtra("NEW_PW", new_pw);
        intent.putExtra("ANIMAL_INFO", new_animal);
        intent.putExtra("ANIMAL_MORE_INFO", animal_info);

        startActivity(intent);
    }
}
