package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Matching_Option extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Button button;
    private EditText chatting_room;

    private String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_option);

        chatting_room = (EditText)findViewById(R.id.chatting_name);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Spinner h_matching_sex_spinner = (Spinner)findViewById(R.id.h_matching_sex);
        ArrayAdapter h_matching_sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_sex, android.R.layout.simple_spinner_item);
        h_matching_sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_sex_spinner.setAdapter(h_matching_sexAdapter);
        String text_h_matching_sex = h_matching_sex_spinner.getSelectedItem().toString();

        Spinner h_matching_age1_spinner = (Spinner)findViewById(R.id.h_matching_age1);
        ArrayAdapter h_matching_age1_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_age1, android.R.layout.simple_spinner_item);
        h_matching_age1_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_age1_spinner.setAdapter(h_matching_age1_Adapter);
        String text_h_matching_age1 = h_matching_age1_spinner.getSelectedItem().toString();

        Spinner h_matching_age2_spinner = (Spinner)findViewById(R.id.h_matching_age2);
        ArrayAdapter h_matching_age2_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_age2, android.R.layout.simple_spinner_item);
        h_matching_age2_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_age2_spinner.setAdapter(h_matching_age2_Adapter);
        String text_h_matching_age2 = h_matching_age2_spinner.getSelectedItem().toString();

        Spinner h_matching_pet_age_spinner = (Spinner)findViewById(R.id.h_matching_pet_age);
        ArrayAdapter h_matching_pet_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_age, android.R.layout.simple_spinner_item);
        h_matching_pet_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_age_spinner.setAdapter(h_matching_pet_age_Adapter);
        String text_h_matching_pet_age = h_matching_pet_age_spinner.getSelectedItem().toString();

        Spinner h_matching_pet_option_spinner = (Spinner)findViewById(R.id.h_matching_pet_option);
        ArrayAdapter h_matching_pet_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_option, android.R.layout.simple_spinner_item);
        h_matching_pet_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_option_spinner.setAdapter( h_matching_pet_option_Adapter);
        String text_h_matching_pet_option = h_matching_pet_option_spinner.getSelectedItem().toString();

        Spinner h_car_option_spinner = (Spinner)findViewById(R.id.h_car_option);
        ArrayAdapter h_car_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_car_option, android.R.layout.simple_spinner_item);
        h_car_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_car_option_spinner.setAdapter(h_car_option_Adapter);
        String text_h_car_option = h_car_option_spinner.getSelectedItem().toString();

        Spinner matching_room_option_spinner = (Spinner)findViewById(R.id.matching_room_option);
        ArrayAdapter matching_room_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.matching_room_option, android.R.layout.simple_spinner_item);
        matching_room_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        matching_room_option_spinner.setAdapter(matching_room_option_Adapter);
        String text_matching_room_option = matching_room_option_spinner.getSelectedItem().toString();

        button = (Button)findViewById(R.id.matching_okay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room_database(chatting_room.getText().toString(),text_h_matching_sex,text_h_matching_age1,text_h_matching_age2,text_h_matching_pet_age,text_h_matching_pet_option,text_matching_room_option,text_h_car_option);
            }
        });


    }

    public void room_database(String chatting_room, String matching_sex,String matching_age1,String matching_age2,String matching_pet_age,String matching_pet_option,String matching_room_option, String matching_car_option){
        Room_Database room_database = new Room_Database();
     room_database.chattingname = chatting_room;
     room_database.matching_age1 = matching_age1;
     room_database.matching_age2 = matching_age2;
     room_database.matching_pet_age = matching_pet_age;
     room_database.matching_pet_option = matching_pet_option;
     room_database.matching_room_option = matching_room_option;
     room_database.matching_sex = matching_sex;
     room_database.matching_car_option = matching_car_option;


        mDatabase.child("users").child(uid).child(chatting_room).setValue(room_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"생성 완료!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
