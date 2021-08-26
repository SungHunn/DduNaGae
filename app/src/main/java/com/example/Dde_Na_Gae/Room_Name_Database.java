package com.example.Dde_Na_Gae;

import com.example.Dde_Na_Gae.model.ChatModel;

import java.util.Map;

public class Room_Name_Database {


    public String Room_name;
    public String Room_selector_option;


    public Room_Name_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Room_Name_Database(String room_name,String Room_selector_option) {

        this.Room_name = room_name;
        this.Room_selector_option = Room_selector_option;

    }

}