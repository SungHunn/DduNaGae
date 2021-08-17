package com.example.Dde_Na_Gae;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private ArrayList<Data> listdata;

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matching_list,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listdata.get(position));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    void addItem(Data data){
        listdata.add(data);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView nickname;
        TextView pet_inf;
        TextView h_trip_area;
        ImageView profile_photo;

        public ItemViewHolder(View view){
            super(view);
            this.nickname = (TextView)view.findViewById(R.id.chatting_nickname);
            this.pet_inf = (TextView)view.findViewById(R.id.chatting_dog_inf);
            this.h_trip_area = (TextView)view.findViewById(R.id.chatting_hope_area);
            this.profile_photo = (ImageView)view.findViewById(R.id.profile_photo);
        }
        void onBind(Data data){
            nickname.setText(data.getNickname());
            pet_inf.setText(data.getDog_inf());
            h_trip_area.setText(data.getH_trip_area());
            profile_photo.setImageResource(data.getProfile_photo());
        }
    }
}