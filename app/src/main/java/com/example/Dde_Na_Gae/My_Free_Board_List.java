package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.chat.New_MessageActivity;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class My_Free_Board_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_free_board_list);
        recyclerView = (RecyclerView)findViewById(R.id.free_board_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(My_Free_Board_List.this));
        recyclerView.setAdapter(new My_Free_Board_List.RecyclerViewAdapter());

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

       List<Article_Model> articles;

        public  RecyclerViewAdapter() {
            articles = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Free_Board").orderByChild("writing_time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                articles.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    Article_Model article = item.getValue(Article_Model.class);
                    if(article.uid.equals(uid)) {
                        articles.add(article);
                    }
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);

            return new BoardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            BoardViewHolder boardviewholder = ((My_Free_Board_List.RecyclerViewAdapter.BoardViewHolder)holder);
                System.out.println(articles.get(position).nickname);
            System.out.println(articles.get(position).writing_time);
            System.out.println(articles.get(position).title);
            System.out.println(articles.get(position).imageUri);
                boardviewholder.nickname.setText(articles.get(position).nickname);
                boardviewholder.time.setText(articles.get(position).writing_time);
                boardviewholder.title.setText(articles.get(position).title);
                Glide.with(holder.itemView.getContext())
                    .load(articles.get(position).imageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(boardviewholder.imageView);
        }

        @Override
        public int getItemCount() {
            return articles.size();
        }

        private class BoardViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView nickname;
            public TextView time;
            public TextView title;

            public BoardViewHolder(View view) {
                super(view);
                imageView = (ImageView)findViewById(R.id.freeboard_imageview);
                nickname = (TextView)findViewById(R.id.freeboard_nickname);
                time = (TextView)findViewById(R.id.freeboard_time);
                title = (TextView)findViewById(R.id.freeboard_title);

            }
        }
    }
}
