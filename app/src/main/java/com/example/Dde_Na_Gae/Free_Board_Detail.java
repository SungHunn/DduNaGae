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
import com.example.Dde_Na_Gae.model.Article_Model;
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

    private String articleid;

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
        articleid =  intent.getStringExtra("articleid");

        System.out.println(articleid);

        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Article_Model article ;
                article = snapshot.getValue(Article_Model.class);
                title.setText(article.title);
                content.setText(article.content);
                nickname.setText(article.nickname);

                Glide.with(Free_Board_Detail.this)
                        .load(article.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(photo);

                FirebaseDatabase.getInstance().getReference().child("users").child(article.uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        String uri = snapshot.getValue(String.class);

                        Glide.with(Free_Board_Detail.this)
                                .load(uri)
                                .apply(new RequestOptions().circleCrop())
                                .into(profile);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });



            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}

