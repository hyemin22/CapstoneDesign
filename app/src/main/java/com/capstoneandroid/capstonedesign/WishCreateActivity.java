package com.capstoneandroid.capstonedesign;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WishCreateActivity extends AppCompatActivity {

    ImageButton backBtn, spinnerBtn;
    ImageView iconSelect;
    EditText titleEdit, memoEdit;
    TextView startDay, endDay, plusText;
    Spinner spinner;
    Switch alarmSwitch;
    Button okBtn;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wish_create);

        backBtn = findViewById(R.id.backBtn);
        spinnerBtn = findViewById(R.id.spinnerBtn);
        iconSelect = findViewById(R.id.iconSelect);
        titleEdit = findViewById(R.id.titleEdit);
        memoEdit = findViewById(R.id.memoEdit);
        startDay = findViewById(R.id.startDay);
        endDay = findViewById(R.id.endDay);
        plusText = findViewById(R.id.plusText);
        spinner = findViewById(R.id.spinner);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        okBtn = findViewById(R.id.okBtn);

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 백엔드에서 아이템 가져오기
        fetchDataFromBackend();

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //제목 DB에 저장

        //성취 예정일 스피너
        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        //성취 예정일 스피너
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        //카테고리 추가하기 버튼
        plusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카테고리 추가 화면
            }
        });

        //카테고리 스피너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목에 대한 처리
                String selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 선택되지 않은 상태에 대한 처리
            }
        });

        //아이콘 선택
        iconSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이콘 어떻게 보이도록?
            }
        });

        //메모 DB에 저장

        //알림
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // Switch가 켜졌을 때 동작하는 코드
                } else {
                    // Switch가 꺼졌을 때 동작하는 코드
                }
            }
        });

        //등록하기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //예정된 위시리스트 목록에 추가
            }
        });
    }

    private void showDatePickerDialog() {
        // 현재 날짜로 설정
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 스피너 스타일의 DatePickerDialog 생성
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, // 스피너 형태로 설정
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 선택된 날짜로 캘린더 객체 설정
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // 요일을 얻기 위한 배열
                        String[] daysOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
                        int dayOfWeekIndex = selectedDate.get(Calendar.DAY_OF_WEEK) - 1; // 요일 인덱스 얻기

                        // 월과 일을 두 자리 숫자로 형식화
                        String formattedDate = String.format("%04d.%02d.%02d(%s)", year, month + 1, dayOfMonth, daysOfWeek[dayOfWeekIndex]);

                        startDay.setText(formattedDate);
                    }
                },
                year, month, day);

        // 다이얼로그를 스피너 모드로 설정
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

    private void fetchDataFromBackend() {
        // 예를 들어, 백엔드에서 데이터를 가져오는 메서드
        // 여기에 비동기 네트워크 요청 코드 추가
        // 데이터가 도착한 후에 다음과 같이 업데이트합니다.

        // 가짜 데이터 예시
        List<String> items = new ArrayList<>();
        items.add("선택해주세요.");
        items.add("맛집");
        items.add("여행");
        items.add("기타");

        // 스피너 어댑터에 아이템 추가
        adapter.clear();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }
}
