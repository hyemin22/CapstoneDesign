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

    ArrayList<LocalDate> dayList;
    boolean isMonthlyView;  // 월간/주간 뷰 구분을 위한 변수

    public CalendarAdapter(ArrayList<LocalDate> dayList, boolean isMonthlyView) {
        this.dayList = dayList;
        this.isMonthlyView = isMonthlyView;  // 생성자로 월간/주간 뷰 구분
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

        if(day == null) {
            holder.dayText.setText("");
        } else {
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));

            // 오늘 날짜 색상 칠하기
            if(day.equals(CalendarUtil.selecedDate)) {
                holder.parentView.setBackgroundResource(R.drawable.my_selector);
                holder.dayText.setTextColor(Color.WHITE);
                holder.dateDot.setVisibility(View.VISIBLE); // 점 표시
            }else {
                holder.dateDot.setVisibility(View.GONE); // 점 숨김
            }


        }


        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iYear = day.getYear();   // 년
                int iMonth = day.getMonthValue();   // 월
                int iDay = day.getDayOfMonth();    //일

                String yearMonDay = iYear + "년" + iMonth + "월" + iDay + "일";

                Toast.makeText(holder.itemView.getContext(), yearMonDay, Toast.LENGTH_SHORT).show();
            }
        });
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
