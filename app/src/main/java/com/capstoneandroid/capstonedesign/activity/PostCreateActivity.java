package com.capstoneandroid.capstonedesign.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstoneandroid.capstonedesign.R;

import java.util.ArrayList;
import java.util.List;

public class PostCreateActivity extends AppCompatActivity {
    ImageButton backBtn, spinnerBtn;
    EditText contentEdit, anonName;
    //받는사람 등 추가!!!
    ImageView myChar, receiverChar;
    TextView myName, receiverName;
    CheckBox anonChk;
    Spinner spinner;
    Button okBtn;
    ArrayAdapter<String> adapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create); // 액티비티의 레이아웃 파일 설정

        backBtn = findViewById(R.id.backBtn);
        contentEdit = findViewById(R.id.contentEdit);
        anonName = findViewById(R.id.anonName);
        myChar = findViewById(R.id.myChar);
        myName = findViewById(R.id.myName);
        receiverChar = findViewById(R.id.receiverChar);
        receiverName = findViewById(R.id.receiverName);
        anonChk = findViewById(R.id.anonChk);
        spinner = findViewById(R.id.spinner);
        spinnerBtn = findViewById(R.id.spinnerBtn);
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

        //보내는 사람 나로 자동 설정
        anonChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //익명 체크 시 텍스트상자 띄우기
                myChar.setVisibility(View.INVISIBLE);
                myName.setVisibility(View.INVISIBLE);
                anonName.setVisibility(View.VISIBLE);
            }
            else {
                //익명 체크 해제 시
                myChar.setVisibility(View.VISIBLE);
                myName.setVisibility(View.VISIBLE);
                anonName.setVisibility(View.INVISIBLE);
            }
        });

        // Intent에서 식별자 가져오기
        String sourceActivity = getIntent().getStringExtra("source_activity");

        // 식별자에 따라 다른 동작을 수행
        if ("AlarmFragment".equals(sourceActivity)) {
            // 1. 쪽지 작성 버튼 클릭해서 넘어온거면 -> 받는 사람 선택
            spinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinner.performClick();
                }
            });
        } else if ("PostCheckActivity".equals(sourceActivity)) {
            // 2. 답장 보내러가기 버튼 클릭해서 넘어온거면 -> 받는 사람 자동 설정
            receiverChar.setVisibility(View.VISIBLE);
            receiverName.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            spinnerBtn.setVisibility(View.INVISIBLE);
        }

        //확인버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = contentEdit.getText().toString();
                // 다음 화면으로 이동

            }
        });
    }

    private void fetchDataFromBackend() {
        // 예를 들어, 백엔드에서 데이터를 가져오는 메서드
        // 여기에 비동기 네트워크 요청 코드 추가
        // 데이터가 도착한 후에 다음과 같이 업데이트합니다.

        // 가짜 데이터 예시
        List<String> items = new ArrayList<>();
        items.add("받는 사람을 선택하세요.");
        items.add("엄마");
        items.add("아빠");
        items.add("동생");

        // 스피너 어댑터에 아이템 추가
        adapter.clear();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }
}
