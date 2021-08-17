package com.example.Dde_Na_Gae;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Maching_Room_detail extends AppCompatActivity {

    DatabaseReference mDatabase;
    public TextView Room_Name;
    public TextView master_nickname;
    public TextView master_age;
    public TextView master_sex;
    public TextView master_pet_age;
    public TextView master_pet_option;
    public TextView master_have_car;
    public TextView hope_age;
    public TextView hope_sex;
    public TextView hope_pet_age;
    public TextView hope_pet_option;
    public TextView hope_have_car;
    public String[] masterchild = {"nickname","myage","my_sex","petage","petweight","havecar"};
    public ImageView petprofile;
    public Button room_detail_chatting_list;
    public String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_room_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        master_nickname = (TextView)findViewById(R.id.room_detail_master);
        Room_Name = (TextView)findViewById(R.id.room_detail_room_name);
        master_age = (TextView)findViewById(R.id.room_detail_age);
        master_sex = (TextView)findViewById(R.id.room_detail_sex);
        master_pet_age = (TextView)findViewById(R.id.room_detail_pet_age);
        master_pet_option = (TextView)findViewById(R.id.room_detail_pet_option);
        master_have_car = (TextView)findViewById(R.id.room_detail_have_car);
        hope_age = (TextView)findViewById(R.id.h_room_detail_age);
        hope_sex = (TextView)findViewById(R.id.h_room_detail_sex);
        hope_pet_age = (TextView)findViewById(R.id.h_room_detail_pet_age);
        hope_pet_option = (TextView)findViewById(R.id.h_room_detail_pet_option);
        hope_have_car = (TextView)findViewById(R.id.h_room_detail_have_car);
        petprofile = (ImageView)findViewById(R.id.room_detail_room_image);
        room_detail_chatting_list = (Button)findViewById(R.id.room_detail_go_my_chatting_list);

    TextView[] masterdata = {master_nickname,master_age,master_sex,master_pet_age,master_pet_option, master_have_car};

    gettextview(masterchild,masterdata);

    Intent getintent = getIntent();
        String name1 = getintent.getExtras().getString("text_h_matching_sex");
        hope_sex.setText(name1);
        String name2 = getintent.getExtras().getString("text_h_matching_age");
        hope_age.setText(name2);
        String name3 = getintent.getExtras().getString("text_h_matching_pet_age");
        hope_pet_age.setText(name3);
        String name4 = getintent.getExtras().getString("text_h_matching_pet_option");
        hope_pet_option.setText(name4);
        String name5 = getintent.getExtras().getString("text_h_car_option");
        hope_have_car.setText(name5);
        String name6 = getintent.getExtras().getString("Room_Name");
        Room_Name.setText(name6);

        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageuri = dataSnapshot.getValue(String.class);
                Glide.with(Maching_Room_detail.this)
                        .load(imageuri)
                        .apply(new RequestOptions().circleCrop())
                        .into(petprofile);
                }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        room_detail_chatting_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), New_ChatMainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void gettextview(String[] child,TextView[] data){
        for(int i=0;i<6;i++){
            DatabaseReference data_master_nickname = mDatabase.child("users").child(uid).child(child[i]);
            int finalI = i;
            data_master_nickname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.getValue(String.class);
                    data[finalI].setText(name);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
    }
}


