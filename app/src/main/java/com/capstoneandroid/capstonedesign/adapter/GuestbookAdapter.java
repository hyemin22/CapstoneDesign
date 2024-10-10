package com.capstoneandroid.capstonedesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.activity.GuestBookCheckActivity;
import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.R;

import java.util.ArrayList;

public class GuestbookAdapter extends RecyclerView.Adapter<GuestbookAdapter.ViewHolder> {
    private static final int REQUEST_CODE = 1001;  // REQUEST_CODE 정의
    //GuestbookItem 객체 리스트
    ArrayList<GuestbookItem> items;
    Context context;

    public GuestbookAdapter(ArrayList<GuestbookItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_guestbook, parent, false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuestbookItem item = items.get(position);
        holder.setItem(item);

        // 아이템 클릭 리스너 추가
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GuestBookCheckActivity.class);
                intent.putExtra("content", item.getMessage());
                intent.putExtra("position", holder.getAdapterPosition());  // position 전달
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(GuestbookItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<GuestbookItem> items) {
        this.items = items;
    }

    public GuestbookItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, GuestbookItem item) {
        items.set(position, item);
    }
    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImageView;
        TextView messageTextView;
        TextView usernameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.profileImage);
            messageTextView = itemView.findViewById(R.id.message);
            usernameTextView = itemView.findViewById(R.id.username);
        }

        ////뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(GuestbookItem item) {
            profileImageView.setImageResource(item.getProfileImage());
            messageTextView.setText(item.getMessage());
            usernameTextView.setText(item.getUsername());
        }

    }
}