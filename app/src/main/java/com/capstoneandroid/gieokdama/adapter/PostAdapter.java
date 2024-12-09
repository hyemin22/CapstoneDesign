package com.capstoneandroid.gieokdama.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.activity.PostCheckActivity;
import com.capstoneandroid.gieokdama.item.PostItem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<PostItem> items = new ArrayList<>();
    private static PostAdapter.OnItemClickListener listener;
    private Context context;
    private static String createdDate;

    // 클릭 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 클릭 리스너 설정 메서드
    public void setOnItemClickListener(PostAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    // 생성자에서 Context 전달받음
    public PostAdapter(ArrayList<PostItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_alarm, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        PostItem item = items.get(position);
        holder.setItem(item);

        //holder.bind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostCheckActivity.class);
                intent.putExtra("sender_name", item.getSender_name());
                intent.putExtra("anonymous_name", item.getAnonymous_name());
                intent.putExtra("receiver_name", item.getReceiver_name());
                intent.putExtra("content", item.getContent());
                intent.putExtra("created_at", createdDate);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(PostItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<PostItem> items) {
        this.items = items;
    }

    public PostItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, PostItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView titleTextView;
        TextView dateTextView;
        PostItem currentItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emojiTextView = itemView.findViewById(R.id.emoji);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, position); // 클릭 시 리스너 호출
                    }
                }
            });
        }

        // 항목 설정 메서드
        public void bind(PostItem item) {
            this.currentItem = item; // 현재 항목 저장
        }

        public void setItem(PostItem item) {
            emojiTextView.setText("💌");

            if (item.getAnonymous_name() != null) {
                titleTextView.setText(item.getAnonymous_name() + "님이 쪽지를 보냈어요.");
            } else {
                titleTextView.setText(item.getSender_name() + "님이 쪽지를 보냈어요.");
            }

            dateTextView.setText(formatTimeDifference(item.getCreated_at()));
        }

        // 시간 차이에 따른 포맷 설정 메서드
        private String formatTimeDifference(String createdAt) {
            // LocalDateTime으로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime createdTime = LocalDateTime.parse(createdAt, formatter);
            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.between(createdTime, now);
            long minutes = duration.toMinutes();
            long hours = duration.toHours();
            long days = duration.toDays();

            if (minutes < 1) {
                return "방금 전";
            } else if (minutes < 60) {
                return minutes + "분 전";
            } else if (hours < 24) {
                return hours + "시간 전";
            } else if (days < 7) {
                return days + "일 전";
            } else {
                // 일주일 이상 경과 시 날짜만 표시
                createdDate = createdTime.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"));
                return createdDate;
            }
        }
    }
}
