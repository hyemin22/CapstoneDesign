package com.capstoneandroid.gieokdama.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.item.MyMissionItem;
import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.activity.MissionCreateActivity;

import java.util.ArrayList;

public class MyMissionAdapter extends RecyclerView.Adapter<MyMissionAdapter.ViewHolder>{
    ArrayList<MyMissionItem> items = new ArrayList<MyMissionItem>();
    Context context;

    public MyMissionAdapter(ArrayList<MyMissionItem> items, Context context) {
        this.items = items;
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
                intent.putExtra("id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("emoji", item.getEmoji());
                intent.putExtra("cycle", item.getCycle());
                intent.putExtra("repeat_day", item.getRepeat_day());
                intent.putExtra("repeat_time", item.getRepeat_time());
                intent.putExtra("alarm", item.getAlarm());
                intent.putExtra("alarm_time", item.getAlarm_time());
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
        View progressView, emptyCycleView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emojiTextView = itemView.findViewById(R.id.emoji);
            titleTextView = itemView.findViewById(R.id.title);
            cycleTextView = itemView.findViewById(R.id.cycle);
            percentTextView = itemView.findViewById(R.id.percent);
            countTextView = itemView.findViewById(R.id.count);
            goalTextView = itemView.findViewById(R.id.goal);
            progressBar = itemView.findViewById(R.id.progress);
            progressView = itemView.findViewById(R.id.progressView);
            emptyCycleView = itemView.findViewById(R.id.emptyCycleView);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(MyMissionItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            cycleTextView.setText(item.getCycle());
            percentTextView.setText(item.getPercent());
            countTextView.setText(item.getNow_time().toString());
            goalTextView.setText(item.getGoal_time().toString());

            // percentTextView에서 값을 가져와서 ProgressBar의 progress 속성 설정 (1000이 max, 75%면 750/1000)
            int progress = (int) (Double.parseDouble(item.getPercent()) * 10);
            progressBar.setProgress(progress);

            // "아직 목표가 설정되어 있지 않아요." 인지 체크하고 뷰 상태 변경
            if ("아직 목표가 설정되어 있지 않아요.".equals(item.getCycle())) {
                emptyCycleView.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
            } else {
                emptyCycleView.setVisibility(View.GONE);
                progressView.setVisibility(View.VISIBLE);
            }
        }

    }
}
