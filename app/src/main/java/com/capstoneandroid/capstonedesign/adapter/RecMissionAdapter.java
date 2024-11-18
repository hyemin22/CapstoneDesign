package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.RecMissionItem;

import java.util.ArrayList;

public class RecMissionAdapter extends RecyclerView.Adapter<RecMissionAdapter.ViewHolder>{

    private ArrayList<RecMissionItem> items = new ArrayList<RecMissionItem>();
    Context context;
    private OnRecMissionItemClickListener listener;

    public RecMissionAdapter(ArrayList<RecMissionItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    //리스너 인터페이스
    public interface OnRecMissionItemClickListener {
        void onAddMission(RecMissionItem item);
    }

    //리스너 설정 메서드
    public void setOnItemClickListener(OnRecMissionItemClickListener listener) {
        this.listener = listener;
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public RecMissionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recmission, parent, false);

        return new RecMissionAdapter.ViewHolder(view, this, listener);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull RecMissionAdapter.ViewHolder holder, int position) {
        RecMissionItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(RecMissionItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<RecMissionItem> items) {
        this.items = items;
    }

    public RecMissionItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, RecMissionItem item) {
        items.set(position, item);
    }

    public void removeItem(int position) {
        if (position != RecyclerView.NO_POSITION) {
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView, titleTextView, descriptionTextView;
        CheckBox plus;

        public ViewHolder(@NonNull View itemView, RecMissionAdapter adapter, OnRecMissionItemClickListener listener) {
            super(itemView);
            emojiTextView = itemView.findViewById(R.id.icon);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.ment);
            plus = itemView.findViewById(R.id.plus);

            plus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    //체크 시 미션 추가 및 해당 아이템 삭제
                    if (position != RecyclerView.NO_POSITION && isChecked && listener != null) {
                        RecMissionItem item = new RecMissionItem(
                                adapter.context,
                                emojiTextView.getText().toString(),
                                titleTextView.getText().toString(),
                                descriptionTextView.getText().toString());

                        listener.onAddMission(item);

                        // 아이템 삭제
                        adapter.removeItem(position);
                    }
                }
            });
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(RecMissionItem item) {
            emojiTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            descriptionTextView.setText(item.getDescription());
        }
    }
}
