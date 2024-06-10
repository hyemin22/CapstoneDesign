package com.capstoneandroid.capstonedesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    //GuestbookItem 객체 리스트
    ArrayList<AlbumItem> items;
    Context context;

    public AlbumAdapter(ArrayList<AlbumItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_album, parent, false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(AlbumItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<AlbumItem> items) {
        this.items = items;
    }

    public AlbumItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, AlbumItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView albumTitleView;
        ImageView emojiView;
        ImageView image1View;
        ImageView image2View;
        ImageView image3View;
        TextView albumNameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumTitleView = itemView.findViewById(R.id.albumTitle);
            emojiView = itemView.findViewById(R.id.emoji);
            image1View = itemView.findViewById(R.id.image1);
            image2View = itemView.findViewById(R.id.image2);
            image3View = itemView.findViewById(R.id.image3);
            albumNameView = itemView.findViewById(R.id.albumName);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(AlbumItem item) {
            albumTitleView.setText(item.getTitle());
            emojiView.setImageResource(item.getEmoji());
            image1View.setImageResource(item.getImage1());
            image2View.setImageResource(item.getImage2());
            image3View.setImageResource(item.getImage3());
            albumNameView.setText(item.getName());
        }

    }
}
