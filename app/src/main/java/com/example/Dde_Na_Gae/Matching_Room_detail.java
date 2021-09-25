package com.example.Dde_Na_Gae;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.chat.Group_MessageActivity;
import com.example.Dde_Na_Gae.chat.New_MessageActivity;
import com.example.Dde_Na_Gae.model.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kakao.usermgmt.StringSet.user_id;

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
    public LinearLayout room_detail_member_num_layout;
    public TextView room_detail_member_number;
    public String[] masterchild = {"nickname","myage","my_sex","petage","petweight","havecar"};
    public String[] hopechild = {"matching_age","matching_sex","matching_pet_age","matching_pet_option","matching_car_option"};
    public ImageView petprofile;
    public Button single_room_detail_go_chatting;
    public Button group_room_detail_go_chatting;
    public String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public String imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_room_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        room_detail_member_number =(TextView)findViewById(R.id.room_detail_member_number);
        room_detail_member_num_layout = (LinearLayout)findViewById(R.id.room_detail_member_num_layout);
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
        single_room_detail_go_chatting = (Button)findViewById(R.id.single_room_detail_go_chatting);
        group_room_detail_go_chatting = (Button)findViewById(R.id.group_room_detail_go_chatting);

        TextView[] masterdata = {master_nickname,master_age,master_sex,master_pet_age,master_pet_option, master_have_car};
        TextView[] hopedata = {hope_age,hope_sex,hope_pet_age,hope_pet_option, hope_have_car};
        Intent intent = getIntent();
        String master_uid = intent.getStringExtra("masteruid");
        String room_name =  intent.getStringExtra("roomname");
        String chatting_room_option_selector = intent.getStringExtra("option_selector");

        gettextview(masterchild,masterdata);
        gettextview1(hopechild,hopedata);
        Room_Name.setText(room_name);


        FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("matching_room_option").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals("그룹 매칭방")){
                    room_detail_member_num_layout.setVisibility(View.VISIBLE);
                    group_room_detail_go_chatting.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("group_member_number").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            room_detail_member_number.setText(snapshot.getValue().toString());
                        }


                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
                else{
                    single_room_detail_go_chatting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });







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


        single_room_detail_go_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), New_MessageActivity.class);
                intent.putExtra("chat-destinationUid", master_uid);
                intent.putExtra("room-name",room_name);
                intent.putExtra("option_selector",chatting_room_option_selector);
                intent.putExtra("key","0");
                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {


                    activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fromright, R.anim.toleft);
                    startActivity(intent, activityOptions.toBundle());






                }
                startActivity(intent);
            }
        });

        group_room_detail_go_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Group_MessageActivity.class);
                intent.putExtra("chat_masterUid", master_uid);
                intent.putExtra("room_name",room_name);
                intent.putExtra("option_selector",chatting_room_option_selector);
                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {


                    activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fromright, R.anim.toleft);
                    startActivity(intent, activityOptions.toBundle());


                    mDatabase.child("users").child(master_uid).child("my_chatting_list").child("그룹 채팅방").child(room_name).child("chatroomuid").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            String abc= snapshot.getValue(String.class);


                            ChatModel chatModel = new ChatModel();
                            chatModel.users.put(uid, true);


                            Map<String, Object> user = new HashMap<>();
                            user.put(uid, true);


                            mDatabase.child("chatting_room")
                                    .child(chatting_room_option_selector)
                                    .child("Room_Name").child(room_name).child("talk")
                                    .child(abc).child("users")
                                    .updateChildren(user, chatModel);

                            group_room_name_database(room_name,chatting_room_option_selector);

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                }
                startActivity(intent);
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
    public void group_room_name_database(String room_name, String Room_selector_option) {
        Room_Name_Database room_name_database = new Room_Name_Database();
        room_name_database.Room_name = room_name;
        room_name_database.Room_selector_option = Room_selector_option;
        mDatabase.child("users").child(uid).child("my_chatting_list").child("그룹 채팅방").child(room_name).setValue(room_name_database);
    }
}