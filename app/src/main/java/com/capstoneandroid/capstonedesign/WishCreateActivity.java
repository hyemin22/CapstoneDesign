package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WishCreateActivity extends AppCompatActivity {

    ImageButton backBtn, spinnerBtn, iconSelect;
    EditText titleEdit, memoEdit;
    TextView startDay, endDay, plusText;
    Spinner spinner;
    Switch alarmSwitch;
    Button okBtn;


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

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //제목 DB에 저장
    }
}
