package com.example.Dde_Na_Gae.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.database.Group_Room_Name_Database;
import com.example.Dde_Na_Gae.New_ChatMainActivity;
import com.example.Dde_Na_Gae.R;
import com.example.Dde_Na_Gae.fragment.My_Personal_ChatFragment;
import com.example.Dde_Na_Gae.model.ChatModel;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class New_MessageActivity extends AppCompatActivity {


    private Button button;
    private EditText editText;
    ImageButton single_chat_hbg;
    Button chattingroom_exit;
    ImageButton singlechat_goback;

    private TextView chat_text;
    private String uid;
    private String chatRoomUid;
    private String destinatonUid;
    private String roomname;
    private String chatting_room_option_selector;
    DatabaseReference mDatabase;
    private RecyclerView recyclerView;

    private TextView member_text;
    private ImageView member_profile;

    DrawerLayout drawerLayout;
    View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);


        destinatonUid = getIntent().getStringExtra("chat-destinationUid"); // 채팅을 당하는 아이디

        //햄버거
        drawerLayout = findViewById(R.id.single_chat_drawlayout);
        drawerView = findViewById(R.id.single_chatting_drawer);

        chat_text = (TextView)findViewById(R.id.chatting_text);

        singlechat_goback = (ImageButton)findViewById(R.id.single_chattingroom_back);
        singlechat_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        single_chat_hbg = (ImageButton) findViewById(R.id.single_talkmenu_open);

        single_chat_hbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(drawerView);

            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                return true;
            }
        });

        member_text = (TextView)findViewById(R.id.single_chatroom_member_nickname);
        member_profile = (ImageView)findViewById(R.id.single_chatroom_memeber_profile);

        FirebaseDatabase.getInstance().getReference().child("users").child(destinatonUid).addListenerForSingleValueEvent(new ValueEventListener() {

            UserModel userModel = new UserModel();

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                userModel = snapshot.getValue(UserModel.class);

                member_text.setText(userModel.nickname);

                Glide.with(New_MessageActivity.this)
                        .load(userModel.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(member_profile);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();  //채팅을 요구 하는 아아디 즉 단말기에 로그인된 UID
        button = (Button) findViewById(R.id.messageActivity_button);
        editText = (EditText) findViewById(R.id.messageActivity_editText);

        roomname = getIntent().getStringExtra("room-name");
        chatting_room_option_selector = getIntent().getStringExtra("option_selector");

        recyclerView = (RecyclerView)findViewById(R.id.single_messageActivity_recyclerview);




        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatModel chatModel = new ChatModel();
                chatModel.users.put(uid,true);
                chatModel.users.put(destinatonUid,true);

                if(chatRoomUid == null){
                    button.setEnabled(false);
                    FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            checkChatRoom();


                        }
                    });
                }else {

                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = editText.getText().toString();
                    FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").child(chatRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            editText.setText("");

                        }
                    });

                }

            }
        });
        checkChatRoom1();


        chattingroom_exit = (Button)findViewById(R.id.single_room_exit);

        chattingroom_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(New_MessageActivity.this);
                alt_bld.setMessage("방을 나가시겠습니까??").setCancelable(false)
                        .setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("my_chatting_list").child("1대1 채팅방").child(roomname).setValue(null);
                                        FirebaseDatabase.getInstance().getReference().child("users").child(destinatonUid).child("my_chatting_list").child("1대1 채팅방").child(roomname).child("chatroomuid").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                chatRoomUid = snapshot.getValue().toString();
                                                FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").child(chatRoomUid).child("users").child(uid).setValue(null);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });
                                        onBackPressed();
                                    }
                                }).setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                // 대화창 클릭시 뒷 배경 어두워지는 것 막기
                //alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                alert.setTitle("로그아웃");
                alert.setIcon(R.drawable.logo);
                // 대화창 배경 색 설정
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 220, 213)));
                alert.show();
            }
        });

    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };


    public void room_name_database(String room_name, String Room_selector_option, String chatRoomUid) {
        Group_Room_Name_Database room_name_database = new Group_Room_Name_Database();
        room_name_database.Room_selector_option = Room_selector_option;
        room_name_database.chatroomuid = chatRoomUid;
        String room_name_uid = room_name+"@"+uid;
        room_name_database.Room_name = room_name_uid;

        mDatabase.child("users").child(uid).child("my_chatting_list").child("1대1 채팅방").child(room_name_uid).setValue(room_name_database);
    }

    public void room_name_database_2(String room_name, String Room_selector_option, String master_uid, String chatRoomUid) {
        Group_Room_Name_Database room_name_database = new Group_Room_Name_Database();
        room_name_database.Room_selector_option = Room_selector_option;
        room_name_database.chatroomuid = chatRoomUid;
        String room_name_uid = room_name+"@"+uid;
        room_name_database.Room_name = room_name_uid;
        mDatabase.child("users").child(master_uid).child("my_chatting_list").child("1대1 채팅방").child(room_name_uid).setValue(room_name_database);

    }


    void  checkChatRoom(){

        FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    ChatModel  chatModel = item.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(destinatonUid)){
                        chatRoomUid = item.getKey();


                        room_name_database(roomname, chatting_room_option_selector, chatRoomUid);
                        room_name_database_2(roomname, chatting_room_option_selector, destinatonUid, chatRoomUid);

                        button.setEnabled(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(New_MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void  checkChatRoom1(){

        FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    ChatModel  chatModel = item.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(destinatonUid)){
                        chatRoomUid = item.getKey();
                        button.setEnabled(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(New_MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<ChatModel.Comment> comments;
        UserModel userModel;
        public  RecyclerViewAdapter() {
            comments = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("users").child(destinatonUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    userModel = snapshot.getValue(UserModel.class);
                    getMessageList();



                }


                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });



        }



        void getMessageList(){
            Intent intent = getIntent();
            String key = intent.getStringExtra("key");
            if(key.equals(1)) {
                String chatroom_uid = intent.getStringExtra("chatroom_uid");

                FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").child(chatroom_uid).child("comments").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        comments.clear();

                        for (DataSnapshot item : snapshot.getChildren()) {
                            comments.add(item.getValue(ChatModel.Comment.class));
                        }
                        //메세지 새로고침
                        notifyDataSetChanged();

                        recyclerView.scrollToPosition(comments.size() - 1);


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
            else
            {
                FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        comments.clear();

                        for (DataSnapshot item : snapshot.getChildren()) {
                            comments.add(item.getValue(ChatModel.Comment.class));
                        }
                        //메세지 새로고침
                        notifyDataSetChanged();

                        recyclerView.scrollToPosition(comments.size() - 1);


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        }


        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);

            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder,final int position) {

            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

            if(comments.get(position).uid.equals(uid)){
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.rightbubble);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);


            }
            if(comments.get(position).uid.equals(destinatonUid)){

                Glide.with(holder.itemView.getContext())
                        .load(userModel.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(messageViewHolder.imageView_profile);

                messageViewHolder.textView_name.setText(userModel.nickname);

                messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.leftbubble);
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setTextSize(25);
            }




        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_message;
            public TextView textView_name;
            public ImageView imageView_profile;
            public LinearLayout linearLayout_destination;
            public LinearLayout linearLayout_main;

            public MessageViewHolder(View view) {
                super(view);
                textView_message = (TextView)view.findViewById(R.id.single_messageItem_textView_message);
                textView_name = (TextView)view.findViewById(R.id.single_messageItem_textview_name);
                imageView_profile = (ImageView)view.findViewById(R.id.single_messageItem_imageview_profile);

                linearLayout_destination = (LinearLayout)view.findViewById(R.id.single_messageItem_linearlayout_destination);
                linearLayout_main = (LinearLayout)view.findViewById(R.id.single_messageItem_linearlayout_main);

            }
        }
    }

}