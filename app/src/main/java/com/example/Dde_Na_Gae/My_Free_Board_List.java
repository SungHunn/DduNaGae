package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

        recyclerView = (RecyclerView) findViewById(R.id.my_free_board_list);
        recyclerView.setAdapter(new My_Free_Board_List.BoardRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
                List<Article_Model> articles;
                List<String> articleid;


        public BoardRecyclerViewAdapter() {
                    articleid = new ArrayList<>();
                    articles = new ArrayList<>();
                    articles.clear();

                    FirebaseDatabase.getInstance().getReference().child("Free_Board").orderByChild("writing_time").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot item : snapshot.getChildren()) {
                                Article_Model article = item.getValue(Article_Model.class);
                                if (article.uid.equals(uid)) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
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
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);

                    return new BoardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            BoardViewHolder boardviewholder = ((My_Free_Board_List.BoardRecyclerViewAdapter.BoardViewHolder) holder);

            Glide.with(holder.itemView.getContext())
                    .load(articles.get(position).imageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(boardviewholder.imageView);

            String Time = articles.get(position).writing_time.substring(2, 4) + "." + articles.get(position).writing_time.substring(6, 8) + "." + articles.get(position).writing_time.substring(10, 12) + "." + articles.get(position).writing_time.substring(18, 20) + ":" + articles.get(position).writing_time.substring(21, 23);
            boardviewholder.nickname.setText(articles.get(position).nickname);
            boardviewholder.time.setText(Time);
            boardviewholder.title.setText(articles.get(position).title);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), My_free_board_detail.class);
                    intent.putExtra("articleid", articleid.get(position));
                    startActivity(intent);
                }
            });

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
                imageView = (ImageView) view.findViewById(R.id.freeboard_imageview);
                nickname = (TextView) view.findViewById(R.id.freeboard_nickname);
                time = (TextView) view.findViewById(R.id.freeboard_time);
                title = (TextView) view.findViewById(R.id.freeboard_title);

            }

        }
    }
}