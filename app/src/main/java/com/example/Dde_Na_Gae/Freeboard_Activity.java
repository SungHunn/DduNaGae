package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
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

public class Freeboard_Activity extends AppCompatActivity {

    private Button writing;
    private Button my_list;
    private RecyclerView recyclerView;
    private String uid;

    int str = 0;

    DrawerLayout drawerLayout;
    View drawerView;
    ListView listview = null;

    TextView freeboard_Title;

    ImageButton imageButton_open;
    ImageButton imageButton_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_activity);

        freeboard_Title = (TextView)findViewById(R.id.free_board_text);

        recyclerView = (RecyclerView)findViewById(R.id.free_board_list);
        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        writing = (Button) findViewById(R.id.go_writing);
        my_list = (Button) findViewById(R.id.my_text_list);

        listview = findViewById(R.id.Free_board_drawer_list);

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

        drawerLayout = findViewById(R.id.free_board_activity_drawlayout);
        drawerView = findViewById(R.id.free_board_drawer);
        drawerLayout = findViewById(R.id.free_board_activity_drawlayout);

        imageButton_open = findViewById(R.id.free_board_category_open);
        imageButton_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        imageButton_close = findViewById(R.id.drawer_close);
        imageButton_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        List<String> list = new ArrayList<>();
        list.add("자유게시판");
        list.add("리뷰");
        list.add("꿀 정보");
        list.add("동호회 모집");
        list.add("기타");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        freeboard_Title.setText("자유게시판");
                        str = 0;
                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
                        break;

                    case 1:

                        Intent intent = new Intent(getApplicationContext(), Free_Board_Review.class);
                        startActivity(intent);
                        break;

                    case 2:
                        str = 2;
                        freeboard_Title.setText("꿀 정보");

                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
                        break;

                    case 3:
                        str = 3;
                        freeboard_Title.setText("동호회 모집");

                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
                        break;

                    case 4:
                        str = 4;
                        freeboard_Title.setText("기타");

                        recyclerView.removeAllViewsInLayout();
                        recyclerView.setAdapter(new Freeboard_Activity.BoardRecyclerViewAdapter());
                        break;
                }
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

                    for(DataSnapshot item:snapshot.getChildren()){
                        Article_Model article = item.getValue(Article_Model.class);

                        switch (str){
                            case 0:
                                if(article.category.equals("자유게시판-null"))
                                {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;

                            case 2:
                                if(article.category.equals("꿀 정보-null"))
                                {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 3:
                                if(article.category.equals("동호회 모집-null"))
                                {
                                    articles.add(article);
                                    articleid.add(item.getKey());
                                }
                                break;
                            case 4:
                                if(article.category.equals("기타-null"))
                                {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);

            return new Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder BoardActivityviewholder = ((Freeboard_Activity.BoardRecyclerViewAdapter.BoardActivityViewHolder)holder);

            System.out.println(articles.get(position).nickname);


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
                imageView = (ImageView)view.findViewById(R.id.freeboard_imageview);
                nickname = (TextView)view.findViewById(R.id.freeboard_nickname);
                time = (TextView)view.findViewById(R.id.freeboard_time);
                title = (TextView)view.findViewById(R.id.freeboard_title);

            }

        }
    }

}