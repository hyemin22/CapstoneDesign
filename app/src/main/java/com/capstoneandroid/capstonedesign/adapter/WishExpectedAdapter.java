package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.WishListItem;
import com.capstoneandroid.capstonedesign.activity.WishCreateActivity;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;

import java.util.ArrayList;

public class WishExpectedAdapter extends RecyclerView.Adapter<WishExpectedAdapter.ViewHolder>{
    ArrayList<WishListItem> items = new ArrayList<WishListItem>();
    Context context;

    public WishExpectedAdapter(ArrayList<WishListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

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
        WishListItem item = items.get(position);
        holder.setItem(item);

        holder.bind(item); // 전달된 item을 ViewHolder로 넘기기

        // 위시 아이템 클릭 시 수정/삭제 화면으로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WishCreateActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("start_date", item.getStartDate());
                intent.putExtra("end_date", item.getEndDate());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("icon", item.getEmoji());
                intent.putExtra("memo", item.getMemo());
                intent.putExtra("alarm", item.getAlarm());
                intent.putExtra("source", "WishExpectedAdapter");
                context.startActivity(intent);
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
        TextView ddayTextView;
        TextView dateTextView;
        RelativeLayout parentLayout;
        WishListItem currentItem;

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
                        parentLayout.setBackgroundResource(R.drawable.checked_background_day);
                        titleTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
                        dateTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
                        // 체크 시 확인 모달 띄우기
                        if (currentItem != null) { // 서버로 completed true로 바꾸는 요청 보내기
                            updateState(currentItem); //완료로 변경
                        }
                    } else {
                        parentLayout.setBackgroundResource(R.drawable.checked_background_wish);
                        titleTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.black));
                        dateTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.black));
                    }
                }
            });
        }

        // 항목 설정 메서드
        public void bind(WishListItem item) {
            this.currentItem = item; // 현재 항목 저장
        }

        // 위시리스트 완료로 변경
        public void updateState(WishListItem item) {
            WishListRepository wishListRepository = new WishListRepository();
            wishListRepository.updateWishStateToServer(item.getId(), new WishListRepository.WishListCallback() {

                @Override
                public void onSuccess() {
                    Log.d("WishExpectedAdapter", "위시리스트 상태 수정 성공");
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("WishExpectedAdapter", "위시리스트 상태 수정 실패: " + errorMessage);
                }
            });
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(WishListItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            ddayTextView.setText(item.getDday());
            dateTextView.setText(item.getStartDate());
        }

    }
}
