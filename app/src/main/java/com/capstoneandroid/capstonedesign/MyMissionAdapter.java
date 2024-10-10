package com.capstoneandroid.capstonedesign;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyMissionAdapter extends RecyclerView.Adapter<MyMissionAdapter.ViewHolder>{
    ArrayList<MyMissionItem> items = new ArrayList<MyMissionItem>();
    Context context;

    public MyMissionAdapter(Context context) {
        this.context = context;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public MyMissionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_mymission, parent, false);

        return new MyMissionAdapter.ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull MyMissionAdapter.ViewHolder holder, int position) {
        MyMissionItem item = items.get(position);
        holder.setItem(item);

        // 미션 아이템 클릭 시 수정/삭제 화면으로 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MissionCreateActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("source", "MyMissionAdapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MyMissionItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<MyMissionItem> items) {
        this.items = items;
    }

    public MyMissionItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MyMissionItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView, titleTextView, cycleTextView,
                percentTextView, countTextView, goalTextView;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emojiTextView = itemView.findViewById(R.id.emoji);
            titleTextView = itemView.findViewById(R.id.title);
            cycleTextView = itemView.findViewById(R.id.cycle);
            percentTextView = itemView.findViewById(R.id.percent);
            countTextView = itemView.findViewById(R.id.count);
            goalTextView = itemView.findViewById(R.id.goal);
            progressBar = itemView.findViewById(R.id.progress);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(MyMissionItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            cycleTextView.setText(item.getCycle());
            percentTextView.setText(item.getPercent());
            countTextView.setText(item.getCount());
            goalTextView.setText(item.getGoal());

            // percentTextView에서 값을 가져와서 ProgressBar의 progress 속성 설정
            int progress = Integer.parseInt(item.getPercent());
            progressBar.setProgress(progress);
        }

    }
}
