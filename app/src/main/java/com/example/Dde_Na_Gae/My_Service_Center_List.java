package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.database.Service_Database;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class My_Service_Center_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView go_back;
    public String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_service_center_list);


        recyclerView = (RecyclerView) findViewById(R.id.my_service_center_list);
        recyclerView.setAdapter(new My_Service_Center_List.ServiceCenterRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        go_back = (ImageView)findViewById(R.id.My_service_center_list_img_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    class ServiceCenterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Service_Database> articles;
        List<String> articleid;

        public ServiceCenterRecyclerViewAdapter() {
            articles = new ArrayList<>();
            articleid = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("Service_Center").orderByChild("writing_time").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    articles.clear();

                    for (DataSnapshot item : snapshot.getChildren()) {
                        Service_Database article = item.getValue(Service_Database.class);
                        System.out.println(article.uid);
                        System.out.println(uid);
                        if(article.uid.equals(uid)){
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

            return new My_Service_Center_List.ServiceCenterRecyclerViewAdapter.ServiceCenterActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            My_Service_Center_List.ServiceCenterRecyclerViewAdapter.ServiceCenterActivityViewHolder serviceCenterActivityViewHolder = ((My_Service_Center_List.ServiceCenterRecyclerViewAdapter.ServiceCenterActivityViewHolder)holder);

            Glide.with(holder.itemView.getContext())
                    .load(articles.get(position).imageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(serviceCenterActivityViewHolder.imageView);
            String Time = articles.get(position).writing_time.substring(2, 4) + "." + articles.get(position).writing_time.substring(6, 8) + "." + articles.get(position).writing_time.substring(10, 12) + "." + articles.get(position).writing_time.substring(18, 20) + ":" + articles.get(position).writing_time.substring(21, 23);
            serviceCenterActivityViewHolder.time.setText(Time);
            serviceCenterActivityViewHolder.title.setText(articles.get(position).title);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), My_Service_Center_Detail.class);
                    intent.putExtra("articleid", articleid.get(position));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return articles.size();
        }

        private class ServiceCenterActivityViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView nickname;
            public TextView time;
            public TextView title;

            public ServiceCenterActivityViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.freeboard_imageview);
                nickname = (TextView) view.findViewById(R.id.freeboard_nickname);
                time = (TextView) view.findViewById(R.id.freeboard_time);
                title = (TextView) view.findViewById(R.id.freeboard_title);
            }

        }
    }

}