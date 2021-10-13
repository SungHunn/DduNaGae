package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class My_Page extends AppCompatActivity {

    ImageView myprofile;
    String myuid;
    Uri url;
    TextView myname;
    BottomNavigationView bottomNavigationView;
    TextView go_my_article;
    TextView go_my_info;
    TextView secession;
    TextView service_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        bottomNavigationBar();
        myprofile = (ImageView)findViewById(R.id.my_page_img);
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child("users").child(myuid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                url = Uri.parse(dataSnapshot.getValue().toString());
                Glide.with(getApplicationContext())
                        .load(url)
                        .apply(new RequestOptions().circleCrop())
                        .into(myprofile);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });

        myname = (TextView)findViewById(R.id.my_page_nickname);

        FirebaseDatabase.getInstance().getReference().child("users").child(myuid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                myname.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        go_my_article = (TextView) findViewById(R.id.my_page_write);

        go_my_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Free_Board_List.class);
                startActivity(intent);
            }
        });

        service_center = findViewById(R.id.my_page_service_center);

        service_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Service_Center.class);
                startActivity(intent);
            }
        });

        go_my_info = (TextView)findViewById(R.id.my_page_info);

        go_my_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Page_Information.class);
                startActivity(intent);
            }
        });

        secession = findViewById(R.id.my_page_secession);

        secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(My_Page.this, "회원 탈퇴 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login_New_Page.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

    }

    private void bottomNavigationBar() {
        // 바텀네비게이션바 클릭 이벤트 삽입 구간
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.matching:
                        Intent intent = new Intent(getApplicationContext(), New_ChatMainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    /*case R.id.mylocation:
                        //Intent intent3 = new Intent(getApplicationContext(), );
                        //startActivity(intent3);
                        break;*/
                }
                return false;
            }
        });


    }

}
