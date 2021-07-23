package com.example.Dde_Na_Gae;

public class Member_Database {

    public String firstname;
    public String lastname;
    public String phone_num;
    public String myage;
    public String pettype;
    public String petage;
    public String petweight;
    public String petname;
    public String petsex;
    public String havecar;
    public String unique;

    public Member_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Member_Database(String firstname, String lastname, String phone_num, String myage, String pettype, String petage, String petweight, String petname,String petsex, String havecar, String unique) {
       this.firstname = firstname;
       this.lastname = lastname;
       this.phone_num = phone_num;
       this.myage = myage;
       this.pettype = pettype;
       this.petage = petage;
       this.petweight = petweight;
       this.petname = petname;
       this.petsex = petsex;
       this.havecar = havecar;
       this.unique = unique;
    }

}