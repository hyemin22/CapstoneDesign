package com.capstoneandroid.capstonedesign.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.capstoneandroid.capstonedesign.R;

import java.util.Calendar;

public class DiaryCreateActivity extends AppCompatActivity {

    private EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_create);

        // XML에서 EditText 참조
        editTextDate = findViewById(R.id.editTextDate);

        // EditText 클릭 시 DatePickerDialog 표시
        editTextDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        // 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 생성 및 표시
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.CustomDatePickerDialog, // 사용자 정의 테마
                (view, selectedYear, selectedMonth, selectedDay) -> {
            // 선택된 날짜를 YYYY.MM.DD 형식으로 EditText에 설정
            String selectedDate = selectedYear + "." + (selectedMonth + 1) + "." + selectedDay;
            editTextDate.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }
}