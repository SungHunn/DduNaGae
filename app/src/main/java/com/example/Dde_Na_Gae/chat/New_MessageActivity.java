package com.example.Dde_Na_Gae.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.New_ChatMainActivity;
import com.example.Dde_Na_Gae.R;
import com.example.Dde_Na_Gae.Room_Name_Database;
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

    private String uid;
    private String chatRoomUid;
    private String destinatonUid;
    private String roomname;
    private String chatting_room_option_selector;
    DatabaseReference mDatabase;
    RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();  //채팅을 요구 하는 아아디 즉 단말기에 로그인된 UID
        button = (Button) findViewById(R.id.messageActivity_button);
        editText = (EditText) findViewById(R.id.messageActivity_editText);
        destinatonUid = getIntent().getStringExtra("chat-destinationUid"); // 채팅을 당하는 아이디
        roomname = getIntent().getStringExtra("room-name");
        chatting_room_option_selector = getIntent().getStringExtra("option_selector");
        recyclerView = (RecyclerView)findViewById(R.id.messageActivity_recyclerview);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);





        mDatabase = FirebaseDatabase.getInstance().getReference();


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
                            room_name_database(roomname, chatting_room_option_selector);
                            room_name_database_2(roomname, chatting_room_option_selector, destinatonUid);

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
        checkChatRoom();


    }



    public void room_name_database(String room_name, String Room_selector_option) {
        Room_Name_Database room_name_database = new Room_Name_Database();
        room_name_database.Room_name = room_name;
        room_name_database.Room_selector_option = Room_selector_option;
        mDatabase.child("users").child(uid).child("my_chatting_list").child("1대1 채팅방").child(room_name).setValue(room_name_database);

    }

    public void room_name_database_2(String room_name, String Room_selector_option, String master_uid) {
        Room_Name_Database room_name_database = new Room_Name_Database();
        room_name_database.Room_name = room_name;
        room_name_database.Room_selector_option = Room_selector_option;
        mDatabase.child("users").child(master_uid).child("my_chatting_list").child("1대1 채팅방").child(room_name).setValue(room_name_database);

    }


    void  checkChatRoom(){

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

            FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").child(roomname).child("talk").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    comments.clear();

                    for(DataSnapshot item : snapshot.getChildren()){
                        comments.add(item.getValue(ChatModel.Comment.class));
                    }
                    //메세지 새로고침
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);

            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

            if(comments.get(position).uid.equals(uid)){
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.rightbubble);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);


            }else{
                Glide.with(holder.itemView.getContext())
                        .load(userModel.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(messageViewHolder.imageView_profile);
                messageViewHolder.textView_name.setText(userModel.userName);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
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
                textView_message = (TextView)view.findViewById(R.id.messageItem_textView_message);
                textView_name = (TextView)view.findViewById(R.id.messageItem_textview_name);
                imageView_profile = (ImageView)view.findViewById(R.id.messageItem_imageview_profile);
                linearLayout_destination = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_destination);
                linearLayout_main = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_main);

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopPlay(); //이 액티비티에서 종료되어야 하는 활동 종료시켜주는 함수
        Intent intent = new Intent(New_MessageActivity.this, New_ChatMainActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

    private void stopPlay() {
    }

}