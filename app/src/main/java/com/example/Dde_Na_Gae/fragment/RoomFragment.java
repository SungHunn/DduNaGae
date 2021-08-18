package com.example.Dde_Na_Gae.fragment;

        import android.app.ActivityOptions;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;


        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;


        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.RequestOptions;
        import com.example.Dde_Na_Gae.Member_Database;
        import com.example.Dde_Na_Gae.R;
        import com.example.Dde_Na_Gae.Room_Name_Detail_Database;
        import com.example.Dde_Na_Gae.chat.MessageActivity;
        import com.example.Dde_Na_Gae.chat.New_MessageActivity;
        import com.example.Dde_Na_Gae.model.UserModel;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;


        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.List;


public class RoomFragment extends Fragment {

    public  String chatting_room_option_selector;
    public  String text_room_name;
    private DatabaseReference mdatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);
        chatting_room_option_selector = getArguments().getString("chatting_room_option_selector");


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.search_room_fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new RoomFragmentRecyclerViewAdapter());

        System.out.println(chatting_room_option_selector);

        return view;



    }
    class RoomFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<Room_Name_Detail_Database> chatrrommodels1;
        public RoomFragmentRecyclerViewAdapter() {
            chatrrommodels1 = new ArrayList<>();
            int i=0;
            final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("chatting_room").child(chatting_room_option_selector).child("Room_Name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    chatrrommodels1.clear();
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                        Room_Name_Detail_Database chatroommodel1 = snapshot.getValue(Room_Name_Detail_Database.class);
                        chatrrommodels1.add(chatroommodel1);
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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

           final CustomViewHolder customViewHolder = (CustomViewHolder)holder;
//
           String abc = chatrrommodels1.get(position).master_uid;
            FirebaseDatabase.getInstance().getReference().child("users").child(abc).addListenerForSingleValueEvent(new ValueEventListener(){
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
            String abcd = chatrrommodels1.get(position).Room_name;
            customViewHolder.textView_roomname.setText(abcd);




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if절 사용하여 디테일로 들어가기
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);

                    ActivityOptions activityOptions = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright, R.anim.toleft);
                        startActivity(intent, activityOptions.toBundle());
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return chatrrommodels1.size();
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