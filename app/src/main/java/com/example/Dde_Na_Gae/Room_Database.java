package com.example.Dde_Na_Gae;

public class Room_Database {

    public String chattingname;
    public String matching_sex;
    public String matching_age1;
    public String matching_age2;
    public String matching_pet_age;
    public String matching_pet_option;
    public String matching_room_option;
    public String matching_car_option;
    public Room_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Room_Database(String chattingname,String matching_sex,String matching_age1,String matching_age2,String matching_pet_age,String matching_pet_option,String matching_room_option,String matching_car_option) {
        this.chattingname = chattingname;
        this.matching_sex = matching_sex;
       this.matching_age1 = matching_age1;
       this.matching_age2 = matching_age2;
       this.matching_pet_age = matching_pet_age;
       this.matching_pet_option = matching_pet_option;
       this.matching_room_option = matching_room_option;
        this.matching_car_option = matching_car_option;
    }

}