package com.example.Dde_Na_Gae;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Dde_Na_Gae.chat.Group_MessageActivity;
import com.example.Dde_Na_Gae.chat.New_MessageActivity;
import com.example.Dde_Na_Gae.model.ChatModel;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Group_Matching_Room_detail extends AppCompatActivity {

    private RecyclerView recyclerView;

    DatabaseReference mDatabase;

    private TextView h_sex;
    private TextView h_age;
    private TextView h_pet_age;
    private TextView h_pet_type;
    private TextView have_car;
    private TextView room_title;

    private Button go_chattingroom;

    public String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public String[] hopechild = {"matching_age", "matching_sex", "matching_pet_age", "matching_pet_option", "matching_car_option"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_room_matching_detail);

        recyclerView = (RecyclerView) findViewById(R.id.room_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Group_Matching_Room_detail.this));
        recyclerView.setAdapter(new RecyclerViewAdapter());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        room_title = (TextView) findViewById(R.id.room_title1);
        h_sex = (TextView) findViewById(R.id.room_text2);
        h_age = (TextView) findViewById(R.id.room_text4);
        h_pet_age = (TextView) findViewById(R.id.room_text6);
        h_pet_type = (TextView) findViewById(R.id.room_text8);
        have_car = (TextView) findViewById(R.id.room_text10);

        TextView[] hopedata = {h_age, h_sex, h_pet_age, h_pet_type, have_car};

        Intent intent = getIntent();
        String master_uid = intent.getStringExtra("masteruid");
        String room_name = intent.getStringExtra("roomname");
        String chatting_room_option_selector = intent.getStringExtra("option_selector");

        gettextview1(hopechild, hopedata);


        go_chattingroom = (Button) findViewById(R.id.room_group_btn);

        go_chattingroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Group_MessageActivity.class);
                intent.putExtra("chat_masterUid", master_uid);
                intent.putExtra("room_name", room_name);
                intent.putExtra("option_selector", chatting_room_option_selector);
                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {


                    activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fromright, R.anim.toleft);
                    startActivity(intent, activityOptions.toBundle());


                    mDatabase.child("users").child(master_uid).child("my_chatting_list").child("그룹 채팅방").child(room_name).child("chatroomuid").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            String abc = snapshot.getValue(String.class);


                            ChatModel chatModel = new ChatModel();
                            chatModel.users.put(uid, true);


                            Map<String, Object> user = new HashMap<>();
                            user.put(uid, true);


                            mDatabase.child("chatting_room")
                                    .child(chatting_room_option_selector)
                                    .child("Room_Name").child(room_name).child("talk")
                                    .child(abc).child("users")
                                    .updateChildren(user, chatModel);

                            group_room_name_database(room_name, chatting_room_option_selector);

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                }
                startActivity(intent);

            }
        });


    }

    public void gettextview1(String[] child,TextView[] data) {

        Intent intent = getIntent();
        String chatting_room_option_selector = intent.getStringExtra("option_selector");
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            mDatabase.child("chatting_room").child(chatting_room_option_selector).child(child[finalI]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.getValue(String.class);
                    System.out.println(data[finalI]);
                    data[finalI].setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void group_room_name_database(String room_name, String Room_selector_option) {
        Room_Name_Database room_name_database = new Room_Name_Database();
        room_name_database.Room_name = room_name;
        room_name_database.Room_selector_option = Room_selector_option;
        mDatabase.child("users").child(uid).child("my_chatting_list").child("그룹 채팅방").child(room_name).setValue(room_name_database);
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<String> members;

        Intent intent = getIntent();
        String master_uid = intent.getStringExtra("masteruid");
        String room_name =  intent.getStringExtra("roomname");
        String chatting_room_option_selector = intent.getStringExtra("option_selector");

        public RecyclerViewAdapter() {

            members = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("users").child(master_uid).child("my_chatting_list").child("그룹 채팅방").child(room_name).child("chatroomuid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    String chatroomuid1 = snapshot.getValue().toString();
                    getmemberslist(chatroomuid1);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        public void getmemberslist(String chatroomuid) {
            FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(room_name).child("talk").child(chatroomuid).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    members.clear();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        members.add(item.getKey());
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });
        }

        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom_people, parent, false);
            return new ProfileViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

            ProfileViewHolder profileViewHolder = ((ProfileViewHolder) holder);

            FirebaseDatabase.getInstance().getReference().child("users").child(members.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    profileViewHolder.group_member_nickname.setText(userModel.nickname);

                    Glide.with(holder.itemView.getContext())
                            .load(userModel.imageUri)
                            .into(profileViewHolder.group_member_profile);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference().child("users").child(members.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserModel userModel = snapshot.getValue(UserModel.class);

                            Intent intent1 = new Intent(getApplicationContext(), Profile_Detail.class);
                            intent1.putExtra("destinationuid", userModel.uid);
                            startActivity(intent1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });


        }

        @Override
        public int getItemCount() {
            return members.size();
        }

        private class ProfileViewHolder extends RecyclerView.ViewHolder {

            public TextView group_member_nickname;
            public ImageView group_member_profile;


            public ProfileViewHolder(View view) {
                super(view);

                group_member_nickname = (TextView) view.findViewById(R.id.memeber_nickname);
                group_member_profile = (ImageView) view.findViewById(R.id.memeber_profile);

            }
        }

    }


}