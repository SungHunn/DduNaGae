package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Free_Board_Detail  extends AppCompatActivity {

    private TextView title;
    private TextView content;
    private TextView nickname;
    private EditText comment;

    private Button send;

    private ImageView photo;
    private ImageView profile;


    private String intent_title;
    private String intent_content;
    private String intent_nickname;
    private String intente_comment;
    private String intent_imageuri;
    private String intent_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_detail);

        title = (TextView)findViewById(R.id.free_board_detail_title) ;
        content = (TextView)findViewById(R.id.free_board_detail_content);
        nickname = (TextView)findViewById(R.id.writer_nickname);
        comment = (EditText)findViewById(R.id.free_board_detail_comment);
        send = (Button)findViewById(R.id.free_board_detail_post_comment);

        photo = (ImageView)findViewById(R.id.free_board_image);
        profile = (ImageView)findViewById(R.id.freeboard_detail_myprofile);





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

        intent_imageuri = intent.getStringExtra("profile");
        intent_uid = intent.getStringExtra("uid");


        Glide.with(Free_Board_Detail.this)
                .load(intent_imageuri)
                .apply(new RequestOptions().circleCrop())
                .into(photo);

        FirebaseDatabase.getInstance().getReference().child("users").child(intent_uid).child("imageUri").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String abc = snapshot.getValue(String.class);

                Glide.with(Free_Board_Detail.this)
                        .load(abc)
                        .apply(new RequestOptions().circleCrop())
                        .into(profile);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}
