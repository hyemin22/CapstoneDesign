package com.capstoneandroid.capstonedesign;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishCompletedAdapter extends RecyclerView.Adapter<WishCompletedAdapter.ViewHolder> {

    ArrayList<WishCompletedItem> items = new ArrayList<WishCompletedItem>();
    Context context;

    public WishCompletedAdapter(Context context) {
        this.context = context;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_wishlist_complete, parent, false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishCompletedItem item = items.get(position);
        holder.setItem(item);

        // 위시 아이템 클릭 시 수정/삭제 화면으로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WishCreateActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("source", "WishCompletedAdapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(WishCompletedItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<WishCompletedItem> items) {
        this.items = items;
    }

    public WishCompletedItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, WishCompletedItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView titleTextView;
        TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emojiTextView = itemView.findViewById(R.id.emoji);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(WishCompletedItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            dateTextView.setText(item.getDate());
        }

    }
}