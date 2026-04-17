package com.capstoneandroid.gieokdama.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.CalendarUtil;
import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.fragment.FeedCalMonthFragment;
import com.capstoneandroid.gieokdama.model.DiaryNum;
import com.capstoneandroid.gieokdama.repository.DiaryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    private FeedCalMonthFragment fragment;
    Long userId = UserInfoManager.getInstance().getUserId();
    Long diaryCount; //일기 개수 저장
    public ArrayList<LocalDate> dayList;
    private Set<LocalDate> diaryDates; // 일기 데이터
    private OnDateSelectedListener dateSelectedListener; // 인터페이스 리스너

    public CalendarAdapter(ArrayList<LocalDate> dayList, OnDateSelectedListener dateSelectedListener) {
        this.dayList = dayList;
        this.dateSelectedListener = dateSelectedListener; // 리스너 초기화
        this.diaryDates = new HashSet<>(); // 초기화
    }

    public void setDiaryDates(Set<LocalDate> diaryDates) {
        this.diaryDates = diaryDates;
        notifyDataSetChanged(); // 데이터 갱신
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        // 날짜 변수에 담기
        LocalDate day = dayList.get(position);

        if (day == null) {
            holder.dayText.setText("");
            holder.onedot.setVisibility(View.INVISIBLE); // 점 숨기기
            holder.twodot.setVisibility(View.INVISIBLE); // 점 숨기기
            holder.parentView.setBackgroundResource(0); // 배경 제거
        } else {
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));

            // 일기 개수에 따라 점 표시
            getDiaryCountForDate(day, holder);

            // 클릭한 날짜 강조 (배경 색 및 텍스트 색 변경)
            if (day.equals(CalendarUtil.selecedDate)) {
                holder.parentView.setBackgroundResource(R.drawable.selector_background);
                holder.dayText.setTextColor(Color.WHITE);
            } else {
                holder.parentView.setBackgroundResource(0); // 배경 제거
                holder.dayText.setTextColor(Color.BLACK); // 기본 색상
            }

            // 날짜 클릭 이벤트
            holder.itemView.setOnClickListener(view -> {
                // 현재 선택된 날짜를 갱신
                CalendarUtil.selecedDate = day; // 선택된 날짜 업데이트
                notifyDataSetChanged(); // RecyclerView 갱신

                // 선택된 날짜를 리스너를 통해 전달
                if (dateSelectedListener != null) {
                    dateSelectedListener.onDateSelected(day); // 리스너 호출
                }
            });
        }
    }

    // Helper 메서드: 특정 날짜의 일기 개수 반환
    private void getDiaryCountForDate(LocalDate date, CalendarViewHolder holder) {
        DiaryRepository diaryRepository = new DiaryRepository();
        diaryRepository.getDiaryNum(userId, new DiaryRepository.GetDiaryNumCallback() {
            @Override
            public void onSuccess(List<DiaryNum> diaryNums) {
                // 일기 목록에서 해당 날짜에 맞는 개수 찾기
                diaryCount = 0L; // 일기 개수 기본값을 0으로 설정
                for (DiaryNum diaryNum : diaryNums) {
                    if (diaryNum.getDate().equals(date.toString())) {
                        // diaryCount 값이 null일 경우 0L로 처리
                        diaryCount = (diaryNum.getCount() != null) ? diaryNum.getCount() : 0L;
                    }
                }

                // 점 표시 업데이트
                updateDots(holder);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("CalendarAdapter", "Error fetching diary count: " + errorMessage);
            }
        });
    }

    // 점 표시 업데이트 메서드
    private void updateDots(CalendarViewHolder holder) {
        if (diaryCount == 1) {
            holder.onedot.setVisibility(View.VISIBLE);
            holder.twodot.setVisibility(View.INVISIBLE);
        } else if (diaryCount == 2) {
            holder.onedot.setVisibility(View.INVISIBLE);
            holder.twodot.setVisibility(View.VISIBLE);
        } else {
            holder.onedot.setVisibility(View.INVISIBLE);
            holder.twodot.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnDateSelectedListener {
        void onDateSelected(LocalDate date);
    }

    @Override
    public int getItemCount() {

        return dayList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;
        View parentView;
        View onedot, twodot;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
            onedot = itemView.findViewById(R.id.onedot);
            twodot = itemView.findViewById(R.id.twodot);
        }
    }
}
