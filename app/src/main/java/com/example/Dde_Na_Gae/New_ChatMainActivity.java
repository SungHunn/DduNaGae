package com.example.Dde_Na_Gae;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Dde_Na_Gae.fragment.My_Group_ChatFragment;
import com.example.Dde_Na_Gae.fragment.My_Personal_ChatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class New_ChatMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_chatactivity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.new_mainactivity_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_group_room:
                        getSupportFragmentManager().beginTransaction().replace(R.id.new_mainactivity_framelayout,new My_Group_ChatFragment()).commit();
                        return true;
                    case R.id.my_personal_room:
                        getSupportFragmentManager().beginTransaction().replace(R.id.new_mainactivity_framelayout,new My_Personal_ChatFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }
}
