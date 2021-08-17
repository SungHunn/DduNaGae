    package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Dde_Na_Gae.chat.New_MessageActivity;
import com.example.Dde_Na_Gae.model.ChatModel;
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

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public class Matching_Option extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Button button;
    private EditText Room_Name;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_option);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Room_Name = (EditText)findViewById(R.id.chatting_name);

        Spinner h_matching_sex_spinner = (Spinner)findViewById(R.id.h_matching_sex);
        ArrayAdapter h_matching_sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_sex, android.R.layout.simple_spinner_item);
        h_matching_sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_sex_spinner.setAdapter(h_matching_sexAdapter);


        Spinner h_matching_age_spinner = (Spinner)findViewById(R.id.h_matching_age);
        ArrayAdapter h_matching_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_age, android.R.layout.simple_spinner_item);
        h_matching_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_age_spinner.setAdapter(h_matching_age_Adapter);



        Spinner h_matching_pet_age_spinner = (Spinner)findViewById(R.id.h_matching_pet_age);
        ArrayAdapter h_matching_pet_age_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_age, android.R.layout.simple_spinner_item);
        h_matching_pet_age_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_age_spinner.setAdapter(h_matching_pet_age_Adapter);

        Spinner h_matching_pet_option_spinner = (Spinner)findViewById(R.id.h_matching_pet_option);
        ArrayAdapter h_matching_pet_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_matching_pet_option, android.R.layout.simple_spinner_item);
        h_matching_pet_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_matching_pet_option_spinner.setAdapter( h_matching_pet_option_Adapter);


        Spinner h_car_option_spinner = (Spinner)findViewById(R.id.h_car_option);
        ArrayAdapter h_car_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.h_car_option, android.R.layout.simple_spinner_item);
        h_car_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        h_car_option_spinner.setAdapter(h_car_option_Adapter);


        Spinner matching_room_option_spinner = (Spinner)findViewById(R.id.matching_room_option);
        ArrayAdapter matching_room_option_Adapter = ArrayAdapter.createFromResource(this,
                R.array.matching_room_option, android.R.layout.simple_spinner_item);
        matching_room_option_Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        matching_room_option_spinner.setAdapter(matching_room_option_Adapter);





        button = (Button)findViewById(R.id.matching_okay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String text_h_matching_sex =  h_matching_sex_spinner.getSelectedItem().toString();
               String text_h_car_option = h_car_option_spinner.getSelectedItem().toString();
               String text_h_matching_age =h_matching_age_spinner.getSelectedItem().toString();
               String text_h_matching_pet_age = h_matching_pet_age_spinner.getSelectedItem().toString();
               String text_h_matching_pet_option = h_matching_pet_option_spinner.getSelectedItem().toString();
               String text_matching_room_option = matching_room_option_spinner.getSelectedItem().toString();
               String chatting_room_option_selector = text_h_matching_sex+text_h_matching_age+text_h_matching_pet_age+text_h_matching_pet_option+text_matching_room_option+text_h_car_option;

               String text_room_name = Room_Name.getText().toString();
                Room_Name_Detail_Database room_name_detail_database = new Room_Name_Detail_Database(text_room_name,uid);
                room_database(text_h_matching_sex,text_h_matching_age,text_h_matching_pet_age,text_h_matching_pet_option,text_matching_room_option,text_h_car_option,chatting_room_option_selector);
                room_name_database(text_room_name,chatting_room_option_selector);
                FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").setValue(room_name_detail_database).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent_option = new Intent(getApplicationContext(), Maching_Room_detail.class);
                            intent_option.putExtra("text_h_matching_sex",text_h_matching_sex);
                            intent_option.putExtra("text_h_matching_age",text_h_matching_age);
                            intent_option.putExtra("text_h_matching_pet_age",text_h_matching_pet_age);
                            intent_option.putExtra("text_h_matching_pet_option",text_h_matching_pet_option);
                            intent_option.putExtra("text_h_car_option",text_h_car_option);
                            intent_option.putExtra("Room_Name",Room_Name.getText().toString());
                            startActivity(intent_option);
                        }
                    });

                }
        });



    }
    public void room_name_database(String room_name, String Room_selector_option) {
       Room_Name_Database room_name_database = new Room_Name_Database();
        room_name_database.Room_name = room_name;
        room_name_database.Room_selector_option = Room_selector_option;
        mDatabase.child("users").child(uid).child("my_chatting_list").child(Room_selector_option).child(room_name).setValue(room_name_database);

    }
    public void room_database( String matching_sex,String matching_age,String matching_pet_age,String matching_pet_option,String matching_room_option, String matching_car_option,String chatting_room_option_selector){
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
