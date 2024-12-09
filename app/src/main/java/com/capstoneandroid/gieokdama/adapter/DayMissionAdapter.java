package com.capstoneandroid.gieokdama.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.activity.MissionCreateActivity;
import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.item.MyMissionItem;
import com.capstoneandroid.gieokdama.repository.MissionRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DayMissionAdapter extends RecyclerView.Adapter<DayMissionAdapter.ViewHolder>{

    ArrayList<MyMissionItem> items;
    Context context;

    public DayMissionAdapter(ArrayList<MyMissionItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

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
        MyMissionItem item = items.get(position);
        holder.setItem(item);

        // 오늘 날짜 확인
        String today = holder.getTodayDate();
        String savedDate = holder.preferences.getString("mission_" + item.getId(), null);

        // 오늘 완료된 미션이면 UI 갱신
        if (today.equals(savedDate)) {
            holder.updateCheckedUI(); // UI 상태 갱신
        }

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
                intent.putExtra("source", "DayMissionAdapter");
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
        TextView iconTextView;
        TextView titleTextView;
        TextView progressStaticTextView;
        TextView progressTextView;
        TextView textView;
        RelativeLayout parentLayout;
        CheckBox checkBox;
        MyMissionItem currentItem; // 현재 아이템 저장
        SharedPreferences preferences;
        private boolean isFromUser = false; // 사용자 입력 여부 확인


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iconTextView = itemView.findViewById(R.id.missionIcon);
            titleTextView = itemView.findViewById(R.id.missionTitle);
            progressStaticTextView = itemView.findViewById(R.id.progressText);
            progressTextView = itemView.findViewById(R.id.progress);
            textView = itemView.findViewById(R.id.text);
            parentLayout = itemView.findViewById(R.id.missionLayout);
            checkBox = itemView.findViewById(R.id.check);

            preferences = itemView.getContext().getSharedPreferences("MissionPrefs", Context.MODE_PRIVATE);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isFromUser) return; // 프로그래밍적으로 변경된 경우 리스너 동작 차단
                progressTextView.setText("100.0");

                // 오늘 날짜 확인
                String today = getTodayDate();
                String savedDate = preferences.getString("mission_" + currentItem.getId(), null);

                if (today.equals(savedDate)) {
                    Toast.makeText(itemView.getContext(), "오늘 이미 완료한 미션입니다.", Toast.LENGTH_SHORT).show();
                    updateCheckedUI(); // UI 상태 갱신
                    return;
                }

                preferences.edit().putString("mission_" + currentItem.getId(), today).apply();
                updateCheckedUI();
                updateMissionProgress(currentItem.getId());
            });
        }

        private void updateCheckedUI() {
            parentLayout.setBackgroundResource(R.drawable.checked_background_day);
            checkBox.setBackgroundResource(R.drawable.ic_checked);
            titleTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            progressStaticTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            progressTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            textView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(MyMissionItem item) {
            currentItem = item; //현재 아이템 저장

            // 아이템 데이터 설정
            iconTextView.setText(item.getEmoji());
            titleTextView.setText(item.getTitle());
            progressTextView.setText(item.getPercent());

            // 저장된 날짜 확인
            String savedDate = preferences.getString("mission_" + item.getId(), null);
            String today = getTodayDate();

            if (today.equals(savedDate)) {
                // 오늘 이미 체크된 상태
                checkBox.setChecked(true);
            } else {
                // 초기 상태
                checkBox.setChecked(false);
            }

            // 리스너 동작 가능 플래그 설정
            isFromUser = true;
        }

        private String getTodayDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.format(new Date());
        }

        private void updateMissionProgress(Long id) {
            MissionRepository missionRepository = new MissionRepository();
            missionRepository.updateMissionProgress(id, new MissionRepository.MissionCallback() {
                @Override
                public void onSuccess() {
                    Log.d("MissionCreateActivity", "미션 진행률이 성공적으로 수정되었습니다");
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("MissionCreateActivity", "미션 진행률 수정 실패: " + errorMessage);
                }
            });
        }
    }

}
