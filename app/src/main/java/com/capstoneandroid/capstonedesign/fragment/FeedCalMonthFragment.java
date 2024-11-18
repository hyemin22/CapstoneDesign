package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.CalendarUtil;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.CalendarAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class FeedCalMonthFragment extends Fragment {

    TextView monthText; // 월 일 요일 텍스트뷰
    TextView todayText; // 오늘 날짜 텍스트뷰
    RecyclerView recyclerView;
    private Spinner spinner;
    // 요일 TextView 변수 선언
    TextView sundayText, mondayText, tuesdayText, wednesdayText, thursdayText, fridayText, saturdayText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_calendar.xml 레이아웃을 인플레이트
        return inflater.inflate(R.layout.fragment_feed_cal_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 스피너 설정
        spinner = view.findViewById(R.id.spinner);

        // 초기화
        monthText = view.findViewById(R.id.monthText);
        todayText = view.findViewById(R.id.todayText);
        recyclerView = view.findViewById(R.id.recyclerview);

        // 요일 TextView 연결
        sundayText = view.findViewById(R.id.sundayText);
        mondayText = view.findViewById(R.id.mondayText);
        tuesdayText = view.findViewById(R.id.tuesdayText);
        wednesdayText = view.findViewById(R.id.wednesdayText);
        thursdayText = view.findViewById(R.id.thursdayText);
        fridayText = view.findViewById(R.id.fridayText);
        saturdayText = view.findViewById(R.id.saturdayText);

        // 현재 날짜 설정
        CalendarUtil.selecedDate = LocalDate.now();
        setTodayDate(); // 오늘 날짜를 텍스트뷰에 설정
        setMonthView(); // 기본적으로 월간 달력을 표시

        // 커스텀 레이아웃을 사용한 ArrayAdapter 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_item, getResources().getStringArray(R.array.selectCalender));

        // 드롭다운 항목의 레이아웃 설정
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        // 어댑터를 스피너에 적용
        spinner.setAdapter(adapter);

        // 드롭다운이 스피너 아래에 생성되도록 설정
        spinner.setDropDownVerticalOffset(100); // 드롭다운이 스피너에서 떨어져서 보이는 오프셋 설정

        // 스피너 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Spinner에서 선택한 항목을 확인하여 달력 전환
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("이번달")) {
                    setMonthView(); // 월간 달력 표시
                } else if (selectedItem.equals("이번주")) {
                    setWeekView(); // 주간 달력 표시
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 기본적으로 월간 달력 표시
                setMonthView();
            }
        });
    }

    // 날짜 형식 설정 (MM월 dd일 요일)
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE", Locale.KOREAN);
        return date.format(formatter);
    }

    // 오늘 날짜를 텍스트뷰에 설정하는 메서드
    private void setTodayDate() {
        LocalDate today = LocalDate.now();
        String formattedDate = monthYearFromDate(today);
        todayText.setText(formattedDate);
    }

    // 화면 설정(월간 달력)
    private void setMonthView() {
        monthText.setText(monthYearFromDate(CalendarUtil.selecedDate));
        sundayText.setText("일");
        mondayText.setText("월");
        tuesdayText.setText("화");
        wednesdayText.setText("수");
        thursdayText.setText("목");
        fridayText.setText("금");
        saturdayText.setText("토");

        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtil.selecedDate);

        CalendarAdapter adapter = new CalendarAdapter(dayList, true); // true: 월간 뷰
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    // 화면 설정(주간 달력)
    private void setWeekView() {
        monthText.setText(monthYearFromDate(CalendarUtil.selecedDate));
        sundayText.setText("일");
        mondayText.setText("월");
        tuesdayText.setText("화");
        wednesdayText.setText("수");
        thursdayText.setText("목");
        fridayText.setText("금");
        saturdayText.setText("토");

        ArrayList<LocalDate> weekList = daysInWeekArray(CalendarUtil.selecedDate);

        CalendarAdapter adapter = new CalendarAdapter(weekList, false); // false: 주간 뷰
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDay = yearMonth.lengthOfMonth();
        LocalDate firstDay = CalendarUtil.selecedDate.withDayOfMonth(1);
        int dayofweek = firstDay.get(WeekFields.of(Locale.KOREA).dayOfWeek()) - 1;

        for (int i = 1; i < 42; i++) {
            if (i <= dayofweek || i > lastDay + dayofweek) {
                dayList.add(null);
            } else {
                dayList.add(LocalDate.of(CalendarUtil.selecedDate.getYear(), CalendarUtil.selecedDate.getMonth(), i - dayofweek));
            }
        }
        return dayList;
    }

    private ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> weekDays = new ArrayList<>();
        LocalDate startOfWeek = selectedDate.with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1);

        for (int i = 0; i < 7; i++) {
            weekDays.add(startOfWeek.plusDays(i));
        }

        return weekDays;
    }

    private void previousMonth() {
        CalendarUtil.selecedDate = CalendarUtil.selecedDate.minusMonths(1);
        setMonthView(); // 달력 갱신
    }

    private void nextMonth() {
        CalendarUtil.selecedDate = CalendarUtil.selecedDate.plusMonths(1);
        setMonthView(); // 달력 갱신
    }

    private void previousWeek() {
        CalendarUtil.selecedDate = CalendarUtil.selecedDate.minusWeeks(1);
        setWeekView(); // 주간 달력 갱신
    }

    private void nextWeek() {
        CalendarUtil.selecedDate = CalendarUtil.selecedDate.plusWeeks(1);
        setWeekView(); // 주간 달력 갱신
    }
}
