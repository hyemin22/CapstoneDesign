package com.capstoneandroid.capstonedesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishExpectedAdapter extends RecyclerView.Adapter<WishExpectedAdapter.ViewHolder>{
    ArrayList<WishExpectedItem> items = new ArrayList<WishExpectedItem>();

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_wishlist_expected, parent, false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishExpectedItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(WishExpectedItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<WishExpectedItem> items) {
        this.items = items;
    }

    public WishExpectedItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, WishExpectedItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView titleTextView;
        TextView ddayTextView;
        TextView dateTextView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emojiTextView = itemView.findViewById(R.id.emoji);
            titleTextView = itemView.findViewById(R.id.title);
            ddayTextView = itemView.findViewById(R.id.dday);
            dateTextView = itemView.findViewById(R.id.date);
            parentLayout = itemView.findViewById(R.id.wishLayout);
            CheckBox check = itemView.findViewById(R.id.check);

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        parentLayout.setBackgroundResource(R.drawable.checked_background_wish);
                        check.setBackgroundResource(R.drawable.ic_checked);

                    } else {
                        parentLayout.setBackgroundResource(R.drawable.unchecked_background);
                        check.setBackgroundResource(R.drawable.ic_unchecked);
                    }
                }
            });
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(WishExpectedItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            ddayTextView.setText(item.getDday());
            dateTextView.setText(item.getDate());
        }

    }
}
