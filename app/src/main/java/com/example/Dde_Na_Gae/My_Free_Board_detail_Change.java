package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.database.Article_Database;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class My_Free_Board_detail_Change extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private TextView nickname;
    private ImageView profile;
    private ImageView photo;

    private Button okay;

    private String articleid;
    private String uid;
    private Uri imageUri;

    private static final int PICK_FROM_ALBUM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_free_board_detail_change);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        nickname = (TextView) findViewById(R.id.my_writer_nickname_change);
        title = (EditText) findViewById(R.id.my_free_board_detail_title_change);
        content = (EditText) findViewById(R.id.my_free_board_detail_content_change);
        profile = (ImageView) findViewById(R.id.my_profile_image_change);

        okay = (Button)findViewById(R.id.my_free_board_detail_change_okay);

        photo = (ImageView) findViewById(R.id.my_free_board_detail_profile_image_change);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        Intent intent = getIntent();
        articleid = intent.getStringExtra("my_articleid");

        System.out.println(articleid);

        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Article_Model article;
                article = snapshot.getValue(Article_Model.class);
                title.setText(article.title);
                content.setText(article.content);
                nickname.setText(article.nickname);

                Glide.with(My_Free_Board_detail_Change.this)
                        .load(article.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(photo);

                FirebaseDatabase.getInstance().getReference().child("users").child(article.uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        String uri = snapshot.getValue(String.class);

                        Glide.with(My_Free_Board_detail_Change.this)
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

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseStorage.getInstance().getReference().child("Freeboard_Images").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        @SuppressWarnings("VisibleForTests")
                        Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                        while (!imageUrl.isComplete()) ;

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일  a hh:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                        Article_Database(uid, nickname.getText().toString(), title.getText().toString(), content.getText().toString(), imageUrl.getResult().toString(), sdf.format(timestamp));

                    }
                });
            }
        });
    }

    public void Article_Database(String uid,String nickname, String title, String content, String imageUri, String writing_time){

        Article_Database article_database = new Article_Database();
        article_database.uid = uid;
        article_database.nickname = nickname;
        article_database.title = title;
        article_database.content = content;
        article_database.imageUri = imageUri;
        article_database.writing_time = writing_time;


        Intent intent = getIntent();
        articleid = intent.getStringExtra("my_articleid");


        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).setValue(article_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...

                        Intent intent = new Intent(getApplicationContext(), My_Free_Board_List.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(getApplicationContext(),"오류 발생",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            photo.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData();// 이미지 경로 원본
        }
    }

}