package com.example.Dde_Na_Gae;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Today_RecyclerViewAdapter extends RecyclerView.Adapter<Today_RecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener){
        this.onLongItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView today_img_item;
        TextView today_title_item;

        public ViewHolder(View itemView){
            super(itemView);

            today_img_item = (ImageView) itemView.findViewById(R.id.today_img_item);
            today_title_item = (TextView) itemView.findViewById(R.id.today_title_item);
        }
    }

    private ArrayList<Today_RecyclerViewItem> mList = null;

    public Today_RecyclerViewAdapter(ArrayList<Today_RecyclerViewItem> mList){
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.today_recycler_item, parent, false);
        Today_RecyclerViewAdapter.ViewHolder vh = new Today_RecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(Today_RecyclerViewAdapter.ViewHolder holder, int position) {
        Today_RecyclerViewItem item = mList.get(position);

        Glide.with(holder.itemView.getContext()).load(item.getUrl()).into(holder.today_img_item);
        holder.today_title_item.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
