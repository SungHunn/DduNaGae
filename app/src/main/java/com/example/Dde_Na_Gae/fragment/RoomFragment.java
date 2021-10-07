package com.example.Dde_Na_Gae.fragment;

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
import com.example.Dde_Na_Gae.Group_Matching_Room_detail;
import com.example.Dde_Na_Gae.Single_Matching_Room_detail;
import com.example.Dde_Na_Gae.R;
import com.example.Dde_Na_Gae.Room_Name_Detail_Database;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RoomFragment extends Fragment {

    String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public  String chatting_room_option_selector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        chatting_room_option_selector = getArguments().getString("chatting_room_option_selector");


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.search_room_fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new RoomFragmentRecyclerViewAdapter());


        return view;

    }
    class RoomFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Room_Name_Detail_Database> chatroommodels1;

        public RoomFragmentRecyclerViewAdapter() {
            chatroommodels1 = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    chatroommodels1.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Room_Name_Detail_Database chatroommodel1 = snapshot.getValue(Room_Name_Detail_Database.class);
                        if(chatroommodel1.master_uid.equals(myUid)){
                            continue;
                        }
                        chatroommodels1.add(chatroommodel1);
                    }
                    notifyDataSetChanged();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomlist,parent,false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final CustomViewHolder customViewHolder = (CustomViewHolder)holder;

            String masteruid = chatroommodels1.get(position).master_uid;
            FirebaseDatabase.getInstance().getReference().child("users").child(masteruid).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel userModel =  dataSnapshot.getValue(UserModel.class);

                    Glide.with(customViewHolder.itemView.getContext())
                            .load(userModel.imageUri)
                            .apply(new RequestOptions().circleCrop())
                            .into(customViewHolder.profileimage);


                    customViewHolder.textView_nickname.setText(userModel.nickname);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            String room_name = chatroommodels1.get(position).Room_name;
            customViewHolder.textView_roomname.setText(room_name);




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatroommodels1.get(position).chatting_room_option_selector).child("matching_room_option").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.getValue().toString().equals("그룹 매칭방")){
                                Intent intent = new Intent(getActivity(), Group_Matching_Room_detail.class);
                                intent.putExtra("masteruid", chatroommodels1.get(position).master_uid);
                                intent.putExtra("roomname", chatroommodels1.get(position).Room_name);
                                intent.putExtra("option_selector", chatroommodels1.get(position).chatting_room_option_selector);

                                startActivity(intent);



                            }
                            else{

                                Intent intent1 = new Intent(getActivity(), Single_Matching_Room_detail.class);
                                intent1.putExtra("masteruid", chatroommodels1.get(position).master_uid);
                                intent1.putExtra("roomname", chatroommodels1.get(position).Room_name);
                                intent1.putExtra("option_selector", chatroommodels1.get(position).chatting_room_option_selector);

                                startActivity(intent1);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });





                }
            });
        }

        @Override
        public int getItemCount() {
            return chatroommodels1.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {

            public TextView textView_roomname;
            public TextView textView_nickname;
            public ImageView profileimage;
            public CustomViewHolder(View view) {
                super(view);
                profileimage = (ImageView) view.findViewById(R.id.chatting_room_list_profile_photo);
                textView_nickname = (TextView)view.findViewById(R.id.chatting_room_list_nickname);
                textView_roomname = (TextView)view.findViewById(R.id.chatting_room_list_room_name);

            }
        }
    }

}