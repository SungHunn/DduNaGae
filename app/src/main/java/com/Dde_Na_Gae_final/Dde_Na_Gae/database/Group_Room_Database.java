package com.Dde_Na_Gae_final.Dde_Na_Gae.database;

public class Group_Room_Database {


    public String matching_sex;
    public String matching_age;
    public String matching_pet_age;
    public String matching_pet_option;
    public String matching_room_option;
    public String matching_car_option;
    public String chatting_room_option_selector;
    public String group_member_number;

    public Group_Room_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Group_Room_Database(String matching_sex, String matching_age, String matching_pet_age, String matching_pet_option, String matching_room_option, String matching_car_option, String chatting_room_option_selector, String group_member_number) {

       this.chatting_room_option_selector = chatting_room_option_selector;
        this.matching_sex = matching_sex;
        this.matching_age = matching_age;
        this.matching_pet_age = matching_pet_age;
        this.matching_pet_option = matching_pet_option;
        this.matching_room_option = matching_room_option;
        this.matching_car_option = matching_car_option;
        this.group_member_number = group_member_number;
    }

}