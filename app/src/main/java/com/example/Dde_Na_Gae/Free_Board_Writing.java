package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.Dde_Na_Gae.fragment.Freeboard_Fragment;
import com.example.Dde_Na_Gae.model.RoomModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Free_Board_Writing extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private ImageView photo;
    private static final int PICK_FROM_ALBUM = 10;
    private Button okay;
    private String uid;
    private Uri imageUri;

    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_writing_page);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        title = (EditText)findViewById(R.id.article_title);
        content = (EditText)findViewById(R.id.article_content);

        photo = (ImageView) findViewById(R.id.article_image);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });



        okay = (Button)findViewById(R.id.writing_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseStorage.getInstance().getReference().child("Freeboard_Images").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        @SuppressWarnings("VisibleForTests")
                        Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                        while(!imageUrl.isComplete());


                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                String nickname = snapshot.getValue(String.class);

                                Timestamp timestamp = new Timestamp(System.currentTimeMillis() );

                                SimpleDateFormat sdf = new SimpleDateFormat ("yyyy년 MM월 dd일  a hh:mm:ss");
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                                Article_Database(uid, nickname,title.getText().toString(), content.getText().toString() , imageUrl.getResult().toString(), sdf.format(timestamp));


                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


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





        mDatabase.child("Free_Board").child(nickname +" + " + title).setValue(article_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...

                        Intent intent = new Intent(getApplicationContext(), Freeboard_Fragment.class);
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
