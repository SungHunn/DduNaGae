package com.example.Dde_Na_Gae.model;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {

    public Map<String,Boolean> users = new HashMap<>(); //채팅방의 유저들
    public Map<String,Comment> comments = new HashMap<>();//채팅방의 대화내용
    public String room_name;
    public static class Comment {

        public String uid;
        public String message;
    }
}