package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.activity.MissionCreateActivity;
import com.capstoneandroid.capstonedesign.item.ActivityItem;
import com.capstoneandroid.capstonedesign.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    ArrayList<ActivityItem> items = new ArrayList<ActivityItem>();
    Context context;
    public ActivityAdapter(Context context) {
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
                Intent intent = new Intent(context, MissionCreateActivity.class); //상세페이지 액티비티로 변경!!
                context.startActivity(intent);
            }
        });

        // ViewHolder의 이미지와 텍스트 설정
        holder.heart.setImageResource(item.isHeartFilled() ? R.drawable.ic_heart_fill : R.drawable.ic_heart);

        // 하트 클릭 리스너 설정
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFilled = !item.isHeartFilled();
                item.setHeartFilled(isFilled);
                holder.heart.setImageResource(isFilled ? R.drawable.ic_heart_fill : R.drawable.ic_heart);
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
        TextView titleTextView, typeTextView, numTextView, tag1TextView, tag2TextView, tag3TextView;
        ImageView imageView1, imageView2, imageView3, heart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.profile);
            titleTextView = itemView.findViewById(R.id.title);
            typeTextView = itemView.findViewById(R.id.type);
            numTextView = itemView.findViewById(R.id.num);
            tag1TextView = itemView.findViewById(R.id.tag1);
            tag2TextView = itemView.findViewById(R.id.tag2);
            tag3TextView = itemView.findViewById(R.id.tag3);
            imageView1 = itemView.findViewById(R.id.image1);
            imageView2 = itemView.findViewById(R.id.image2);
            imageView3 = itemView.findViewById(R.id.image3);
            heart = itemView.findViewById(R.id.heart);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(ActivityItem item) {
            profileImageView.setImageResource(item.getProfile());
            titleTextView.setText(item.getTitle());
            typeTextView.setText(item.getType());
            numTextView.setText(item.getNum());
            imageView1.setImageResource(item.getImage1());
            imageView2.setImageResource(item.getImage2());
            imageView3.setImageResource(item.getImage3());
            tag1TextView.setText(item.getTag1());
            tag2TextView.setText(item.getTag2());
            tag3TextView.setText(item.getTag3());
        }
    }
}
