package com.example.Dde_Na_Gae;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Dde_Na_Gae.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class My_Page_Information extends AppCompatActivity {

    private TextView age;
    private TextView name;
    private TextView nickname;
    private TextView petage;
    private TextView petname;
    private TextView pettype;
    private TextView havecar;

    private Button change;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_information);

        age = (TextView)findViewById(R.id.information_age);
        name = (TextView)findViewById(R.id.information_name);
        nickname = (TextView)findViewById(R.id.information_nickname);
        petage = (TextView)findViewById(R.id.information_pet_age);
        petname = (TextView)findViewById(R.id.information_pet_name);
        pettype = (TextView)findViewById(R.id.information_pet_type);
        havecar = (TextView)findViewById(R.id.information_havecar);


        FirebaseDatabase.getInstance().getReference().child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                age.setText(userModel.myage + " 세");
                name.setText(userModel.my_name + " 님");
                nickname.setText(userModel.nickname);
                petage.setText(userModel.petage + " 세");
                petname.setText(userModel.petname + " 님");
                pettype.setText(userModel.pettype);
                havecar.setText(userModel.havecar);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }



}