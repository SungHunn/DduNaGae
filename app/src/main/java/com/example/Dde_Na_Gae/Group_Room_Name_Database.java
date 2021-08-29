package com.example.Dde_Na_Gae;

public class Group_Room_Name_Database {


    public String Room_name;
    public String Room_selector_option;
    public String chatroomuid;
    public Group_Room_Name_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Group_Room_Name_Database(String room_name, String Room_selector_option, String chatroomuid) {

        this.Room_name = room_name;
        this.Room_selector_option = Room_selector_option;
        this.chatroomuid = chatroomuid;
    }

}