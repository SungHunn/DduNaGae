package com.example.Dde_Na_Gae;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Dde_Na_Gae.fragment.Matching_Fragment;
import com.example.Dde_Na_Gae.fragment.My_Group_ChatFragment;
import com.example.Dde_Na_Gae.fragment.My_Personal_ChatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class New_ChatMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_chatactivity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.new_mainactivity_bottomnavigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.matching_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.new_mainactivity_framelayout,new Matching_Fragment()).commit();
                        return true;
                    case R.id.my_group_room:
                        if (user == null) {
                            Toast.makeText(New_ChatMainActivity.this, "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
                            bottomNavigationView.setSelectedItemId(R.id.matching_home);
                        }else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.new_mainactivity_framelayout,new My_Group_ChatFragment()).commit();
                            return true;
                        }
                    case R.id.my_personal_room:
                        if (user == null) {
                            Toast.makeText(New_ChatMainActivity.this, "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
                            bottomNavigationView.setSelectedItemId(R.id.matching_home);
                        }else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.new_mainactivity_framelayout,new My_Personal_ChatFragment()).commit();
                            return true;
                        }


                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.matching_home);
    }
}
