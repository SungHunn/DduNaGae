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
import com.example.Dde_Na_Gae.database.Room_Name_Database;
import com.example.Dde_Na_Gae.chat.Group_MessageActivity;
import com.example.Dde_Na_Gae.model.ChatModel;
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

public class My_Group_ChatFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_chat,container,false);

        RecyclerView recyclerView1  = (RecyclerView) view.findViewById(R.id.chatgroupFragment_recyclerview);
        recyclerView1.setAdapter(new GroupChatRecyclerViewAdapter());
        recyclerView1.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        return view;
    }



//그룹 리사이클러 뷰

    class GroupChatRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private ArrayList<String> destinationUser = new ArrayList<>();
        private List<ChatModel> chatModels = new ArrayList<>();
        private List<Room_Name_Database> RoomModels  = new ArrayList<>();
        private String uid;

        public GroupChatRecyclerViewAdapter() {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("my_chatting_list").child("그룹 채팅방").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    RoomModels.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        Room_Name_Database chatroommodel2 = snapshot1.getValue(Room_Name_Database.class);
                        RoomModels.add(chatroommodel2);

                    }
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
                    }
                    notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });



        }



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_chat,parent,false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,int position ) {

            final CustomViewHolder customViewHolder = (CustomViewHolder)holder;
            customViewHolder.group_textView_room.setText(RoomModels.get(position).Room_name);

            Intent intent = new Intent(getActivity(), Group_MessageActivity.class);

            FirebaseDatabase.getInstance().getReference().child("chatting_room").child(RoomModels.get(position).Room_selector_option).child("Room_Name").child(RoomModels.get(position).Room_name).child("master_uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String mstuid = dataSnapshot.getValue().toString();


                    FirebaseDatabase.getInstance().getReference().child("users").child(mstuid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserModel userModel =  dataSnapshot.getValue(UserModel.class);
                            Glide.with(customViewHolder.itemView.getContext())
                                    .load(userModel.imageUri)
                                    .apply(new RequestOptions().circleCrop())
                                    .into(customViewHolder.group_chatitem_imageview);

                            intent.putExtra("chat_masterUid",mstuid);
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



            if(chatModels.get(position).comments == null) {
                customViewHolder.group_textView_last_message.setText("채팅 내역이 없습니다");
            }
            else{
                Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
                commentMap.putAll(chatModels.get(position).comments);
                String lastMessageKey = (String) commentMap.keySet().toArray()[0];
                customViewHolder.group_textView_last_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    intent.putExtra("room_name",RoomModels.get(position).Room_name);
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

            public TextView group_chatitem_textview_member_num;
            public TextView group_chatitem_textview_member;
            public ImageView group_chatitem_imageview;
            public TextView group_textView_room;
            public TextView group_textView_last_message;
            public CustomViewHolder(View view) {
                super(view);
                group_chatitem_textview_member_num = (TextView)view.findViewById(R.id.group_chatitem_textview_member_num);
                group_chatitem_textview_member = (TextView)view.findViewById(R.id.group_chatitem_textview_member);
                group_chatitem_imageview = (ImageView) view.findViewById(R.id.group_chatitem_imageview);
                group_textView_room = (TextView)view.findViewById(R.id.group_chatitem_textview_title);
                group_textView_last_message = (TextView)view.findViewById(R.id.group_chatitem_textview_lastMessage);
            }
        }
    }


}