package com.Dde_Na_Gae_final.Dde_Na_Gae.database;

public class Room_Name_Detail_Database {


    public String Room_name;
    public String master_uid;
    public String chatting_room_option_selector;

    public Room_Name_Detail_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Room_Name_Detail_Database(String room_name, String master_uid, String chatting_room_option_selector) {
        this.chatting_room_option_selector = chatting_room_option_selector;
        this.Room_name = room_name;
        this.master_uid = master_uid;

    }

}