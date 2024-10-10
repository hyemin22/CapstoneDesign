package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstoneandroid.capstonedesign.R;

import java.util.ArrayList;
import java.util.List;

public class MissionCreateActivity extends AppCompatActivity {
    ImageButton backBtn, hamBtn;
    TextView ment, cycle, underlineText, weekmonthText, timeSelect;
    EditText titleEdit;
    ImageView iconSelect;
    Spinner spinner, spinner2;
    CheckBox everydayChk;
    LinearLayout days, weekmonth;
    Switch alarmSwitch;
    Button okBtn;
    ArrayAdapter<String> adapter, adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_create);

        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        ment = findViewById(R.id.ment);
        cycle = findViewById(R.id.cycle);
        underlineText = findViewById(R.id.underlineText);
        weekmonthText = findViewById(R.id.weekmonthText);
        timeSelect = findViewById(R.id.timeSelect); //시간 선택 스피너 필요
        titleEdit = findViewById(R.id.titleEdit);
        iconSelect = findViewById(R.id.iconSelect);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        everydayChk = findViewById(R.id.everydayChk);
        days = findViewById(R.id.days);
        weekmonth = findViewById(R.id.weekmonth);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        okBtn = findViewById(R.id.okBtn);

        // Intent로 전달된 데이터 받기 - 어떤 화면에서 넘어왔는지 먼저 확인
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        // 어떤 화면에서 넘어왔는지에 따라 보여지는 화면이 다름
        if ("MyMissionAdapter".equals(source) || "DayMissionAdapter".equals(source)) {
            // MyMissionAdapter에서 넘어온 경우 - 미션 확인
            String title = intent.getStringExtra("title");
            titleEdit.setText(title);

            hamBtn.setVisibility(View.VISIBLE);
            ment.setVisibility(View.GONE);
            timeSelect.setEnabled(false);
            titleEdit.setEnabled(false);
            iconSelect.setEnabled(false);
            spinner.setEnabled(false);
            spinner2.setEnabled(false);
            alarmSwitch.setEnabled(false);
            okBtn.setVisibility(View.GONE);

            //햄버거 버튼 설정
            hamBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // PopupMenu 생성
                    PopupMenu popupMenu = new PopupMenu(MissionCreateActivity.this, hamBtn);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());

                    // 메뉴 항목 클릭 리스너 설정
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int itemId = item.getItemId();
                            if (itemId == R.id.edit) { // 수정
                                timeSelect.setEnabled(true);
                                titleEdit.setEnabled(true);
                                iconSelect.setEnabled(true);
                                spinner.setEnabled(true);
                                spinner2.setEnabled(true);
                                alarmSwitch.setEnabled(true);
                                okBtn.setVisibility(View.VISIBLE);
                                okBtn.setText("수정하기");

                                //수정된사항 DB에 저장!!!

                                return true;
                            } else if (itemId == R.id.delete) { // 삭제
                                // 삭제!!!!!
                                finish(); // 현재 액티비티 종료
                                return true;
                            }
                            return false;
                        }
                    });

                    // 팝업 메뉴 보여주기
                    popupMenu.show();
                }
            });

            //백엔드에서 정보 가져와서 내용 채우기!!!!!
            //제목 채우기
            //아이콘 채우기
            //주기 설정 - 내용 채우기
            //알림 채우기

        } else if ("MissionActivity".equals(source)) {
            // MissionActivity에서 넘어온 경우 - 작성 화면
            hamBtn.setVisibility(View.INVISIBLE);
            ment.setVisibility(View.VISIBLE);
            timeSelect.setEnabled(true);
            titleEdit.setEnabled(true);
            iconSelect.setEnabled(true);
            spinner.setEnabled(true);
            spinner2.setEnabled(true);
            alarmSwitch.setEnabled(true);
            okBtn.setVisibility(View.VISIBLE);
            okBtn.setText("등록하기");

            //DB에 미션 내용 저장
            //미션 메인 화면에 미션 추가
        }

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 스피너와 어댑터 초기화
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        // 백엔드에서 아이템 가져오기
        fetchDataFromBackend();

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //아이콘 선택
        iconSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이콘 어떻게 보이도록?
            }
        });

        //주기 선택 스피너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목에 대한 처리
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch (selectedItem) {
                    case "매일":
                        // 매일 선택 시 처리
                        cycle.setVisibility(View.VISIBLE);
                        cycle.setText("반복 요일");
                        underlineText.setVisibility(View.VISIBLE);
                        everydayChk.setVisibility(View.VISIBLE);
                        days.setVisibility(View.VISIBLE);
                        weekmonth.setVisibility(View.GONE);
                        break;
                    case "주마다":
                        // 주마다 선택 시 처리
                        cycle.setVisibility(View.VISIBLE);
                        cycle.setText("주 목표");
                        underlineText.setVisibility(View.GONE);
                        everydayChk.setVisibility(View.GONE);
                        days.setVisibility(View.GONE);
                        weekmonth.setVisibility(View.VISIBLE);
                        weekmonthText.setText("주마다");
                        fetchDataFromBackend2();
                        break;
                    case "월마다":
                        // 월마다 선택 시 처리
                        cycle.setVisibility(View.VISIBLE);
                        cycle.setText("월 목표");
                        underlineText.setVisibility(View.GONE);
                        everydayChk.setVisibility(View.GONE);
                        days.setVisibility(View.GONE);
                        weekmonth.setVisibility(View.VISIBLE);
                        weekmonthText.setText("월마다");
                        fetchDataFromBackend2();
                        break;
                    case "직접 지정":
                        // 직접 지정 선택 시 처리
                        cycle.setVisibility(View.VISIBLE);
                        cycle.setText("반복 요일");
                        underlineText.setVisibility(View.GONE);
                        everydayChk.setVisibility(View.VISIBLE);
                        days.setVisibility(View.VISIBLE);
                        weekmonth.setVisibility(View.GONE);
                        break;
                    default:
                        cycle.setVisibility(View.GONE);
                        underlineText.setVisibility(View.GONE);
                        everydayChk.setVisibility(View.GONE);
                        days.setVisibility(View.GONE);
                        weekmonth.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 선택되지 않은 상태에 대한 처리
                cycle.setVisibility(View.GONE);
                underlineText.setVisibility(View.GONE);
                everydayChk.setVisibility(View.GONE);
                days.setVisibility(View.GONE);
                weekmonth.setVisibility(View.GONE);
            }
        });

        //반복 횟수 스피너
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목에 대한 처리
                String selectedItem = parent.getItemAtPosition(position).toString();
                // 선택 횟수 DB에 저장!!!
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 선택되지 않은 상태에 대한 처리
            }
        });

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

        //알림 시간 선택!!!!!

        //등록하기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 내용 저장
                //미션 목록에 추가
            }
        });
    }

    private void fetchDataFromBackend() {
        // 예를 들어, 백엔드에서 데이터를 가져오는 메서드
        // 여기에 비동기 네트워크 요청 코드 추가
        // 데이터가 도착한 후에 다음과 같이 업데이트합니다.

        // 가짜 데이터 예시
        List<String> items = new ArrayList<>();
        items.add("미션을 수행할 주기를 선택해주세요.");
        items.add("매일");
        items.add("주마다");
        items.add("월마다");
        items.add("직접 지정");

        // 스피너 어댑터에 아이템 추가
        adapter.clear();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }

    private void fetchDataFromBackend2() {
        // 예를 들어, 백엔드에서 데이터를 가져오는 메서드
        // 여기에 비동기 네트워크 요청 코드 추가
        // 데이터가 도착한 후에 다음과 같이 업데이트합니다.

        // 가짜 데이터 예시
        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        //주마다 몇번이 무슨 의미?

        // 스피너 어댑터에 아이템 추가
        adapter2.clear();
        adapter2.addAll(items);
        adapter2.notifyDataSetChanged();
    }

    private void daySelected() {
        Button selectedButton;

//        public void onClick(View view) {
//            // 현재 선택된 버튼이 있는 경우
//            if (selectedButton != null) {
//                // 현재 선택된 버튼을 unselected 상태로 변경
//                selectedButton.setSelected(false);
//            }
//            // 새로 클릭된 버튼을 selected 상태로 변경
//            category.setSelected(true);
//            selectedButton = category;
//
//            // 화면 전환 또는 추가 작업 수행
//            //어떤 카테고리를 클릭했느냐에 따라 보여지는 위시리스트 목록이 다름 - 백엔드
//            //updateForSelectedButton(category);
//        }
    }
}
