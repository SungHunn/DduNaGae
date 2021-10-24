package com.Dde_Na_Gae_final.Dde_Na_Gae.database;

public class Service_Database {

    public String uid;
    public String nickname;
    public String title;
    public String content;
    public String imageUri;
    public String writing_time;
    public String category;
    public String have_answer;


    public Service_Database(){

    }

    public Service_Database(String uid, String nickname, String title, String content, String imageUri,
                            String writing_time, String writing_category, String have_comment){

        this.uid = uid;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.imageUri = imageUri;
        this.writing_time = writing_time;
        this.category = writing_category;
        this.have_answer = have_answer;

    }
}
