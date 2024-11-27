package com.capstoneandroid.capstonedesign.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.CalendarUtil;
import com.capstoneandroid.capstonedesign.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    public ArrayList<LocalDate> dayList;
    private boolean isMonthlyView;  // 월간/주간 뷰 구분을 위한 변수
    private OnDateSelectedListener dateSelectedListener; // 인터페이스 리스너

    public CalendarAdapter(ArrayList<LocalDate> dayList, boolean isMonthlyView) {
        this.dayList = dayList;
        this.isMonthlyView = isMonthlyView;  // 생성자로 월간/주간 뷰 구분
        this.dateSelectedListener = dateSelectedListener; // 리스너 초기화
    }

    @Override
    public int getItemViewType(int position) {
        // 월간 뷰일 때는 기본 뷰 타입, 주간 뷰일 때는 다른 뷰 타입을 반환
        return isMonthlyView ? 1 : 2;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        // 월간 뷰와 주간 뷰의 레이아웃을 다르게 설정
        if(viewType == 1) {  // 월간 뷰
            view = inflater.inflate(R.layout.calendar_cell, parent, false);
        } else {  // 주간 뷰
            view = inflater.inflate(R.layout.calendar_cell, parent, false);
        }

        return new CalendarViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        // 날짜 변수에 담기
        LocalDate day = dayList.get(position);

        if (day == null) {
            holder.dayText.setText("");
            holder.dateDot.setVisibility(View.GONE); // 점 숨기기
            holder.parentView.setBackgroundResource(0); // 배경 제거
        } else {
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));

            // 오늘 날짜 강조
            if (day.equals(LocalDate.now())) { // 오늘 날짜만 강조
                holder.dateDot.setVisibility(View.VISIBLE); // 점 표시
            } else {
                holder.dateDot.setVisibility(View.GONE); // 점 숨김
            }

            // 선택된 날짜 강조 (배경 색 및 텍스트 색 변경)
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
        View dateDot;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
            dateDot = itemView.findViewById(R.id.dateDot);
        }
    }
}
