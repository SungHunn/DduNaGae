package com.example.Dde_Na_Gae.database;

public class Member_Database {

    public String uid;
    public String nickname;
    public String my_name;
    public String phone_num;
    public String myage;
    public String pettype;
    public String petage;
    public String petweight;
    public String petname;
    public String havecar;
    public String unique;
    public String imageUri;
    public String my_sex;

    public Member_Database() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Member_Database( String uid, String nickname, String my_sex, String my_name, String phone_num, String myage,String imageUri, String pettype, String petage, String petweight, String petname, String havecar, String unique) {
        this.imageUri = imageUri;
        this.my_sex = my_sex;
        this.nickname = nickname;
        this.my_name = my_name;;
       this.phone_num = phone_num;
       this.myage = myage;
       this.pettype = pettype;
       this.petage = petage;
       this.petweight = petweight;
       this.petname = petname;
       this.havecar = havecar;
       this.unique = unique;
    }

}