package com.example.Dde_Na_Gae;

public class Article_Database {

    public String uid;
    public String nickname;
    public String title;
    public String content;
    public String imageUri;


    public Article_Database(){

    }


    public Article_Database(String uid,String nickname, String title, String content, String imageUri){

        this.uid = uid;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.imageUri = imageUri;


    }
}
