package com.example.Dde_Na_Gae;

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
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Freeboard_Activity extends AppCompatActivity {

    private Button writing;
    private Button my_list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_activity);

        recyclerView = (RecyclerView)findViewById(R.id.free_board_list);
        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        writing = (Button) findViewById(R.id.go_writing);
        my_list = (Button) findViewById(R.id.my_text_list);

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Free_Board_Writing.class);
                startActivity(intent);
            }
        });

        my_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Free_Board_List.class);
                startActivity(intent);
            }
        });


    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Article_Model> articles;

        public  BoardRecyclerViewAdapter() {

            articles = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("Free_Board").orderByChild("writing_time").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    articles.clear();

                    for(DataSnapshot item:snapshot.getChildren()){
                        Article_Model article = item.getValue(Article_Model.class);
                        articles.add(article);
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

            return new Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

            Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder BoardActivityviewholder = ((Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder)holder);

            Glide.with(holder.itemView.getContext())
                    .load(articles.get(position).imageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(BoardActivityviewholder.imageView);
            String Time = articles.get(position).writing_time.substring(2,4)+"."+articles.get(position).writing_time.substring(6,8)+"."+articles.get(position).writing_time.substring(10,12)+"."+articles.get(position).writing_time.substring(18,20)+":"+articles.get(position).writing_time.substring(21,23);
            BoardActivityviewholder.nickname.setText(articles.get(position).nickname);
            BoardActivityviewholder.time.setText(Time);
            BoardActivityviewholder.title.setText(articles.get(position).title);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Free_Board_Detail.class);
                    intent.putExtra("profile", articles.get(position).imageUri);
                    intent.putExtra("nickname", articles.get(position).nickname);
                    intent.putExtra("content", articles.get(position).content);
                    intent.putExtra("writing_time", articles.get(position).writing_time);
                    intent.putExtra("title", articles.get(position).title);
                    intent.putExtra("uid",articles.get(position).uid);

                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return articles.size();
        }

        private class BoardActivityViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView nickname;
            public TextView time;
            public TextView title;

            public BoardActivityViewHolder(View view) {
                super(view);
                imageView = (ImageView)view.findViewById(R.id.freeboard_imageview);
                nickname = (TextView)view.findViewById(R.id.freeboard_nickname);
                time = (TextView)view.findViewById(R.id.freeboard_time);
                title = (TextView)view.findViewById(R.id.freeboard_title);

            }

        }
    }

}