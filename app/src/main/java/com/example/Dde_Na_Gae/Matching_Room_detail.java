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
import com.example.Dde_Na_Gae.chat.New_MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Matching_Room_detail extends AppCompatActivity {

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
    public String[] hopechild = {"matching_age","matching_car_option","matching_pet_age","matching_pet_option","matching_sex"};
    public ImageView petprofile;
    public Button room_detail_go_chatting;
    public String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_matching_room_detail);

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
        room_detail_go_chatting = (Button)findViewById(R.id.room_detail_go_chatting);

        TextView[] masterdata = {master_nickname,master_age,master_sex,master_pet_age,master_pet_option, master_have_car};
        TextView[] hopedata = {hope_age,hope_sex,hope_pet_age,hope_pet_option, hope_have_car};
        Intent intent = getIntent();
        String master_uid = intent.getStringExtra("masteruid");
        String room_name =  intent.getStringExtra("roomname");

        gettextview(masterchild,masterdata);
        gettextview1(hopechild,hopedata);



        FirebaseDatabase.getInstance().getReference().child("users").child(master_uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageuri = dataSnapshot.getValue(String.class);
                Glide.with(Matching_Room_detail.this)
                        .load(imageuri)
                        .apply(new RequestOptions().circleCrop())
                        .into(petprofile);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




    }

    public void gettextview(String[] child,TextView[] data){
        Intent intent = getIntent();
        String master_uid = intent.getStringExtra("masteruid");
        for(int i=0;i<6;i++){
            DatabaseReference data_master_nickname = mDatabase.child("users").child(master_uid).child(child[i]);
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
    public void gettextview1(String[] child,TextView[] data) {
        Intent intent = getIntent();
        String chatting_room_option_selector = intent.getStringExtra("option_selector");
        for (int i = 0; i < 5; i++) {
            DatabaseReference data_master_nickname = mDatabase.child("chatting_room").child(chatting_room_option_selector).child(child[i]);
            int finalI = i;
            data_master_nickname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.getValue(String.class);
                    data[finalI].setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}

