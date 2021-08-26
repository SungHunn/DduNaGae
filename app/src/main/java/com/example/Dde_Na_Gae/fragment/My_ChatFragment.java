package com.example.Dde_Na_Gae.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.R;
import com.example.Dde_Na_Gae.Room_Name_Database;
import com.example.Dde_Na_Gae.Room_Name_Detail_Database;
import com.example.Dde_Na_Gae.chat.New_MessageActivity;
import com.example.Dde_Na_Gae.model.ChatModel;
import com.example.Dde_Na_Gae.model.RoomModel;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class My_ChatFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        RecyclerView recyclerView  = (RecyclerView) view.findViewById(R.id.chatFragment_recyclerview);
        recyclerView.setAdapter(new ChatRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));


        return view;
    }


    class ChatRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private ArrayList<String> destinationUser = new ArrayList<>();
        private List<ChatModel> chatModels = new ArrayList<>();
        private List<Room_Name_Database> RoomModels  = new ArrayList<>();
        private String uid;

        public ChatRecyclerViewAdapter() {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("my_chatting_list").child("1대1 채팅방").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RoomModels.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Room_Name_Database chatroommodel1 = snapshot.getValue(Room_Name_Database.class);


                        RoomModels.add(chatroommodel1);
                    }
                    notifyDataSetChanged();
                    System.out.println(RoomModels.size());
                    chatModels.clear();
                    for (int a = 0; a < RoomModels.size(); a++) {
                        FirebaseDatabase.getInstance().getReference().child("chatting_room").child(RoomModels.get(a).Room_selector_option).child("Room_Name").child(RoomModels.get(a).Room_name).child("talk").orderByChild("users/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                for (DataSnapshot item : snapshot.getChildren()) {
                                    chatModels.add(item.getValue(ChatModel.class));
                                }
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        System.out.println(chatModels.size());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final CustomViewHolder customViewHolder = (CustomViewHolder)holder;
            customViewHolder.textView_room.setText(  RoomModels.get(position).Room_name);

            FirebaseDatabase.getInstance().getReference().child("chatting_room").child(RoomModels.get(position).Room_selector_option).child("Room_Name").child(RoomModels.get(position).Room_name).child("master_uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String mstuid = dataSnapshot.getValue().toString();

                    FirebaseDatabase.getInstance().getReference().child("users").child(mstuid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserModel userModel =  dataSnapshot.getValue(UserModel.class);
                            Room_Name_Database roomNameDatabase = dataSnapshot.getValue(Room_Name_Database.class);
                            Glide.with(customViewHolder.itemView.getContext())
                                    .load(userModel.imageUri)
                                    .apply(new RequestOptions().circleCrop())
                                    .into(customViewHolder.chatitem_imageview);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
            commentMap.putAll(chatModels.get(position).comments);
            String lastMessageKey = (String) commentMap.keySet().toArray()[0];
            customViewHolder.textView_last_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);

            String destinationUid = null;
            for(String user: chatModels.get(position).users.keySet()){
                if(!user.equals(uid)){
                    destinationUid = user;
                    destinationUser.add(destinationUid);
                }
            }

            Intent intent = new Intent(getActivity(), New_MessageActivity.class);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intent.putExtra("chat-destinationUid",destinationUser.get(position));
                    intent.putExtra("room-name",RoomModels.get(position).Room_name);
                    intent.putExtra("option_selector",RoomModels.get(position).Room_selector_option);
                    ActivityOptions activityOptions = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        activityOptions = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fromright, R.anim.toleft);
                        startActivity(intent, activityOptions.toBundle());
                    }
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return chatModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {

            public ImageView chatitem_imageview;
            public TextView textView_room;
            public TextView textView_last_message;
            public CustomViewHolder(View view) {
                super(view);

                chatitem_imageview = (ImageView) view.findViewById(R.id.chatitem_imageview);
                textView_room = (TextView)view.findViewById(R.id.chatitem_textview_title);
                textView_last_message = (TextView)view.findViewById(R.id.chatitem_textview_lastMessage);
            }
        }
    }



}