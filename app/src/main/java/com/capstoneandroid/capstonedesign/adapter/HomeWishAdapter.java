package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.item.HomeWishItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.WishListItem;

import java.util.ArrayList;

public class HomeWishAdapter extends RecyclerView.Adapter<HomeWishAdapter.ViewHolder> {

    ArrayList<WishListItem> items = new ArrayList<WishListItem>();
    Context context;

    public HomeWishAdapter(ArrayList<WishListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

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
        WishListItem item = items.get(position);
        holder.setItem(item);
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
        public void setItem(WishListItem item) {
            titleTextView.setText(item.getTitle());
            dateTextView.setText(item.getStartDate());

            String ddayText = item.getDday();  // 예: "D-2", "D-11", "D-day" 등
            ddayTextView.setText(ddayText);  // Dday를 텍스트로 표시

            // ddayText에서 숫자만 추출
            int dday = 0;
            if (ddayText.contains("D-")) {
                try {
                    // "D-" 이후의 숫자 부분을 추출
                    dday = Integer.parseInt(ddayText.substring(2).trim());
                } catch (NumberFormatException e) {
                    // 숫자 변환 실패 시 기본값 0 설정
                    dday = 0;
                }
            } else if (ddayText.equals("D-day")) {
                // "D-day"일 경우 0일로 처리
                dday = 0;
            }

            // 배경과 글씨 색상을 변경
            Context context = ddayTextView.getContext(); // Context 가져오기

            int backgroundColor;
            int textColor;

            // dday 값에 따라 색상 변경
            if (dday <= 7) {
                backgroundColor = ContextCompat.getColor(context, R.color.lightPink); // lightPink
                textColor = ContextCompat.getColor(context, R.color.pink); // pink
            } else if (dday <= 14) {
                backgroundColor = ContextCompat.getColor(context, R.color.lightGreen); // lightGreen
                textColor = ContextCompat.getColor(context, R.color.green); // green
            } else {
                // dday 값이 14 이상일 경우 기본 색상 설정
                backgroundColor = ContextCompat.getColor(context, R.color.white); // white (예시)
                textColor = ContextCompat.getColor(context, R.color.black); // black (예시)
            }

            // 색상 적용
            ddayTextView.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            ddayTextView.setTextColor(textColor);
        }
    }
}