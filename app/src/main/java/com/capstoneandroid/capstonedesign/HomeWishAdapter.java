package com.capstoneandroid.capstonedesign;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeWishAdapter extends RecyclerView.Adapter<HomeWishAdapter.ViewHolder> {

    ArrayList<HomeWishItem> items = new ArrayList<HomeWishItem>();

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_wishlist_home, parent, false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeWishItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(HomeWishItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<HomeWishItem> items) {
        this.items = items;
    }

    public HomeWishItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, HomeWishItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView ddayTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);
            ddayTextView = itemView.findViewById(R.id.dday);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(HomeWishItem item) {
            titleTextView.setText(item.getTitle());
            dateTextView.setText(item.getDate());
            ddayTextView.setText(item.getDday());

            // backgroundTint와 textColor를 설정합니다.
            ddayTextView.setBackgroundTintList(ColorStateList.valueOf(item.getBackgroundTint()));
            ddayTextView.setTextColor(item.getTextColor());
        }
    }
}