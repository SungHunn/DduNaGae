package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class My_free_board_detail extends AppCompatActivity {

    private TextView title;
    private TextView content;
    private TextView nickname;
    private EditText comment;
    private Button send;

    private Button change;
    private Button delete;

    private String intent_title;
    private String intent_content;
    private String intent_nickname;
    private String intente_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_free_board_detail);

        title = (TextView)findViewById(R.id.my_free_board_detail_title) ;
        content = (TextView)findViewById(R.id.my_free_board_detail_content);
        nickname = (TextView)findViewById(R.id.my_writer_nickname);
        comment = (EditText)findViewById(R.id.my_free_board_detail_comment);
        send = (Button)findViewById(R.id.my_free_board_detail_post_comment);

        change = (Button)findViewById(R.id.my_free_board_detail_change);
        delete = (Button)findViewById(R.id.my_free_board_detail_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        Intent intent = getIntent();
        intent_title =  intent.getStringExtra("title");
        title.setText(intent_title);

        intent_content = intent.getStringExtra("content");
        content.setText(intent_content);

        intent_nickname = intent.getStringExtra("nickname");
        nickname.setText(intent_nickname);

        intente_comment = intent.getStringExtra("comment");
        comment.setText(intente_comment);



    }


}
