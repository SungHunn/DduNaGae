package com.example.Dde_Na_Gae.model;

import java.util.HashMap;
import java.util.Map;

public class New_ChatModel {

    public Map<String,Boolean> users = new HashMap<>(); //채팅방의 유저들
    public Map<String,Comment> comments = new HashMap<>();//채팅방의 대화내용
    public Map<String,RoomName> roomname = new HashMap<>();//채팅방의 대화내용
    public String profileimageurl;

    public static class Comment {

        public String uid;
        public String message;
    }
    public static class RoomName {

        public String roomname;
    }
}
