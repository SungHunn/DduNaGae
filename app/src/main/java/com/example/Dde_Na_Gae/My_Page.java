package com.example.Dde_Na_Gae;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class My_Page extends AppCompatActivity {

    ImageView myprofile;
    String myuid;
    Uri url;
    TextView myname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        myprofile = (ImageView)findViewById(R.id.my_profile);
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("users").child(myuid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                url = Uri.parse(dataSnapshot.getValue().toString());
                Glide.with(getApplicationContext())
                        .load(url)
                        .apply(new RequestOptions().circleCrop())
                        .into(myprofile);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }


        });

        myname = (TextView)findViewById(R.id.myname);

        FirebaseDatabase.getInstance().getReference().child("users").child(myuid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                myname.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
