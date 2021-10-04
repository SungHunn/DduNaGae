package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Dde_Na_Gae.database.Member_Database;
import com.example.Dde_Na_Gae.model.RoomModel;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class My_Information extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 10;
    private Button button;
    private EditText my_name;
    private EditText phone_num1;
    private EditText myage1;
    private EditText petage1;
    private EditText petweight1;
    private EditText petname1;
    private EditText unique1;
    private EditText nick_name;
    private ImageView pet_profile;
    private Uri imageUri;

    private DatabaseReference mDatabase;

    private String uid = FirebaseAuth.getInstance().getUid();


    private Button overlapbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        my_name = (EditText)findViewById(R.id.my_name);
        phone_num1 = (EditText)findViewById(R.id.phone_number);
        myage1 = (EditText)findViewById(R.id.my_age);
        petage1 = (EditText)findViewById(R.id.pet_age);
        petweight1 = (EditText)findViewById(R.id.pet_weight);
        petname1 = (EditText)findViewById(R.id.pet_name);
        unique1 = (EditText)findViewById(R.id.uniqueness);
        nick_name = (EditText)findViewById(R.id.nick_name);

        pet_profile = (ImageView) findViewById(R.id.information_imageview_profile);
        pet_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        Spinner my_sex_spinner = (Spinner)findViewById(R.id.my_sex);
        ArrayAdapter my_sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_sex, android.R.layout.simple_spinner_item);
        my_sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        my_sex_spinner.setAdapter(my_sexAdapter);


        Spinner pet_type_spinner = (Spinner)findViewById(R.id.pet_type);
        ArrayAdapter pettypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.pet_type, android.R.layout.simple_spinner_item);
        pettypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pet_type_spinner.setAdapter(pettypeAdapter);



        Spinner car_spinner = (Spinner)findViewById(R.id.car);
        ArrayAdapter carAdapter = ArrayAdapter.createFromResource(this,
                R.array.car, android.R.layout.simple_spinner_item);
        carAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        car_spinner.setAdapter(carAdapter);



        button = (Button)findViewById(R.id.okay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                String text_pet_type = pet_type_spinner.getSelectedItem().toString();
                String text_car_spinner = car_spinner.getSelectedItem().toString();
                String text_my_sex_spinner = my_sex_spinner.getSelectedItem().toString();

                if(overlapbutton.isEnabled()==false && my_name!=null  && phone_num1!=null && myage1!=null && petage1!=null && petweight1!=null && petname1 != null) {

                    FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            @SuppressWarnings("VisibleForTests")
                            Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                            while(!imageUrl.isComplete());

                            RoomModel roomModel = new RoomModel();
                            roomModel.profileImageUrl = imageUrl.getResult().toString();
                            roomModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            member_database(uid, nick_name.getText().toString(),text_my_sex_spinner, my_name.getText().toString(), phone_num1.getText().toString(), myage1.getText().toString(),roomModel.profileImageUrl,text_pet_type, petage1.getText().toString(), petweight1.getText().toString(), petname1.getText().toString(), text_car_spinner, unique1.getText().toString());
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"닉네임 중복 확인 필수!!",Toast.LENGTH_SHORT).show();//토스메세지 출력
                }


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
        mDatabase.child("users").child(nick_name.getText().toString()).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(getApplicationContext(),"중복된 닉네임입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                }
                else{
                    Toast.makeText(getApplicationContext(),"사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show();//토스메세지 출력
                    //버튼 비활성화
                    overlapbutton.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
    }



    public void member_database(String uid, String nick_name,String my_sex, String my_name, String phone_num, String myage, String imageUri, String pettype, String petage, String petweight, String petname, String havecar, String unique){
        Member_Database member_database = new Member_Database();
        member_database.uid = uid;
        member_database.nickname = nick_name;
        member_database.my_name = my_name;
        member_database.my_sex = my_sex;
        member_database.phone_num = phone_num;
        member_database.myage = myage;
        member_database.pettype = pettype;
        member_database.petage = petage;
        member_database.petweight = petweight;
        member_database.petname = petname;
        member_database.havecar = havecar;
        member_database.imageUri = imageUri;





        mDatabase.child("users").child(uid).setValue(member_database)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            pet_profile.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData();// 이미지 경로 원본
        }
    }
}