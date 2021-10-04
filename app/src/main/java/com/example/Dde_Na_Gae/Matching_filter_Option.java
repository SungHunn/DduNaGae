package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Matching_filter_Option extends AppCompatActivity {


    private Button button;
    private String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_filter_option);



        Spinner h_matching_sex_spinner = (Spinner)findViewById(R.id.h_matching_sex_filter);
        ArrayAdapter h_matching_sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_sex, android.R.layout.simple_spinner_item);
        h_matching_sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_sex_spinner.setAdapter(h_matching_sexAdapter);


        Spinner h_matching_age_spinner = (Spinner)findViewById(R.id.h_matching_age_filter);
        ArrayAdapter h_matching_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_age, android.R.layout.simple_spinner_item);
        h_matching_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_age_spinner.setAdapter(h_matching_age_Adapter);



        Spinner h_matching_pet_age_spinner = (Spinner)findViewById(R.id.h_matching_pet_age_filter);
        ArrayAdapter h_matching_pet_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_age, android.R.layout.simple_spinner_item);
        h_matching_pet_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_age_spinner.setAdapter(h_matching_pet_age_Adapter);


        Spinner h_matching_pet_option_spinner = (Spinner)findViewById(R.id.h_matching_pet_option_filter);
        ArrayAdapter h_matching_pet_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_option, android.R.layout.simple_spinner_item);
        h_matching_pet_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_option_spinner.setAdapter( h_matching_pet_option_Adapter);


        Spinner h_car_option_spinner = (Spinner)findViewById(R.id.h_car_option_filter);
        ArrayAdapter h_car_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_car_option, android.R.layout.simple_spinner_item);
        h_car_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_car_option_spinner.setAdapter(h_car_option_Adapter);


        Spinner matching_room_option_spinner = (Spinner)findViewById(R.id.matching_room_option_filter);
        ArrayAdapter matching_room_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.matching_room_option, android.R.layout.simple_spinner_item);
        matching_room_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        matching_room_option_spinner.setAdapter(matching_room_option_Adapter);



        button = (Button)findViewById(R.id.matching_filter_okay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_h_matching_sex =  h_matching_sex_spinner.getSelectedItem().toString();
                String text_h_car_option = h_car_option_spinner.getSelectedItem().toString();
                String text_h_matching_age =h_matching_age_spinner.getSelectedItem().toString();
                String text_h_matching_pet_age = h_matching_pet_age_spinner.getSelectedItem().toString();
                String text_h_matching_pet_option = h_matching_pet_option_spinner.getSelectedItem().toString();
                String text_matching_room_option = matching_room_option_spinner.getSelectedItem().toString();

                String chatting_room_option_selector = text_h_matching_sex+text_h_matching_age+text_h_matching_pet_age+text_h_matching_pet_option+text_matching_room_option+text_h_car_option;

                Intent intent_matching_room_detail = new Intent(getApplicationContext(), Single_Matching_Room_detail.class);
                intent_matching_room_detail.putExtra("chatting_room_option_selector",chatting_room_option_selector);

                Intent intent_chattinglist = new Intent(getApplicationContext(), Chatting_List.class);
                intent_chattinglist.putExtra("chatting_room_option_selector",chatting_room_option_selector);
                startActivity(intent_chattinglist);
                finish();
            }
        });


    }



}
