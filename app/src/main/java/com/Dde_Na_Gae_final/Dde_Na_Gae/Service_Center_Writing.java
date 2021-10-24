package com.Dde_Na_Gae_final.Dde_Na_Gae;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Dde_Na_Gae_final.Dde_Na_Gae.database.Service_Database;
import com.Dde_Na_Gae_final.Dde_Na_Gae.database.Service_No_Photo_Database;
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


public class Service_Center_Writing extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private ImageView photo;
    private static final int PICK_FROM_ALBUM = 10;
    private Button okay;
    private String uid;
    private Uri imageUri;

    private DatabaseReference mDatabase;


    private String category;
    private String category_review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_center_writing);

        category_review = null;


        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        title = (EditText) findViewById(R.id.service_title);
        content = (EditText) findViewById(R.id.service_content);

        photo = (ImageView) findViewById(R.id.service_image);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        Spinner service_spinner = (Spinner) findViewById(R.id.service_category);

        ArrayAdapter service_center_category = ArrayAdapter.createFromResource(this,
                R.array.service_center_list, android.R.layout.simple_spinner_item);
        service_center_category.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        service_spinner.setAdapter(service_center_category);

        okay = (Button) findViewById(R.id.service_writing_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = service_spinner.getSelectedItem().toString();


                if (imageUri != null) {
                    FirebaseStorage.getInstance().getReference().child("Service_Center_Images").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
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

                                    Service_Database(uid, nickname, title.getText().toString(), content.getText().toString(), imageUrl.getResult().toString(), sdf.format(timestamp), category);

                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }else {

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String nickname = snapshot.getValue(String.class);

                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일  a hh:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

                            Service_No_Photo_Database(uid, nickname, title.getText().toString(), content.getText().toString(), sdf.format(timestamp), category);

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    public void Service_Database(String uid, String nickname, String title, String content, String imageUri, String writing_time, String category) {

        Service_Database service_database = new Service_Database();
        service_database.uid = uid;
        service_database.nickname = nickname;
        service_database.title = title;
        service_database.content = content;
        service_database.imageUri = imageUri;
        service_database.writing_time = writing_time;
        service_database.have_answer = "none";
        service_database.category = category;

        mDatabase.child("Service_Center").push().setValue(service_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), Service_Center.class);
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

    public void Service_No_Photo_Database(String uid, String nickname, String title, String content, String writing_time, String category) {

        Service_No_Photo_Database service_database = new Service_No_Photo_Database();
        service_database.uid = uid;
        service_database.nickname = nickname;
        service_database.title = title;
        service_database.content = content;
        service_database.writing_time = writing_time;
        service_database.have_answer = "none";
        service_database.category = category;

        mDatabase.child("Service_Center").push().setValue(service_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), Service_Center.class);
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
