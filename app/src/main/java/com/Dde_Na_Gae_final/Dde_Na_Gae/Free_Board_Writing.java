package com.Dde_Na_Gae_final.Dde_Na_Gae;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.Dde_Na_Gae_final.Dde_Na_Gae.database.Article_Database;
import com.Dde_Na_Gae_final.Dde_Na_Gae.database.Article_No_Photo_Database;
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
    private ImageView go_back;

    private DatabaseReference mDatabase;


    private String category;
    private String category_review;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_writing_page);


        category_review = null;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        title = (EditText) findViewById(R.id.article_title);
        content = (EditText) findViewById(R.id.article_content);

        go_back = (ImageView)findViewById(R.id.free_board_writing_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        photo = (ImageView) findViewById(R.id.article_image);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        Spinner free_board_category_spinner = (Spinner) findViewById(R.id.free_board_category);
        ArrayAdapter free_board_category = ArrayAdapter.createFromResource(this,
                R.array.free_board_category, android.R.layout.simple_spinner_item);
        free_board_category.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        free_board_category_spinner.setAdapter(free_board_category);

        Spinner review_category_spinner = (Spinner) findViewById(R.id.review_category);
        ArrayAdapter review_category = ArrayAdapter.createFromResource(this,
                R.array.review_category_list, android.R.layout.simple_spinner_item);
        review_category.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        review_category_spinner.setAdapter(review_category);

        free_board_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1)
                    review_category_spinner.setVisibility(View.VISIBLE);
                else
                    review_category_spinner.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        okay = (Button) findViewById(R.id.writing_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = free_board_category_spinner.getSelectedItem().toString();
                if (category.equals("리뷰")) {
                    category_review = review_category_spinner.getSelectedItem().toString();
                }

                if (imageUri != null) {

                    FirebaseStorage.getInstance().getReference().child("Freeboard_Images").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            @SuppressWarnings("VisibleForTests")
                            Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                            while (!imageUrl.isComplete()) ;


                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    String nickname = snapshot.getValue(String.class);

                                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일  a hh:mm:ss");
                                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                                    Article_Database(uid, nickname, title.getText().toString(), content.getText().toString(), imageUrl.getResult().toString(), sdf.format(timestamp), category, category_review);

                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                    });
                } else {

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String nickname = snapshot.getValue(String.class);

                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일  a hh:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                            Article_No_Photo_Database(uid, nickname, title.getText().toString(), content.getText().toString(), sdf.format(timestamp), category, category_review);

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    public void Article_Database(String uid, String nickname, String title, String content, String imageUri, String writing_time, String category, String category_review) {

        Article_Database article_database = new Article_Database();
        article_database.uid = uid;
        article_database.nickname = nickname;
        article_database.title = title;
        article_database.content = content;
        article_database.imageUri = imageUri;
        article_database.writing_time = writing_time;
        article_database.have_comment = "none";

        article_database.category = category + "-" + category_review;

        mDatabase.child("Free_Board").push().setValue(article_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...

                        Intent intent = new Intent(getApplicationContext(), Freeboard_Activity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void Article_No_Photo_Database(String uid, String nickname, String title, String content, String writing_time, String category, String category_review) {

        Article_No_Photo_Database article_database = new Article_No_Photo_Database();
        article_database.uid = uid;
        article_database.nickname = nickname;
        article_database.title = title;
        article_database.content = content;
        article_database.writing_time = writing_time;
        article_database.have_comment = "none";

        article_database.category = category + "-" + category_review;

        mDatabase.child("Free_Board").push().setValue(article_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...

                        Intent intent = new Intent(getApplicationContext(), Freeboard_Activity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
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