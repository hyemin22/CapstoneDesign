package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    ArrayList<DiaryListItem> items = new ArrayList<DiaryListItem>();
    Context context;

    public DiaryAdapter(ArrayList<DiaryListItem> item, Context context) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_diary, parent, false);

        return new DiaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, int pos) {
        DiaryListItem item = items.get(pos);
        holder.setItem(item); //아이템 데이터 바인딩
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(DiaryListItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<DiaryListItem> items) {
        this.items = items;
    }

    public DiaryListItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, DiaryListItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, contentTextView;
        ImageView photoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            contentTextView = itemView.findViewById(R.id.content);
            photoImageView = itemView.findViewById(R.id.photo);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(DiaryListItem item) {
            titleTextView.setText(item.getTitle());
            contentTextView.setText(item.getContent());
            Glide.with(itemView.getContext()).load(item.getPhoto1()).into(photoImageView);

        }
    }
}
