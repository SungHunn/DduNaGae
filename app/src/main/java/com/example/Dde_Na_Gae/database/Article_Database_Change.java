package com.example.Dde_Na_Gae.database;

public class Article_Database_Change {

    public String uid;
    public String title;
    public String content;
    public String writing_time;


    public Article_Database_Change(){

    }


    public Article_Database_Change(String uid, String title, String content, String writing_time){

        this.uid = uid;
        this.title = title;
        this.content = content;
        this.writing_time = writing_time;

    }
}

