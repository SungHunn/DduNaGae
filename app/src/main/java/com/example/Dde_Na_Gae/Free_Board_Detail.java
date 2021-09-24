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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.example.Dde_Na_Gae.model.ChatModel;
import com.example.Dde_Na_Gae.model.CommentModel;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.user.model.User;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Free_Board_Detail  extends AppCompatActivity {

    private TextView title;
    private TextView content;
    private TextView nickname;
    private EditText edittext_comment;

    LinearLayout linearLayout;

    private Button send;

    private ImageView filledheart;
    private ImageView unfilledheart;
    private ImageView photo;
    private ImageView profile;

    private String articleid;
    private String uid;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_detail);

        uid = FirebaseAuth.getInstance().getUid();

        linearLayout = findViewById(R.id.free_board_detail_iloveit);
        recyclerView = findViewById(R.id.free_board_detail_comment_recyclerview);

        title = (TextView)findViewById(R.id.free_board_detail_title) ;
        content = (TextView)findViewById(R.id.free_board_detail_content);
        nickname = (TextView)findViewById(R.id.writer_nickname);
        edittext_comment = (EditText)findViewById(R.id.free_board_detail_comment);
        send = (Button)findViewById(R.id.free_board_detail_post_comment);

        filledheart = findViewById(R.id.filled_heart);
        unfilledheart = findViewById(R.id.unfilled_heart);
        photo = (ImageView)findViewById(R.id.free_board_image);
        profile = (ImageView)findViewById(R.id.freeboard_detail_myprofile);




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentModel commentModel = new CommentModel();
                commentModel.uid = uid;
                commentModel.comment = edittext_comment.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").push().setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        edittext_comment.setText("");
                    }
                });
            }
        });

        checkloveit();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel usermodels = new UserModel();
                if(unfilledheart.getVisibility() == View.VISIBLE) {
                    usermodels.uid = uid;
                    FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").push().setValue(usermodels);
                    checkloveit();
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").orderByChild("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for(DataSnapshot item : snapshot.getChildren()){
                                UserModel userModel = item.getValue(UserModel.class);
                                if(userModel.uid.equals(uid)){
                                  String key = snapshot.getKey();
                                  FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").child(key).setValue(null);
                                }
                            }
                        }
                    });
                }
            }
        });
        Intent intent = getIntent();
        articleid =  intent.getStringExtra("articleid");

        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Article_Model article ;
                article = snapshot.getValue(Article_Model.class);
                title.setText(article.title);
                content.setText(article.content);
                nickname.setText(article.nickname);

                Glide.with(Free_Board_Detail.this)
                        .load(article.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(photo);

                FirebaseDatabase.getInstance().getReference().child("users").child(article.uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        String uri = snapshot.getValue(String.class);

                        Glide.with(Free_Board_Detail.this)
                                .load(uri)
                                .apply(new RequestOptions().circleCrop())
                                .into(profile);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void checkcomment(){
        FirebaseDatabase.getInstance().getReference().child("FreeBoard").child(articleid).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot != null){
                    recyclerView.setAdapter(new Free_Board_Detail.BoardCommentRecyclerViewAdapter());
                    recyclerView.setLayoutManager(new LinearLayoutManager(Free_Board_Detail.this));
                }
            }
        });
    }

    public void checkloveit(){
        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child(uid).child("Loveit").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                        filledheart.setVisibility(View.VISIBLE);
                        unfilledheart.setVisibility(View.GONE);
                }else{
                    filledheart.setVisibility(View.GONE);
                    unfilledheart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    class BoardCommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<CommentModel> commentModels;
        List<String> uids;

        public BoardCommentRecyclerViewAdapter() {
            commentModels = new ArrayList<>();
            uids = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    commentModels.clear();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            CommentModel commentModel = item.getValue(CommentModel.class);
                            uids.add(commentModel.uid);
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

            return new Free_Board_Detail.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Free_Board_Detail.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder boardCommentViewHolder = ((Free_Board_Detail.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder)holder);

            boardCommentViewHolder.comment_comment.setText(commentModels.get(position).comment);

            FirebaseDatabase.getInstance().getReference().child("users").child(uids.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
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
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
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

                comment_comment=view.findViewById(R.id.item_comment_comment);
                comment_nickname=view.findViewById(R.id.item_comment_nickname);
                comment_image = view.findViewById(R.id.item_comment_imageview);

            }

        }
    }

}

