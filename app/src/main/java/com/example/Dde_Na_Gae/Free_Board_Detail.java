package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Free_Board_Detail  extends AppCompatActivity {

    private TextView title;
    private TextView content;
    private TextView nickname;
    private EditText comment;
    private Button send;


    private String intent_title;
    private String intent_content;
    private String intent_nickname;
    private String intente_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_detail);

        title = (TextView)findViewById(R.id.free_board_detail_title) ;
        content = (TextView)findViewById(R.id.free_board_detail_content);
        nickname = (TextView)findViewById(R.id.writer_nickname);
        comment = (EditText)findViewById(R.id.free_board_detail_comment);
        send = (Button)findViewById(R.id.free_board_detail_post_comment);




        Intent intent = getIntent();
        intent_title =  intent.getStringExtra("title");

        //System.out.println(intent_title);

        title.setText(intent_title);

        intent_content = intent.getStringExtra("content");
        content.setText(intent_content);

        intent_nickname = intent.getStringExtra("nickname");
        nickname.setText(intent_nickname);

        intente_comment = intent.getStringExtra("comment");
        comment.setText(intente_comment);



    }
}

