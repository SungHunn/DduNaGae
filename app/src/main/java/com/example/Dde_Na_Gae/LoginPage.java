package com.example.Dde_Na_Gae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    Button btn_login;
    EditText getlogin_id;
    EditText getlogin_pw;
    String login_pw;
    String login_id;

    Button btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        btn_login = (Button)findViewById(R.id.login);
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOnDdeNaGea();
            }
        });

        btn_sign_up = (Button)findViewById(R.id.sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(intent);
            }
        });
    }

    private void loginOnDdeNaGea() {
        getlogin_id = (EditText)findViewById(R.id.login_id);
        getlogin_pw = (EditText)findViewById(R.id.login_pw);

        login_id = getlogin_id.getText().toString();
        login_pw = getlogin_pw.getText().toString();

        /*
        이 사이에 로그인 id, pw 정보가 데이터 베이스에 있는지 확인하는 코드 작성 예정

        이 뒤 부분은 추후 삭제 예정
        */

        Intent intent = new Intent(getApplication(), Mainactivity.class);

        intent.putExtra("LOGIN_ID", login_id);
        intent.putExtra("LOGIN_PW", login_pw);

        startActivity(intent);

        getlogin_id.setText("");
        getlogin_pw.setText("");
    }
}