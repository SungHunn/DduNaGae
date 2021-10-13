package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.database.Service_Database;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.example.Dde_Na_Gae.model.CommentModel;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Service_Center_Detail extends AppCompatActivity {

    private TextView title;
    private TextView content;

    private ImageView photo;
    private ImageView go_back;

    private String articleid;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user != null ? user.getUid() : null;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_center_detail);

        uid = FirebaseAuth.getInstance().getUid();
        Intent intent = getIntent();
        articleid =  intent.getStringExtra("articleid");

        recyclerView = findViewById(R.id.service_center_answer_recyclerview);


        title = (TextView)findViewById(R.id.service_center_detail_title) ;
        content = (TextView)findViewById(R.id.service_center_detail_content);

        photo = (ImageView)findViewById(R.id.service_center_image);


        go_back = (ImageView)findViewById(R.id.service_center_detail_img_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Service_Center.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Service_Center").child(articleid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Service_Database article;
                article = snapshot.getValue(Service_Database.class);
                title.setText(article.title);
                content.setText(article.content);

                Glide.with(Service_Center_Detail.this)
                        .load(article.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(photo);
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }


    public void set_answer_true(String articleid){


                FirebaseDatabase.getInstance().getReference().child("Service_Center").child(articleid).child("have_answer").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


    }

    class BoardCommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<CommentModel> commentModels;

        public BoardCommentRecyclerViewAdapter(){
            commentModels = new ArrayList<>();


            FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    commentModels.clear();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        CommentModel commentModel = item.getValue(CommentModel.class);
                        commentModels.add(commentModel);
                    }


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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
            return new Service_Center_Detail.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Service_Center_Detail.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder boardCommentViewHolder = ((Service_Center_Detail.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder)holder);



            boardCommentViewHolder.comment_comment.setText(commentModels.get(position).comment);


            System.out.println(commentModels.get(1).uid);

            FirebaseDatabase.getInstance().getReference().child("users").child(commentModels.get(position).uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    UserModel usermodel = snapshot.getValue(UserModel.class);
                    Glide.with(holder.itemView.getContext())
                            .load(usermodel.imageUri)
                            .apply(new RequestOptions().circleCrop())
                            .into(boardCommentViewHolder.comment_image);

                    boardCommentViewHolder.comment_nickname.setText(usermodel.nickname);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) { }
            });
        }


        @Override
        public int getItemCount() {
            return commentModels.size();
        }
        private class BoardCommentViewHolder extends RecyclerView.ViewHolder {
            public ImageView comment_image;
            public TextView comment_nickname;
            public TextView comment_comment;


            public BoardCommentViewHolder(View view) {
                super(view);
                comment_comment= view.findViewById(R.id.item_comment_comment);
                comment_nickname= view.findViewById(R.id.item_comment_nickname);
                comment_image = view.findViewById(R.id.item_comment_imageview);

            }

        }
    }

}

