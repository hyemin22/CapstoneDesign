package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.activity.DiaryActivity;
import com.capstoneandroid.capstonedesign.activity.DiaryCreateActivity;
import com.capstoneandroid.capstonedesign.activity.WishCreateActivity;
import com.capstoneandroid.capstonedesign.item.WishListItem;

import java.util.ArrayList;

public class WishCompletedAdapter extends RecyclerView.Adapter<WishCompletedAdapter.ViewHolder> {

    ArrayList<WishListItem> items = new ArrayList<WishListItem>();
    Context context;

    public WishCompletedAdapter(ArrayList<WishListItem> items, Context context) {
        this.items = items;
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
        WishListItem item = items.get(position);
        holder.setItem(item);

        TextView existDiaryTextView = holder.itemView.findViewById(R.id.existDiary);

        // 일기가 작성된 경우
        if (item.getDiaryId() != null) {
            existDiaryTextView.setText(" 일기 작성 완료 ");
            existDiaryTextView.setBackgroundTintList(context.getResources().getColorStateList(R.color.lightpurple));
            existDiaryTextView.setTextColor(context.getResources().getColor(R.color.purple));
        } else { // 일기가 작성되지 않은 경우
            existDiaryTextView.setText(" 일기 작성 미완료 ");
            existDiaryTextView.setBackgroundTintList(context.getResources().getColorStateList(R.color.lightblue));
            existDiaryTextView.setTextColor(context.getResources().getColor(R.color.blue));
        }

        // 위시 아이템 클릭 시 일기 작성 화면 또는 일기 확인 화면으로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("diaryInfo", "diaryId??" + item.getDiaryId());
                if (item.getDiaryId() != null) { //만약, 작성된 일기가 있는 경우
                    Intent intent = new Intent(context, DiaryActivity.class);
                    intent.putExtra("id", item.getDiaryId());
                    context.startActivity(intent);
                } else { // 작성된 일기가 없는 경우
                    Intent intent = new Intent(context, DiaryCreateActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("date", item.getStartDate());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(WishListItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<WishListItem> items) {
        this.items = items;
    }

    public WishListItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, WishListItem item) {
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
        public void setItem(WishListItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            dateTextView.setText(item.getCompletedDate());
        }

    }
}