package com.example.login_example;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        btn_login = (Button)findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlogin_id = (EditText)findViewById(R.id.login_id);
                getlogin_pw = (EditText)findViewById(R.id.login_pw);

                login_id = getlogin_id.getText().toString();
                login_pw = getlogin_pw.getText().toString();

                Intent intent = new Intent(getApplication(), Mainactivity.class);

                intent.putExtra("LOGIN_ID", login_id);
                intent.putExtra("LOGIN_PW", login_pw);

                startActivity(intent);

                getlogin_id.setText("");
                getlogin_pw.setText("");

            }
        });
    }
}