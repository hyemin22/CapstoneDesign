package com.capstoneandroid.capstonedesign.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.capstoneandroid.capstonedesign.EmojiFilter;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.fragment.MissionCompleteFragment;
import com.capstoneandroid.capstonedesign.model.Mission;
import com.capstoneandroid.capstonedesign.repository.MissionRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MissionCreateActivity extends BaseActivity {
    Long userId = UserInfoManager.getInstance().getUserId();
    Long idNum; // 미션 아이디
    ImageButton backBtn, hamBtn;
    TextView pageTitle, ment, cycle, weekmonthText, timeSelect;
    EditText titleEdit, emojiSelect;
    Spinner cycleSpinner, repeat_timeSpinner;
    LinearLayout days, weekmonth;
    Switch alarmSwitch;
    Button okBtn;
    ArrayAdapter<String> adapter, adapter2;
    Integer cycleId, repeatNum = null;
    Dialog dialogDelete;
    private HashMap<String, Integer> selectedDays = new LinkedHashMap<>();
    private List<Button> dayButtons;
    StringBuilder selectedDaysString = new StringBuilder();
    private String selectedTime, repeatDayText, cycleType;
    HashMap<String, Integer> dayNumberMap;
    int activityState;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_create);

        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        pageTitle = findViewById(R.id.pageTitle);
        ment = findViewById(R.id.ment);
        cycle = findViewById(R.id.cycle);
        weekmonthText = findViewById(R.id.weekmonthText);
        titleEdit = findViewById(R.id.titleEdit);
        emojiSelect = findViewById(R.id.emojiSelect);
        cycleSpinner = findViewById(R.id.spinner);
        repeat_timeSpinner = findViewById(R.id.spinner2);
        days = findViewById(R.id.days);
        weekmonth = findViewById(R.id.weekmonth);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        timeSelect = findViewById(R.id.timeSelect); //시간 선택 스피너 필요
        okBtn = findViewById(R.id.okBtn);

        // 요일 버튼
        Button btnMonday = findViewById(R.id.monday);
        Button btnTuesday = findViewById(R.id.tuesday);
        Button btnWednesday = findViewById(R.id.wednesday);
        Button btnThursday = findViewById(R.id.thursday);
        Button btnFriday = findViewById(R.id.friday);
        Button btnSaturday = findViewById(R.id.saturday);
        Button btnSunday = findViewById(R.id.sunday);

        // 버튼들을 리스트로 추가
        dayButtons = Arrays.asList(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday, btnSunday);

        // 요일과 매핑된 숫자 값 저장
        dayNumberMap = new HashMap<>();
        dayNumberMap.put("월", 1);
        dayNumberMap.put("화", 2);
        dayNumberMap.put("수", 3);
        dayNumberMap.put("목", 4);
        dayNumberMap.put("금", 5);
        dayNumberMap.put("토", 6);
        dayNumberMap.put("일", 7);

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        cycleSpinner.setAdapter(adapter);

        // 스피너와 어댑터 초기화
        adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown);
        repeat_timeSpinner.setAdapter(adapter2);

        // 주기 스피너 초기화
        setCycleList();

        // Intent로 전달된 데이터 받기 - 어떤 화면에서 넘어왔는지 먼저 확인
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        // 어떤 화면에서 넘어왔는지에 따라 보여지는 화면이 다름
        if ("MyMissionAdapter".equals(source) || "DayMissionAdapter".equals(source)) {
            // MyMissionAdapter에서 넘어온 경우 - 미션 확인

            activityState = 1; // 확인 화면

            //백엔드에서 정보 가져와서 내용 채우기
            idNum = intent.getLongExtra("id", -1L);
            String titleText = intent.getStringExtra("title");
            String emojiText = intent.getStringExtra("emoji");
            String cycleText = intent.getStringExtra("cycle");
            repeatDayText = intent.getStringExtra("repeat_day");
            Integer repeat_timeNum = intent.getIntExtra("repeat_time", -1);
            boolean alarm = intent.getBooleanExtra("alarm", false);
            String alarm_timeText = intent.getStringExtra("alarm_time");

            titleEdit.setText(titleText); //제목 채우기
            emojiSelect.setText(emojiText); //이모지 채우기
            emojiSelect.setBackground(null);
            setCycleSelection(cycleText); //주기 스피너 설정(일일목표, 주간목표&&repeat_day null임, 월간목표, 주간목표&&repeat_day null이 아님)
            setRepeatDayButtons(repeatDayText);
            setRepeatTimeSelection(repeat_timeNum); //반복 횟수 설정 - 스피너 설정
            alarmSwitch.setChecked(alarm); //알림 채우기
            timeSelect.setText(alarm_timeText); //알림시간 채우기

            String title = intent.getStringExtra("title");
            titleEdit.setText(title);
            hamBtn.setVisibility(View.VISIBLE);
            pageTitle.setText("미션 확인하기");
            ment.setVisibility(View.GONE);
            titleEdit.setEnabled(false);
            emojiSelect.setEnabled(false);
            cycleSpinner.setEnabled(false);
            repeat_timeSpinner.setEnabled(false);
            alarmSwitch.setEnabled(false);
            timeSelect.setEnabled(false);
            okBtn.setVisibility(View.GONE);

            // 모달창
            dialogDelete = new Dialog(MissionCreateActivity.this);
            dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogDelete.setContentView(R.layout.activity_custom_dialog_create);

            // 햄버거 버튼 설정
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
                                activityState = 2; // 수정 화면

                                pageTitle.setText("미션 수정하기");
                                titleEdit.setEnabled(true);
                                emojiSelect.setEnabled(true);
                                cycleSpinner.setEnabled(true);
                                repeat_timeSpinner.setEnabled(true);
                                alarmSwitch.setEnabled(true);
                                timeSelect.setEnabled(true);
                                okBtn.setVisibility(View.VISIBLE);
                                okBtn.setText("수정하기");

                                // 반복 요일 버튼들도 활성화
                                for (Button button : dayButtons) {
                                    button.setEnabled(true);
                                }

                                //수정된사항 DB에 저장!!!
                                okBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String title = titleEdit.getText().toString(); //제목
                                        String emoji = emojiSelect.getText().toString(); //이모지
                                        String repeat_day = selectedDaysString.toString(); //반복 요일
                                        selectedTime = timeSelect.getText().toString(); //설정된 시간을 일단 저장

                                        if(selectedTime != null) {
                                            // AM/PM 형식의 시간을 LocalTime으로 파싱
                                            if (selectedTime.contains("AM")) {
                                                selectedTime = selectedTime.replaceAll(" AM","").trim();
                                                selectedTime = selectedTime + ":00";
                                            } else if (selectedTime.contains("PM")) {
                                                selectedTime = selectedTime.replaceAll(" PM", "").trim();
                                                System.out.println("timetime" + selectedTime);
                                                String[] timeParts = selectedTime.split(":");
                                                int hour = Integer.parseInt(timeParts[0]) + 12;
                                                String minute = (timeParts[1].length() == 1) ? "0" + timeParts[1] : timeParts[1];

                                                selectedTime = hour + ":" + minute + ":00";
                                            }
                                        }

                                        if (alarm && (timeSelect.getText() == null)) { //알람은 설정했는데 알람 시간 설정을 안하고 저장하기 클릭한 경우
                                            Toast.makeText(MissionCreateActivity.this, "알람 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Mission mission = new Mission(idNum, title, emoji, cycleId, repeat_day, repeatNum, alarm, selectedTime);

                                            updateMissionData(mission);
                                        }
                                    }
                                });

                                return true;
                            } else if (itemId == R.id.delete) { // 삭제
                                // 모달창에서 미션 삭제할지 질문
                                dialogDelete.show();

                                // askTextView의 텍스트를 변경
                                TextView askTextView = dialogDelete.findViewById(R.id.askTextView);
                                if (askTextView != null) {
                                    askTextView.setText("미션을 삭제하시겠어요?");
                                }

                                // explainTextView의 텍스트를 변경
                                TextView explainTextView = dialogDelete.findViewById(R.id.explainTextView);
                                if (explainTextView != null) {
                                    explainTextView.setText("한번 삭제 시 복구가 불가능해요.");
                                }

                                Button noBtn = dialogDelete.findViewById(R.id.noButton);
                                noBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogDelete.dismiss();
                                    }
                                });

                                dialogDelete.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // MissionActivity로 이동하는 Intent 생성
                                        Intent intent = new Intent(view.getContext(), MissionActivity.class);
                                        view.getContext().startActivity(intent);

                                        // db에서 현재 미션 삭제
                                        deleteMissionData();
                                        finish(); // 현재 액티비티 종료

                                        // 현재
                                        if (view.getContext() instanceof Activity) {
                                            ((Activity) view.getContext()).finish();
                                        }
                                    }
                                });
                                return true;
                            }
                            return false;
                        }
                    });

                    // 팝업 메뉴 보여주기
                    popupMenu.show();
                }
            });

        } else if ("MissionActivity".equals(source)) {
            // MissionActivity에서 넘어온 경우 - 작성 화면
            activityState = 3; // 작성 화면

            hamBtn.setVisibility(View.INVISIBLE);
            ment.setVisibility(View.VISIBLE);
            pageTitle.setText("미션 생성하기");
            titleEdit.setEnabled(true);
            emojiSelect.setEnabled(true);
            cycleSpinner.setEnabled(true);
            repeat_timeSpinner.setEnabled(true);
            alarmSwitch.setEnabled(true);
            timeSelect.setEnabled(true);
            okBtn.setVisibility(View.VISIBLE);
            okBtn.setText("등록하기");

            // 등록하기 버튼 - DB에 미션 내용 저장
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = titleEdit.getText().toString(); //제목
                    String emoji = emojiSelect.getText().toString(); //이모지
                    String repeat_day = selectedDaysString.toString(); // 반복 요일
                    boolean alarm = alarmSwitch.isChecked(); // 알람 여부

                    if (alarm && (selectedTime == null)) { //알람은 설정했는데 알람 시간 설정을 안하고 저장하기 클릭한 경우
                        Toast.makeText(MissionCreateActivity.this, "알람 시간을 설정해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Mission mission = new Mission(userId, title, emoji, cycleId, repeat_day, repeatNum, alarm, selectedTime);

                        // MissionCompleteFragment 생성
                        MissionCompleteFragment fragment = new MissionCompleteFragment();

                        // Activity의 루트 뷰를 숨기기
                        View mainView = findViewById(R.id.main);
                        mainView.setVisibility(View.GONE); // 루트 뷰를 GONE 상태로 설정

                        // 프래그먼트를 현재 Activity 화면에 표시
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(android.R.id.content, fragment) // 전체 화면을 프래그먼트로 교체
                                .commit();

                        sendMissionData(mission);
                    }
                }
            });
        }

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 이모지 선택 버튼 설정
        emojiSelect.setFilters(new InputFilter[]{new EmojiFilter()});
        emojiSelect.addTextChangedListener(new TextWatcher() {
            private int previousLength = 0; // 이전 텍스트 길이 저장

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력 중 작업
                if (s.length() > 0) {
                    // 텍스트가 입력되면 배경 제거
                    emojiSelect.setBackground(null);

                    if (s.length() > previousLength) {
                        // 텍스트가 길어지면 마지막 이모지만 남기고 나머지를 삭제
                        String lastEmoji = s.subSequence(s.length() - count, s.length()).toString();
                        emojiSelect.removeTextChangedListener(this); // 무한 루프 방지를 위해 리스너 제거
                        emojiSelect.setText(lastEmoji); // 마지막 이모지만 설정
                        emojiSelect.setSelection(lastEmoji.length()); // 커서를 마지막 위치로 이동
                        emojiSelect.addTextChangedListener(this); // 리스너 다시 추가
                    }
                } else {
                    // 텍스트가 없으면 아이콘 셀렉트 버튼
                    emojiSelect.setBackgroundResource(R.drawable.ic_emojiselect);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //주기 스피너
        cycleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목 가져오기
                cycleType = parent.getItemAtPosition(position).toString();

                // 주기 선택에 따라 UI 변경
                updateCycleUI(cycleType);

                // 선택된 주기의 ID를 저장
                cycleId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 선택되지 않은 상태에 대한 처리
                cycle.setVisibility(View.GONE);
                days.setVisibility(View.GONE);
                weekmonth.setVisibility(View.GONE);
            }
        });

        // 요일 버튼 초기화
        initializeButtons();

        //알림 시간 선택
        timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmSwitch.isChecked()) {
                    showTimePickerDialog(timeSelect);
                } else {
                    // 필요하면 알림 메세지 등을 표시할 수 있습니다.
                    Toast.makeText(v.getContext(), "알림이 꺼져 있을 때는 시간을 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCycleSelection(String cycleText) {
        // 기존 리스너를 임시로 해제
        cycleSpinner.setOnItemSelectedListener(null);

        // 선택할 스피너 위치를 위한 변수
        int position = 0;

        // 조건에 따라 스피너의 선택 위치를 결정
        if ("일일 목표".equals(cycleText) || "매일".equals(cycleText)) {
            position = 1;  // "매일" 선택
        } else if ("주간 목표".equals(cycleText) && (repeatDayText == null || repeatDayText.isEmpty())) {
            position = 2;  // "한 주에" 선택
        } else if ("월간 목표".equals(cycleText)) {
            position = 3;  // "한 달에" 선택
        } else if ("주간 목표".equals(cycleText) || "직접 지정".equals(cycleText)) {
            position = 4;  // "직접 지정" 선택
        }

        // 스피너에서 해당 위치 선택
        cycleSpinner.setSelection(position);
        updateCycleUI(cycleSpinner.getSelectedItem().toString());

        // 리스너 다시 연결
        cycleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cycleType = parent.getItemAtPosition(position).toString();
                updateCycleUI(cycleType);
                cycleId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cycle.setVisibility(View.GONE);
                days.setVisibility(View.GONE);
                weekmonth.setVisibility(View.GONE);
            }
        });

        // 요일 버튼 상태 설정
        setRepeatDayButtons(repeatDayText);
    }

    // 요일 버튼 초기화
    private void initializeButtons() {
        // 각 버튼에 클릭 리스너 추가
        for (Button button : dayButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    String day = button.getText().toString(); // 버튼 텍스트를 통해 요일 이름 얻기
                    Integer dayNumber = dayNumberMap.get(day); // 요일 이름을 통해 숫자 매핑 가져오기

                    // 버튼이 선택된 상태인지 확인
                    if (button.isSelected()) {
                        // 버튼이 선택된 상태라면 선택 해제
                        button.setSelected(false);
                        button.setTextColor(getResources().getColor(R.color.gray)); // 색상 복구

                        // 해시맵에서 해당 요일 제거
                        selectedDays.remove(day);
                    } else {
                        // 버튼이 선택되지 않은 상태라면 선택
                        button.setSelected(true);
                        button.setTextColor(getResources().getColor(R.color.white)); // 색상 변경

                        // 해시맵에 해당 요일 추가
                        selectedDays.put(day, dayNumber);
                    }

                    // 선택된 요일을 String으로 업데이트
                    updateSelectedDaysString();
                }
            });
        }
    }

    private void setRepeatDayButtons(String repeat_day) {
        if (repeat_day != null && !repeat_day.isEmpty()) {
            // 선택할 요일 버튼 인덱스 가져오기
            List<Integer> selectedDaysIndices = new ArrayList<>();

            for (String dayNumStr : repeat_day.split(",")) {
                dayNumStr = dayNumStr.trim(); // 공백 제거
                try {
                    selectedDaysIndices.add(Integer.parseInt(dayNumStr) - 1); // 0부터 시작하는 인덱스
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // 숫자 파싱 오류 처리
                }
            }

            // 요일에 맞는 버튼들을 선택 상태로 설정
            for (int index : selectedDaysIndices) {
                if (index >= 0 && index < dayButtons.size()) {
                    Button button = dayButtons.get(index);
                    Integer dayNumber = dayNumberMap.get(button.getText().toString()); // 요일 이름을 통해 숫자 매핑 가져오기

                    button.setSelected(true);
                    button.setTextColor(getResources().getColor(R.color.white));

                    // 해시맵에 해당 요일 추가
                    selectedDays.put(button.getText().toString(), dayNumber);
                }
            }
        }
    }

    private void setRepeatTimeSelection(Integer repeat_time) {
        // repeat_time 값이 1부터 7 사이일 때만 스피너 선택
        if (repeat_time >= 1 && repeat_time <= 7) {
            repeat_timeSpinner.setSelection(repeat_time - 1); // repeat_time에 맞는 인덱스 선택
        }
    }

    private void setCycleList() {
        List<String> items = new ArrayList<>();
        items.add("미션을 수행할 주기를 선택해주세요.");
        items.add("매일");
        items.add("한 주에");
        items.add("한 달에");
        items.add("직접 지정");

        // 스피너 어댑터에 아이템 추가
        adapter.clear();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }

    private void updateCycleUI(String cycleType) {
        switch (cycleType) {
            case "매일":
                cycle.setVisibility(View.GONE);
                days.setVisibility(View.GONE);
                break;
            case "한 주에":
                // 한 주에 선택 시 처리
                cycle.setVisibility(View.VISIBLE);
                cycle.setText("주 목표");
                days.setVisibility(View.GONE);
                weekmonth.setVisibility(View.VISIBLE);
                weekmonthText.setText("한 주에");
                setNumList();
                break;
            case "한 달에":
                // 한 달에 선택 시 처리
                cycle.setVisibility(View.VISIBLE);
                cycle.setText("월 목표");
                days.setVisibility(View.GONE);
                weekmonth.setVisibility(View.VISIBLE);
                weekmonthText.setText("한 달에");
                setNumList();
                break;
            case "직접 지정":
                // 직접 지정 선택 시 처리
                cycle.setVisibility(View.VISIBLE);
                cycle.setText("반복 요일");
                days.setVisibility(View.VISIBLE);
                weekmonth.setVisibility(View.GONE);

                // 각 버튼의 현재 상태를 그대로 유지하면서 활성화 가능하게 설정
                for (Button button : dayButtons) {
                    button.setSelected(false);
                    button.setTextColor(getResources().getColor(R.color.gray));
                    button.setEnabled(activityState != 1); // 확인 화면이면 비활성화
                }

                setRepeatDayButtons(repeatDayText);

                break;
            default:
                cycle.setVisibility(View.GONE);
                days.setVisibility(View.GONE);
                weekmonth.setVisibility(View.GONE);
                break;
        }
    }

    private void setNumList() {
        repeatNum = null; // 선택한 숫자 저장

        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");

        // 스피너 어댑터에 아이템 추가
        adapter2.clear();
        adapter2.addAll(items);
        adapter2.notifyDataSetChanged();

        // 스피너 리스너
        repeat_timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 스피너의 선택된 항목을 문자열로 가져오기
                String selectedItem = parent.getItemAtPosition(position).toString();

                // 선택된 문자열을 Integer로 변환하여 저장
                repeatNum = Integer.parseInt(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때 처리
            }
        });
    }

    private void updateSelectedDaysString() {
        selectedDaysString = new StringBuilder();

        // 선택된 요일들을 쉼표로 구분하여 String으로 변환
        for (Integer dayNumber : selectedDays.values()) {
            selectedDaysString.append(dayNumber).append(", ");
        }

        if (selectedDaysString.length() > 0) {
            selectedDaysString.setLength(selectedDaysString.length() - 2); // 마지막 쉼표 제거
        }

        // 예: "1, 3, 5" 형식으로 String 표시
        Toast.makeText(this, selectedDaysString.toString(), Toast.LENGTH_SHORT).show();
    }

    private void showTimePickerDialog(final TextView targetTextView) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 12시간 형식으로 TimePickerDialog 생성
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, // 스타일 적용 (Optional)
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // 선택된 시간을 AM/PM 형식으로 변환하여 TextView에 표시
                        String amPm = (hourOfDay < 12) ? "AM" : "PM";
                        int hour = (hourOfDay % 12 == 0) ? 12 : hourOfDay % 12; // 12시간 형식으로 변환
                        String time = String.format("%02d:%02d %s", hour, minute, amPm);
                        targetTextView.setText(time);

                        // 선택된 시간을 LocalTime으로 저장
                        LocalTime timeinfo = LocalTime.of(hourOfDay, minute, 0, 0);

                        // LocalTime 객체를 "HH:mm:ss" 형식의 문자열로 변환
                        selectedTime = timeinfo.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    }
                }, hour, minute, false); // 마지막 파라미터가 false일 때 12시간 형식 사용

        // TimePickerDialog 보여주기
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void sendMissionData(Mission mission) {
        // 서버로 POST 요청 보내기
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.sendMissionToServer(mission, new MissionRepository.MissionCallback() {
            @Override
            public void onSuccess() {
                Log.d("MissionCreateActivity", "미션이 성공적으로 추가되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MissionCreateActivity", "미션 추가 실패: " + errorMessage);
            }
        });
    }

    private void updateMissionData(Mission mission) {
        // 서버로 PUT 요청 보내기
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.updateMissionToServer(mission, new MissionRepository.MissionCallback() {
            @Override
            public void onSuccess() {
                Log.d("MissionCreateActivity", "미션이 성공적으로 수정되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MissionCreateActivity", "미션 수정 실패: " + errorMessage);
            }
        });
    }

    private void deleteMissionData() {
        //서버로 DELETE 요청 보내기
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.deleteMissionToServer(idNum, new MissionRepository.MissionCallback() {
            @Override
            public void onSuccess() {
                Log.d("MissionCreateActivity", "미션이 성공적으로 삭제되었습니다");
                finish(); //현재 액티비티 종료
            }
            @Override
            public void onFailure(String errorMessage) {
                Log.e("MissionCreateActivity", "미션 삭제 실패: " + errorMessage);
            }
        });
    }
}
