package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Free_Board_Review extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user != null ? user.getUid() : null;

    private RecyclerView recyclerView;


    Button hotel;
    Button hospital;
    Button travel;
    Button hair_cut;
    Button park;
    Button etc;

    int str = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_review);

        recyclerView = (RecyclerView) findViewById(R.id.review_list);
        recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hotel = findViewById(R.id.review_hotel);
        hospital = findViewById(R.id.review_hspt);
        travel = findViewById(R.id.review_travel);
        hair_cut = findViewById(R.id.review_haircut);
        park = findViewById(R.id.review_park);
        etc = findViewById(R.id.review_etc);

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 1;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 2;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());
            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 3;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());
            }
        });
        hair_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 4;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());
            }
        });
        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 5;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());
            }
        });
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 6;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Free_Board_Review.BoardRecyclerViewAdapter());


            }
        });
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
                        Article_Model article = new Article_Model();
                        article = item.getValue(Article_Model.class);

                        switch (str) {
                            case 0:
                                if (article.category.equals("리뷰")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 1:
                                if (article.category.equals("리뷰-숙소")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 2:
                                if (article.category.equals("리뷰-병원")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 3:
                                if (article.category.equals("리뷰-여행지")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 4:
                                if (article.category.equals("리뷰-미용실")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 5:
                                if (article.category.equals("리뷰-공원")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 6:
                                if (article.category.equals("리뷰-기타")) {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
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

            return new Free_Board_Review.BoardRecyclerViewAdapter.BoardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Free_Board_Review.BoardRecyclerViewAdapter.BoardViewHolder boardviewholder = ((Free_Board_Review.BoardRecyclerViewAdapter.BoardViewHolder) holder);

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
                    Intent intent = new Intent(getApplicationContext(), Free_Board_Detail.class);
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
