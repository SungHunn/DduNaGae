package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.Article_Model;
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
    private ImageView photo;
    private ImageView profile;
    private ImageView go_back;

    private Button change;
    private Button delete;

    private String articleid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_free_board_detail);

        title = (TextView)findViewById(R.id.my_free_board_detail_title) ;
        content = (TextView)findViewById(R.id.my_free_board_detail_content);
        nickname = (TextView)findViewById(R.id.my_writer_nickname);
        comment = (EditText)findViewById(R.id.my_free_board_detail_comment);
        send = (Button)findViewById(R.id.my_free_board_detail_post_comment);
        photo = (ImageView)findViewById(R.id.my_free_board_detail_profile_image);
        profile = (ImageView)findViewById(R.id.my_profile_image);

        go_back = (ImageView)findViewById(R.id.My_article_detail_img_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Free_Board_List.class);
                startActivity(intent);
            }
        });



        change = (Button)findViewById(R.id.my_free_board_detail_change);
        delete = (Button)findViewById(R.id.my_free_board_detail_delete);

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

                Glide.with(My_free_board_detail.this)
                        .load(article.imageUri)
                        .into(photo);

                FirebaseDatabase.getInstance().getReference().child("users").child(article.uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        String uri = snapshot.getValue(String.class);

                        Glide.with(My_free_board_detail.this)
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


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),My_Free_Board_List.class);
                FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).setValue(null);
                startActivity(intent1);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),My_Free_Board_detail_Change.class);
                intent.putExtra("my_articleid",articleid);


                startActivity(intent);
            }
        });
    }
}
