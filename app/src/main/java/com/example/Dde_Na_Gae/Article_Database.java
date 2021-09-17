package com.example.Dde_Na_Gae;

public class Article_Database {

    public String uid;
    public String nickname;
    public String title;
    public String content;
    public String imageUri;
    public String writing_time;
    public String category;
    public String category_review;


    public Article_Database(){

    }


    public Article_Database(String uid,String nickname, String title, String content, String imageUri,
                            String writing_time, String writing_category, String writing_category_review){

        this.uid = uid;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.imageUri = imageUri;
        this.writing_time = writing_time;
        this.category = writing_category;
        this.category_review = writing_category_review;

    }
}
