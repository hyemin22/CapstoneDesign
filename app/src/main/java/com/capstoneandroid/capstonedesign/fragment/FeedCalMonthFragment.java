package com.capstoneandroid.capstonedesign.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import java.util.Calendar;
import java.util.Locale;

public class FeedCalMonthFragment extends Fragment implements CalendarAdapter.OnDateSelectedListener {

    TextView monthText; // 월 일 요일 텍스트뷰
    RecyclerView recyclerView;
    private Spinner spinner;
    ImageButton dateSpinnerBtn;
    // 요일 TextView 변수 선언
    TextView sundayText, mondayText, tuesdayText, wednesdayText, thursdayText, fridayText, saturdayText;

    boolean isMonthlyView = true; // 현재 달력 모드 (월간 뷰 기본값)

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
        recyclerView = view.findViewById(R.id.recyclerview);
        dateSpinnerBtn = view.findViewById(R.id.dateSpinnerBtn);

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
        updateMonthText();
        setWeekView(); // 기본적으로 주간 달력을 표시

        // 스피너 설정
        setupSpinner();

        // 날짜 변경 버튼 클릭 이벤트
        dateSpinnerBtn.setOnClickListener(v -> showDatePickerDialog(monthText));

        // 날짜 설정 데이트픽커
        monthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(monthText);
            }
        });
    }

    private void setupSpinner() {
        // 커스텀 레이아웃을 사용한 ArrayAdapter 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_item, getResources().getStringArray(R.array.selectCalender));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        // 어댑터를 스피너에 적용
        spinner.setAdapter(adapter);

        // 드롭다운이 스피너 아래에 생성되도록 설정
        spinner.setDropDownVerticalOffset(100); // 드롭다운이 스피너에서 떨어져 보이는 오프셋 설정

        // 스피너 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if ("이번주".equals(selectedItem)) {
                    isMonthlyView = false;
                    setWeekView(); // 주간 달력 표시
                } else if ("이번달".equals(selectedItem)) {
                    isMonthlyView = true;
                    setMonthView(); // 월간 달력 표시
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 기본적으로 주간 달력 표시
                setWeekView();
            }
        });
    }

    // 날짜 형식 설정 (YYYY년 MM월 dd일)
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY년 MM월 dd일", Locale.KOREAN);
        return date.format(formatter);
    }

    // 날짜 형식 설정 (YYYY년 MM월 dd일)
    private void updateMonthText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN);
        monthText.setText(CalendarUtil.selecedDate.format(formatter));
    }

    @Override
    public void onDateSelected(LocalDate date) {
        // 선택된 날짜로 CalendarUtil.selecedDate를 업데이트
        CalendarUtil.selecedDate = date;

        updateMonthText(); // TextView 업데이트
        // 선택된 날짜를 형식화하여 monthText에 설정
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN));
        monthText.setText(formattedDate);
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
        monthText.setText(CalendarUtil.selecedDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN)));

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
        monthText.setText(CalendarUtil.selecedDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN)));

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
        int dayOfWeek = firstDay.getDayOfWeek().getValue() % 7; // 일요일 시작 기준

        for (int i = 1; i < 42; i++) {
            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add(null);
            } else {
                dayList.add(LocalDate.of(date.getYear(), date.getMonth(), i - dayOfWeek));
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

    private void showDatePickerDialog(final TextView targetTextView) {
        // 현재 날짜로 설정
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 생성
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, // 스피너 스타일 적용
                (view, year1, month1, dayOfMonth) -> {
                    CalendarUtil.selecedDate = LocalDate.of(year1, month1 + 1, dayOfMonth);
                    updateMonthText();

                    if (isMonthlyView) {
                        setMonthView();
                    } else {
                        setWeekView();
                    }
                },
                year, month, day);

        // 다이얼로그를 스피너 모드로 설정
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

}