package com.capstoneandroid.capstonedesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DayMissionAdapter extends RecyclerView.Adapter<DayMissionAdapter.ViewHolder>{

    ArrayList<DayMissionItem> items = new ArrayList<DayMissionItem>();

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public DayMissionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daymission, parent, false);

        return new DayMissionAdapter.ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull DayMissionAdapter.ViewHolder holder, int position) {
        DayMissionItem item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(DayMissionItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<DayMissionItem> items) {
        this.items = items;
    }

    public DayMissionItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, DayMissionItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
        TextView progressStaticTextView;
        TextView progressTextView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iconImageView = itemView.findViewById(R.id.missionIcon);
            titleTextView = itemView.findViewById(R.id.missionTitle);
            progressStaticTextView = itemView.findViewById(R.id.progressText);
            progressTextView = itemView.findViewById(R.id.progress);
            parentLayout = itemView.findViewById(R.id.missionLayout); // Assuming this is the ID of parent layout
            CheckBox checkBox = itemView.findViewById(R.id.missionCheck);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Change background of parentLayout based on CheckBox checked state
                    if (isChecked) {
                        parentLayout.setBackgroundResource(R.drawable.checked_background);
                        checkBox.setBackgroundResource(R.drawable.ic_checked);
                        titleTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
                        progressStaticTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
                        progressTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));

                    } else {
                        parentLayout.setBackgroundResource(R.drawable.unchecked_background);
                        checkBox.setBackgroundResource(R.drawable.ic_unchecked);
                        titleTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.black));
                        progressStaticTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.gray));
                        progressTextView.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.gray));
                    }
                }
            });
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(DayMissionItem item) {
            iconImageView.setImageResource(item.getIcon());
            titleTextView.setText(item.getTitle());
            progressTextView.setText(item.getProgress());
        }

    }
}
