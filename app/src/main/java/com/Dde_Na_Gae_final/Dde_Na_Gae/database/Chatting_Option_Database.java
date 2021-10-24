package com.Dde_Na_Gae_final.Dde_Na_Gae.database;

public class Chatting_Option_Database {

    public String sex;
    public String pet_Option;
    public String have_car;
    public String area;

    public Chatting_Option_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Chatting_Option_Database(String sex, String have_car, String pet_Option, String area) {
        this.sex = sex;
        this.have_car = have_car;
        this.pet_Option = pet_Option;
        this.area = area;
    }

}