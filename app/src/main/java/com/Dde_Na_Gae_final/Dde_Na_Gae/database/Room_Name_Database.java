package com.Dde_Na_Gae_final.Dde_Na_Gae.database;

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