package com.example.Dde_Na_Gae.model;


import java.util.HashMap;
import java.util.Map;

public class RoomModel {
    public String Nickname;
    public String profileImageUrl;
    public String roomName;
    public String uid;

    public Map<String, ChatModel.Comment> comments = new HashMap<>();//채팅방의 대화내용


    public static class Comment {

        public String uid;
        public String message;
    }
}