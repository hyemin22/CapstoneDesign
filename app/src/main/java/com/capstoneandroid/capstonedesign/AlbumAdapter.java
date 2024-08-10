package com.capstoneandroid.capstonedesign;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    //GuestbookItem 객체 리스트
    ArrayList<AlbumItem> items;
    Context context;
    private boolean isListView; //리스트뷰에서 아이템의 크기 조정을 위해

    public AlbumAdapter(ArrayList<AlbumItem> items, Context context) {
        this.items = items;
        this.context = context;
        this.isListView = false; // 초기값은 false로 설정
    }

    public void setViewMode(boolean isListView) {
        this.isListView = isListView;
        notifyDataSetChanged(); // 데이터 변경 알림
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

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumDiaryList.class);
                context.startActivity(intent);
            }
        });

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (isListView) {
            // 리스트뷰 모드일 때
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            layoutParams.width = screenWidth / 2; // 화면의 절반 너비로 설정
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 높이를 자동으로 조정하여 비율 유지

            // 여기서는 GridLayoutManager를 사용하므로 GridLayout.LayoutParams를 사용해야 합니다.
            if (layoutParams instanceof GridLayoutManager.LayoutParams) {
                GridLayoutManager.LayoutParams gridLayoutParams = (GridLayoutManager.LayoutParams) layoutParams;
                gridLayoutParams.setMargins(10, 10, 10, 10); // 아이템 간의 간격을 조정합니다.
            }
        } else {
            // 스와이프 뷰 모드일 때는 기존 설정 유지
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        holder.itemView.setLayoutParams(layoutParams);
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
