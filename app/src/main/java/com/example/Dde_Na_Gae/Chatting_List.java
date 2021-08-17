package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.Dde_Na_Gae.fragment.RoomFragment;
public class Chatting_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_list);

        RoomFragment roomFragment = new RoomFragment();

    Intent getintent = getIntent();
    String chatting_room_option_selector = getintent.getExtras().getString("chatting_room_option_selector");
       FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       Bundle bundle = new Bundle();
       bundle.putString("chatting_room_option_selector",chatting_room_option_selector);

       roomFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.chatting_list_framelayout,new RoomFragment()).commit();
    }
}

