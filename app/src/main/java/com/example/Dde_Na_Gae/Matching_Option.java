package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.Dde_Na_Gae.database.Group_Room_Database;
import com.example.Dde_Na_Gae.database.Group_Room_Name_Database;
import com.example.Dde_Na_Gae.database.Room_Database;
import com.example.Dde_Na_Gae.database.Room_Name_Detail_Database;
import com.example.Dde_Na_Gae.fragment.Matching_Fragment;
import com.example.Dde_Na_Gae.model.ChatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Matching_Option extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Button personal_button;
    private Button group_button;
    private EditText Room_Name;
    private String uid;
    private String ChatRoomUid;
    private LinearLayout group_member_number;
    private ImageView matching_option_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_option);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        group_member_number = (LinearLayout) findViewById(R.id.h_group_member_number_text);
        personal_button = (Button) findViewById(R.id.personal_matching_okay);
        group_button = (Button) findViewById(R.id.group_matching_okay);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Room_Name = (EditText) findViewById(R.id.chatting_name);

        matching_option_back = (ImageView)findViewById(R.id.matching_option_back);
        matching_option_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), New_ChatMainActivity.class);
                startActivity(intent);
            }
        });

        Spinner h_matching_sex_spinner = (Spinner) findViewById(R.id.h_matching_sex);
        ArrayAdapter h_matching_sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_sex, android.R.layout.simple_spinner_item);
        h_matching_sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_sex_spinner.setAdapter(h_matching_sexAdapter);


        Spinner h_matching_age_spinner = (Spinner) findViewById(R.id.h_matching_age);
        ArrayAdapter h_matching_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_age, android.R.layout.simple_spinner_item);
        h_matching_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_age_spinner.setAdapter(h_matching_age_Adapter);


        Spinner h_matching_pet_age_spinner = (Spinner) findViewById(R.id.h_matching_pet_age);
        ArrayAdapter h_matching_pet_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_age, android.R.layout.simple_spinner_item);
        h_matching_pet_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_age_spinner.setAdapter(h_matching_pet_age_Adapter);

        Spinner h_matching_pet_option_spinner = (Spinner) findViewById(R.id.h_matching_pet_option);
        ArrayAdapter h_matching_pet_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_option, android.R.layout.simple_spinner_item);
        h_matching_pet_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_option_spinner.setAdapter(h_matching_pet_option_Adapter);


        Spinner h_car_option_spinner = (Spinner) findViewById(R.id.h_car_option);
        ArrayAdapter h_car_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_car_option, android.R.layout.simple_spinner_item);
        h_car_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_car_option_spinner.setAdapter(h_car_option_Adapter);


        Spinner matching_room_option_spinner = (Spinner) findViewById(R.id.matching_room_option);
        ArrayAdapter matching_room_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.matching_room_option, android.R.layout.simple_spinner_item);
        matching_room_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        matching_room_option_spinner.setAdapter(matching_room_option_Adapter);

        Spinner group_member_number_spinner = (Spinner) findViewById(R.id.h_group_member_number);
        ArrayAdapter group_member_number_Adapter = ArrayAdapter.createFromResource(this,
                R.array.group_member_number, android.R.layout.simple_spinner_item);
        group_member_number_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        group_member_number_spinner.setAdapter(group_member_number_Adapter);

        matching_room_option_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (matching_room_option_spinner.getSelectedItem().toString().equals("그룹 매칭방")) {
                    group_member_number.setVisibility(View.VISIBLE);
                    group_button.setVisibility(View.VISIBLE);
                    personal_button.setVisibility(View.GONE);
                } else if (matching_room_option_spinner.getSelectedItem().toString().equals("1대1 매칭방")) {
                    group_member_number.setVisibility(View.GONE);
                    group_button.setVisibility(View.GONE);
                    personal_button.setVisibility(View.VISIBLE);
                } else {
                    group_member_number.setVisibility(View.GONE);
                    group_button.setVisibility(View.GONE);
                    personal_button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //개인 채팅 클릭
        personal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String text_h_matching_sex = h_matching_sex_spinner.getSelectedItem().toString();
                String text_h_car_option = h_car_option_spinner.getSelectedItem().toString();
                String text_h_matching_age = h_matching_age_spinner.getSelectedItem().toString();
                String text_h_matching_pet_age = h_matching_pet_age_spinner.getSelectedItem().toString();
                String text_h_matching_pet_option = h_matching_pet_option_spinner.getSelectedItem().toString();
                String text_matching_room_option = matching_room_option_spinner.getSelectedItem().toString();

                String chatting_room_option_selector = text_h_matching_sex + text_h_matching_age + text_h_matching_pet_age + text_h_matching_pet_option + text_matching_room_option + text_h_car_option;
                String text_room_name = Room_Name.getText().toString();


                mDatabase.child("chatting_room").child(chatting_room_option_selector).child("chatting_room_option_selector").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            room_name_detail_database(text_room_name, uid, chatting_room_option_selector);

                        } else {
                            room_database(text_h_matching_sex, text_h_matching_age, text_h_matching_pet_age, text_h_matching_pet_option, text_matching_room_option, text_h_car_option, chatting_room_option_selector);
                            room_name_detail_database(text_room_name, uid, chatting_room_option_selector);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent_option = new Intent(getApplicationContext(), My_Matching_Room_detail.class);
                intent_option.putExtra("text_h_matching_sex", text_h_matching_sex);
                intent_option.putExtra("text_h_matching_age", text_h_matching_age);
                intent_option.putExtra("text_h_matching_pet_age", text_h_matching_pet_age);
                intent_option.putExtra("text_h_matching_pet_option", text_h_matching_pet_option);
                intent_option.putExtra("text_h_car_option", text_h_car_option);
                intent_option.putExtra("text_matching_room_option", text_matching_room_option);

                intent_option.putExtra("Room_Name", Room_Name.getText().toString());
                startActivity(intent_option);
            }
        });
        //그룹 채팅 클릭
        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String text_h_matching_sex = h_matching_sex_spinner.getSelectedItem().toString();
                String text_h_car_option = h_car_option_spinner.getSelectedItem().toString();
                String text_h_matching_age = h_matching_age_spinner.getSelectedItem().toString();
                String text_h_matching_pet_age = h_matching_pet_age_spinner.getSelectedItem().toString();
                String text_h_matching_pet_option = h_matching_pet_option_spinner.getSelectedItem().toString();
                String text_matching_room_option = matching_room_option_spinner.getSelectedItem().toString();
                String text_group_member_number = group_member_number_spinner.getSelectedItem().toString();
                String chatting_room_option_selector = text_h_matching_sex + text_h_matching_age + text_h_matching_pet_age + text_h_matching_pet_option + text_matching_room_option + text_h_car_option;
                String text_room_name = Room_Name.getText().toString();


                ChatModel chatModel = new ChatModel();
                chatModel.users.put(uid, true);


                mDatabase.child("chatting_room").child(chatting_room_option_selector).child("chatting_room_option_selector").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            room_name_detail_database(text_room_name, uid, chatting_room_option_selector);
                            add_group_database(chatting_room_option_selector, text_room_name, chatModel);

                        } else {
                            group_room_database(text_h_matching_sex, text_h_matching_age, text_h_matching_pet_age, text_h_matching_pet_option, text_matching_room_option, text_h_car_option, chatting_room_option_selector, text_group_member_number);
                            room_name_detail_database(text_room_name, uid, chatting_room_option_selector);
                            add_group_database(chatting_room_option_selector, text_room_name, chatModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent_option = new Intent(getApplicationContext(), My_Matching_Room_detail.class);
                intent_option.putExtra("text_h_matching_sex", text_h_matching_sex);
                intent_option.putExtra("text_h_matching_age", text_h_matching_age);
                intent_option.putExtra("text_h_matching_pet_age", text_h_matching_pet_age);
                intent_option.putExtra("text_h_matching_pet_option", text_h_matching_pet_option);
                intent_option.putExtra("text_h_car_option", text_h_car_option);
                intent_option.putExtra("text_matching_room_option", text_matching_room_option);
                intent_option.putExtra("text_group_member_number", text_group_member_number);
                intent_option.putExtra("Room_Name", Room_Name.getText().toString());
                startActivity(intent_option);


            }
        });


    }


    public void room_name_detail_database(String roomname, String masteruid, String chatting_room_option_selector) {
        Room_Name_Detail_Database room_name_detail_database = new Room_Name_Detail_Database();
        room_name_detail_database.Room_name = roomname;
        room_name_detail_database.master_uid = masteruid;
        room_name_detail_database.chatting_room_option_selector = chatting_room_option_selector;
        mDatabase.child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).setValue(room_name_detail_database);

    }

    public void group_room_name_database(String room_name, String Room_selector_option, String chatRoomUid) {

        Group_Room_Name_Database group_room_name_database = new Group_Room_Name_Database();
        group_room_name_database.Room_name = room_name;
        group_room_name_database.Room_selector_option = Room_selector_option;
        group_room_name_database.chatroomuid = chatRoomUid;
        mDatabase.child("users").child(uid).child("my_chatting_list").child("그룹 채팅방").child(room_name).setValue(group_room_name_database);


    }

    public void room_database(String matching_sex, String matching_age, String matching_pet_age, String matching_pet_option, String matching_room_option, String matching_car_option, String chatting_room_option_selector) {
        Room_Database room_database = new Room_Database();
        room_database.chatting_room_option_selector = chatting_room_option_selector;
        room_database.matching_age = matching_age;
        room_database.matching_pet_age = matching_pet_age;
        room_database.matching_pet_option = matching_pet_option;
        room_database.matching_room_option = matching_room_option;
        room_database.matching_sex = matching_sex;
        room_database.matching_car_option = matching_car_option;

        mDatabase.child("chatting_room").child(chatting_room_option_selector).setValue(room_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "생성 완료!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void group_room_database(String matching_sex, String matching_age, String matching_pet_age, String matching_pet_option, String matching_room_option, String matching_car_option, String chatting_room_option_selector, String text_group_member_number) {
        Group_Room_Database group_room_database = new Group_Room_Database();
        group_room_database.chatting_room_option_selector = chatting_room_option_selector;
        group_room_database.matching_age = matching_age;
        group_room_database.matching_pet_age = matching_pet_age;
        group_room_database.matching_pet_option = matching_pet_option;
        group_room_database.matching_room_option = matching_room_option;
        group_room_database.matching_sex = matching_sex;
        group_room_database.matching_car_option = matching_car_option;
        group_room_database.group_member_number = text_group_member_number;
        mDatabase.child("chatting_room").child(chatting_room_option_selector).setValue(group_room_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "생성 완료!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void add_group_database(String chatting_room_option_selector, String text_room_name, ChatModel chatModel) {
        mDatabase.child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(text_room_name).child("talk").push().setValue(chatModel);
        FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(text_room_name).child("talk").orderByChild("users/" + uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    ChatRoomUid = item.getKey();

                    group_room_name_database(text_room_name, chatting_room_option_selector, ChatRoomUid);

                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = "그룹 채팅이 시작 되었습니다!";
                    FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(text_room_name).child("talk").child(ChatRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {


                        }
                    });



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}