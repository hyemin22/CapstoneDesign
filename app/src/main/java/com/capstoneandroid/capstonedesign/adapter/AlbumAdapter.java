package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.activity.AlbumDiaryListActivity;
import com.capstoneandroid.capstonedesign.item.AlbumItem;
import com.capstoneandroid.capstonedesign.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    //GuestbookItem 객체 리스트
    ArrayList<AlbumItem> items;
    Context context;
    private final boolean isListView; //리스트뷰에서 아이템의 크기 조정을 위해

    public AlbumAdapter(ArrayList<AlbumItem> items, Context context, boolean isListView) {
        this.items = items;
        this.context = context;
        this.isListView = isListView; // 초기값은 false로 설정
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(isListView) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_album_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_album, parent, false);
        }
        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumItem item = items.get(position);
        holder.setItem(item);

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumDiaryListActivity.class);
                context.startActivity(intent);
            }
        });
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
        RelativeLayout albumBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumTitleView = itemView.findViewById(R.id.albumTitle);
            albumBack = itemView.findViewById(R.id.album_back);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(AlbumItem item) {
            albumTitleView.setText(item.getTitle());
            albumBack.setBackgroundResource(item.getAlbumColor());
        }
    }
}
