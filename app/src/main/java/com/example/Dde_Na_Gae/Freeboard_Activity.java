package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.fragment.My_Personal_ChatFragment;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private ImageView go_back;

    TextView category1;
    TextView category2;
    TextView category3;
    TextView category4;
    TextView category5;

    BottomNavigationView bottomNavigationView;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user != null ? user.getUid() : null;

    int str = 0;

    TextView freeboard_Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_activity);

        freeboard_Title = (TextView) findViewById(R.id.free_board_text);

        recyclerView = (RecyclerView) findViewById(R.id.free_board_list);
        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        go_back = (ImageView) findViewById(R.id.freeboard_home_img_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.free_board_bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_text_list:
                        Intent intent = new Intent(getApplicationContext(), My_Free_Board_List.class);
                        startActivity(intent);
                        break;

                    case R.id.go_writing:
                        Intent intent3 = new Intent(getApplicationContext(), Free_Board_Writing.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });



//        writing = (Button) findViewById(R.id.go_writing);
//        my_list = (Button) findViewById(R.id.my_text_list);
//
//        writing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (user == null) {
//                    Toast.makeText(Freeboard_Activity.this, "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), Free_Board_Writing.class);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        my_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (user == null) {
//                    Toast.makeText(Freeboard_Activity.this, "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), My_Free_Board_List.class);
//                    startActivity(intent);
//                }
//            }
//        });

        category1 = findViewById(R.id.free_board1);
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeboard_Title.setText("자유게시판");
                str = 0;
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
            }
        });

        category2 = findViewById(R.id.free_board2);
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Free_Board_Review.class);
                startActivity(intent);
            }
        });

        category3 = findViewById(R.id.free_board3);
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 2;
                freeboard_Title.setText("꿀 정보");

                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
            }
        });
        category4 = findViewById(R.id.free_board4);
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = 3;
                freeboard_Title.setText("동호회 모집");

                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
            }
        });
    }


    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Article_Model> articles;
        List<String> articleid;

        public BoardRecyclerViewAdapter() {
            articles = new ArrayList<>();
            articleid = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("Free_Board").orderByChild("writing_time").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    articles.clear();

                    try {


                        for (DataSnapshot item : snapshot.getChildren()) {
                            Article_Model article = item.getValue(Article_Model.class);

                            switch (str) {
                                case 0:
                                    if (article.category.equals("자유게시판-null")) {
                                        articles.add(article);
                                        articleid.add(item.getKey());
                                    }
                                    break;

                                case 2:
                                    if (article.category.equals("꿀 정보-null")) {
                                        articles.add(article);
                                        articleid.add(item.getKey());
                                    }
                                    break;
                                case 3:
                                    if (article.category.equals("동호회 모집-null")) {
                                        articles.add(article);
                                        articleid.add(item.getKey());
                                    }
                                    break;
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
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

            return new Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder BoardActivityviewholder = ((Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder) holder);

            System.out.println(articles.get(position).nickname);




            Glide.with(holder.itemView.getContext())
                    .load(articles.get(position).imageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(BoardActivityviewholder.imageView);
            String Time = articles.get(position).writing_time.substring(2, 4) + "." + articles.get(position).writing_time.substring(6, 8) + "." + articles.get(position).writing_time.substring(10, 12) + "." + articles.get(position).writing_time.substring(18, 20) + ":" + articles.get(position).writing_time.substring(21, 23);
            BoardActivityviewholder.nickname.setText(articles.get(position).nickname);
            BoardActivityviewholder.time.setText(Time);
            BoardActivityviewholder.title.setText(articles.get(position).title);


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

        private class BoardActivityViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView nickname;
            public TextView time;
            public TextView title;

            public BoardActivityViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.freeboard_imageview);
                nickname = (TextView) view.findViewById(R.id.freeboard_nickname);
                time = (TextView) view.findViewById(R.id.freeboard_time);
                title = (TextView) view.findViewById(R.id.freeboard_title);

            }

        }
    }

}