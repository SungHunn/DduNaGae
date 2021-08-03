package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class My_Information extends AppCompatActivity {

    private Button button;
    private EditText firstname1;
    private EditText lastname1;
    private EditText phone_num1;
    private EditText myage1;
    private EditText petage1;
    private EditText petweight1;
    private EditText petname1;
    private EditText unique1;
    private EditText nick_name;

    private DatabaseReference mDatabase;

    private DatabaseReference databaseReference;


    private Button overlapbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        firstname1 = (EditText)findViewById(R.id.first_name);
        lastname1 = (EditText)findViewById(R.id.last_name);
        phone_num1 = (EditText)findViewById(R.id.phone_number);
        myage1 = (EditText)findViewById(R.id.my_age);
        petage1 = (EditText)findViewById(R.id.pet_age);
        petweight1 = (EditText)findViewById(R.id.pet_weight);
        petname1 = (EditText)findViewById(R.id.pet_name);
        unique1 = (EditText)findViewById(R.id.uniqueness);
        nick_name = (EditText)findViewById(R.id.nick_name);






        Spinner pet_type_spinner = (Spinner)findViewById(R.id.pet_type);
        ArrayAdapter pettypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_type, android.R.layout.simple_spinner_item);
        pettypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pet_type_spinner.setAdapter(pettypeAdapter);
        String text_pet_type = pet_type_spinner.getSelectedItem().toString();


        Spinner pet_sex_spinner = (Spinner)findViewById(R.id.pet_sex);
        ArrayAdapter petsexAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_sex, android.R.layout.simple_spinner_item);
        pettypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pet_sex_spinner.setAdapter(petsexAdapter);
        String text_pet_sex = pet_sex_spinner.getSelectedItem().toString();


        Spinner car_spinner = (Spinner)findViewById(R.id.car);
        ArrayAdapter carAdapter = ArrayAdapter.createFromResource(this,
                R.array.car, android.R.layout.simple_spinner_item);
        carAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        car_spinner.setAdapter(carAdapter);
        String text_car_spinner = car_spinner.getSelectedItem().toString();


        button = (Button)findViewById(R.id.okay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
               /* if(overlapbutton.isEnabled()==false) {

                }
                else{
                    Toast.makeText(getApplicationContext(),"닉네임 중복 확인 필수!!",Toast.LENGTH_SHORT).show();//토스메세지 출력
                }
*/
                member_database(nick_name.getText().toString(), firstname1.getText().toString(), lastname1.getText().toString(), phone_num1.getText().toString(), myage1.getText().toString(), text_pet_type, petage1.getText().toString(), petweight1.getText().toString(), petname1.getText().toString(), text_pet_sex, text_car_spinner, unique1.getText().toString());
                Intent intent_main = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent_main);
                finish();
            }
        });
        //닉네임칸 함수
        nick_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                overlapbutton.setEnabled(true);
            }
        });
        //중복확인
        overlapbutton = (Button)findViewById(R.id.overlap_button);
        overlapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlap_nickname();
            }
        });

    }
    public void overlap_nickname(){
        databaseReference.child("ddunagae-prj-default-rtdb").child("users").child(nick_name.getText().toString()).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                if(value!=null){
                    Toast.makeText(getApplicationContext(),"중복된 닉네임입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                }
                else{
                    Toast.makeText(getApplicationContext(),"사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                    //버튼 비활성화
                    nick_name.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }



    public void member_database(String nick_name, String firstname, String lastname, String phone_num, String myage, String pettype, String petage, String petweight, String petname, String petsex, String havecar, String unique){
        Member_Database member_database = new Member_Database();
        member_database.nickname = nick_name;
        member_database.firstname = firstname;
        member_database.lastname = lastname;
        member_database.phone_num = phone_num;
        member_database.myage = myage;
        member_database.pettype = pettype;
        member_database.petage = petage;
        member_database.petweight = petweight;
        member_database.petname = petname;
        member_database.petsex = petsex;
        member_database.havecar = havecar;



        mDatabase.child("users").child(lastname).setValue(member_database)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Toast.makeText(getApplicationContext(),"회원가입 완료!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(getApplicationContext(),"오류 발생",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
