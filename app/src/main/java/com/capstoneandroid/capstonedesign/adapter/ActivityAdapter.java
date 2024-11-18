package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.activity.ActivityDetailActivity;
import com.capstoneandroid.capstonedesign.activity.MissionCreateActivity;
import com.capstoneandroid.capstonedesign.item.ActivityItem;
import com.capstoneandroid.capstonedesign.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    ArrayList<ActivityItem> items = new ArrayList<ActivityItem>();
    Context context;

    public ActivityAdapter(ArrayList<ActivityItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.item_activity,parent,false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position) {
        ActivityItem item = items.get(position);
        holder.setItem(item);

        // 활동 아이템 클릭 시 상세페이지 화면으로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityDetailActivity.class); //상세페이지로 이동
                intent.putExtra("id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("type", item.getType());
                intent.putExtra("region", item.getDistrict_id());
                intent.putExtra("main_photo", item.getMain_photo());
                intent.putExtra("address", item.getAddress());
                intent.putExtra("call", item.getPhone_number());
                intent.putExtra("open_time", item.getOpen_time());
                intent.putExtra("closed_day", item.getClosed_day());
                context.startActivity(intent);
            }
        });

//        // ViewHolder의 이미지와 텍스트 설정
//        holder.heart.setImageResource(item.isHeartFilled() ? R.drawable.ic_heart_fill : R.drawable.ic_heart);
//
        // 하트 클릭 리스너 설정
        holder.heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFilled = !item.isHeartFilled();
                item.setHeartFilled(isFilled);
                holder.heartImageView.setImageResource(isFilled ? R.drawable.ic_heart_fill : R.drawable.ic_heart);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ActivityItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<ActivityItem> items) {
        this.items = items;
    }

    public ActivityItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ActivityItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImageView;
        TextView titleTextView, typeTextView, reviewNumTextView;
        ImageView imageView1, heartImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.profile);
            titleTextView = itemView.findViewById(R.id.title);
            typeTextView = itemView.findViewById(R.id.type);
            reviewNumTextView = itemView.findViewById(R.id.num);
            imageView1 = itemView.findViewById(R.id.image1);
            heartImageView = itemView.findViewById(R.id.heart);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(ActivityItem item) {
            int profileId = getDrawableId(item.getProfile());
            int main_photoId = getDrawableId(item.getMain_photo());
            profileImageView.setImageResource(profileId);
            titleTextView.setText(item.getTitle());
            typeTextView.setText(item.getType());
            reviewNumTextView.setText(item.getReview_count());
            imageView1.setImageResource(main_photoId);
            heartImageView.setImageResource(R.drawable.ic_heart);
        }

        private int getDrawableId(String mainPhoto) {
            int drawableId = itemView.getContext().getResources().getIdentifier(mainPhoto, "drawable", itemView.getContext().getPackageName());

            // drawableId가 0이면 해당 drawable이 존재하지 않는 것이므로 예외 처리
            if (drawableId == 0) {
                throw new Resources.NotFoundException("Drawable not found for name: " + mainPhoto);
            }

            return drawableId;
        }
    }
}