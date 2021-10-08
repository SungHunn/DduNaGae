package com.example.Dde_Na_Gae;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MainRecyclerViewItem> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_main;
        TextView tv_main_title;
        TextView tv_main_addr;

        public ViewHolder(View itemView){
            super(itemView);

            img_main = (ImageView) itemView.findViewById(R.id.img_main);
            tv_main_title = (TextView) itemView.findViewById(R.id.tv_main_title);
            tv_main_addr = (TextView) itemView.findViewById(R.id.tv_main_addr);
        }
    }

    MainRecyclerViewAdapter(ArrayList<MainRecyclerViewItem> list){
        mData = list;
    }

    @Override
    public MainRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.main_items, parent, false);
        MainRecyclerViewAdapter.ViewHolder vh = new MainRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter.ViewHolder holder, int position) {
        MainRecyclerViewItem item = mData.get(position);

        Glide.with(holder.itemView.getContext()).load(item.getUrl()).into(holder.img_main);
        holder.tv_main_title.setText(item.getTitle());
        holder.tv_main_addr.setText(item.getAddr());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Search_Selected.class);
                intent.putExtra("conId", mData.get(position).getConId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
