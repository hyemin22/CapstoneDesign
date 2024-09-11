package com.capstoneandroid.capstonedesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder>{

    private ArrayList<AlarmItem> items = new ArrayList<AlarmItem>();
    private static OnItemClickListener listener;

    // 클릭 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 클릭 리스너 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_alarm, parent, false);

        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.ViewHolder holder, int position) {
        AlarmItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(AlarmItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<AlarmItem> items) {
        this.items = items;
    }

    public AlarmItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, AlarmItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView titleTextView;
        TextView dateTextView;

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

        public void setItem(AlarmItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            dateTextView.setText(item.getDate());
        }

    }
}
