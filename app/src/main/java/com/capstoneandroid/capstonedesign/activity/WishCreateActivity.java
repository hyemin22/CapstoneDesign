package com.capstoneandroid.capstonedesign.activity;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.GuestbookAdapter;
import com.capstoneandroid.capstonedesign.adapter.WishExpectedAdapter;
import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.item.WishExpectedItem;
import com.capstoneandroid.capstonedesign.model.WishList;
import com.capstoneandroid.capstonedesign.repository.GuestBookRepository;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WishCreateActivity extends BaseActivity {

    ImageButton backBtn, hamBtn, spinnerBtn;
    ImageView iconSelect;
    EditText titleEdit, memoEdit, emojiEdit;
    TextView ment, startDay, endDay, plusText;
    Spinner spinner;
    Switch alarmSwitch;
    Button okBtn;
    ArrayAdapter<String> adapter;

    private static final int REQUEST_CODE = 100;  // 요청 코드
    private ArrayList<WishExpectedItem> items = new ArrayList<>(); // 위시리시트 아이템 추가


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_create);

        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        ment = findViewById(R.id.ment);
        spinnerBtn = findViewById(R.id.spinnerBtn);
        iconSelect = findViewById(R.id.iconSelect);
        emojiEdit = findViewById(R.id.selectedEmoji);       // 이모지 버튼 클릭 후 키보드에서 이모지 입력 후 보이게 될 화면
        titleEdit = findViewById(R.id.titleEdit);
        memoEdit = findViewById(R.id.memoEdit);
        startDay = findViewById(R.id.startDay);
        endDay = findViewById(R.id.endDay);
        plusText = findViewById(R.id.plusText);
        spinner = findViewById(R.id.spinner);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        okBtn = findViewById(R.id.okBtn);

        // Intent로 전달된 데이터 받기
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        // 어떤 화면에서 넘어왔는지에 따라
        if ("WishExpectedAdapter".equals(source) || "WishCompletedAdapter".equals(source)) {
            String title = intent.getStringExtra("title");
            titleEdit.setText(title);
            hamBtn.setVisibility(View.VISIBLE);
            ment.setVisibility(View.GONE);
            iconSelect.setEnabled(false);
            emojiEdit.setEnabled(false);
            titleEdit.setEnabled(false);
            memoEdit.setEnabled(false);
            startDay.setEnabled(false);
            endDay.setEnabled(false);
            plusText.setEnabled(false);
            spinner.setEnabled(false);
            alarmSwitch.setEnabled(false);
            okBtn.setVisibility(View.GONE);

            //햄버거 버튼 설정
            hamBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // PopupMenu 생성
                    PopupMenu popupMenu = new PopupMenu(WishCreateActivity.this, hamBtn);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());

                    if ("WishCompletedAdapter".equals(source)) {
                        // WishCompletedAdapter에서 넘어온 경우, 수정 메뉴를 숨김
                        popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    }

                    // 메뉴 항목 클릭 리스너 설정
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int itemId = item.getItemId();
                            if (itemId == R.id.edit) { // 수정
                                iconSelect.setEnabled(true);
                                emojiEdit.setEnabled(false);
                                titleEdit.setEnabled(true);
                                memoEdit.setEnabled(true);
                                startDay.setEnabled(true);
                                endDay.setEnabled(true);
                                plusText.setEnabled(true);
                                spinner.setEnabled(true);
                                alarmSwitch.setEnabled(true);
                                okBtn.setVisibility(View.VISIBLE);
                                okBtn.setText("수정하기");
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
            //성취 예정일 채우기
            //카테고리 채우기
            //아이콘 채우기
            //메모 채우기
            //알림 채우기

        } else if ("Fragment2".equals(source)) {
            // Fragment2에서 넘어온 경우 - 작성 화면
            hamBtn.setVisibility(View.INVISIBLE);
            ment.setVisibility(View.VISIBLE);
            iconSelect.setEnabled(true);
            emojiEdit.setEnabled(true);
            titleEdit.setEnabled(true);
            memoEdit.setEnabled(true);
            startDay.setEnabled(true);
            endDay.setEnabled(true);
            plusText.setEnabled(true);
            spinner.setEnabled(true);
            alarmSwitch.setEnabled(true);
            okBtn.setVisibility(View.VISIBLE);
            okBtn.setText("등록하기");
        }

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

        //성취 예정일 스피너
        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(startDay);
            }
        });

        //성취 예정일 스피너
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(endDay);
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
                InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                manager.showSoftInput(emojiEdit, InputMethodManager.SHOW_IMPLICIT);
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

        //등록하기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 내용 저장
                //예정된 위시리스트 목록에 추가
                // 로그인한 사용자 정보 조회
                UserApiClient.getInstance().me((user, error) -> {
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error);
                    } else if (user != null) {
                        Long user_id = user.getId(); // 카카오 사용자 고유 ID
                        String title = titleEdit.getText().toString(); // wishlist 제목 입력한 내용
                        String memo = memoEdit.getText().toString(); // wishlist 제목 입력한 내용
                        String startday = startDay.getText().toString(); // wishlist 시작날짜 입력한 내용
                        String endday = endDay.getText().toString(); // wishlist 종료날짜 입력한 내용
                        String selectedItem = spinner.getSelectedItem().toString(); // 스피너에서 선택된 항목을 문자열로 가져오기
                        Integer spinnerValue = Integer.parseInt(selectedItem); // 문자열을 정수로 변환 // wishlist 제목 입력한 내용
                        String emoji = emojiEdit.getText().toString(); // wishlist 이모지 입력한 내용
                        Boolean alarmswitch = alarmSwitch.isChecked(); // wishlist 알람여부 선택한 내용
                        // POJO 클래스를 사용하여 방명록 데이터 생성
                        WishList wishList = new WishList(user_id, title, startday, endday, spinnerValue, emoji, alarmswitch, memo);

                        // 서버로 POST 요청 보내기
                        sendWishListData(wishList);
                    }
                    return null;
                });
            }
        });
    }

    private void sendWishListData(WishList wishList) {
        // 서버로 POST 요청 보내기
        WishListRepository wishListRepository = new WishListRepository();
        wishListRepository.sendWishListDataToServer(wishList, new WishListRepository.WishListCallback() {
            @Override
            public void onSuccess() {
                // 방명록 추가 성공
                Log.d("WishListCreateActivity", "위시리스트가 성공적으로 추가되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 방명록 추가 실패
                Log.e("WishListCreateActivity", "위시리스트 추가 실패: " + errorMessage);
            }
        });
    }

    private void sendGetWishListData(Long familyId) {

        WishListRepository wishListRepository = new WishListRepository();
        // 방명록 데이터 가져오기
        wishListRepository.getFamilyWishList(familyId, new WishListRepository.GetListCallback() {
            @Override
            public void onListGetSuccess(List<WishExpectedItem> wishExpectedItems) {
                WishCreateActivity.this.runOnUiThread(() -> {
                    // items 리스트에 서버에서 받아온 응답 데이터 추가
                    items.clear(); // 기존 데이터 초기화 (필요 시)

                    // 서버에서 받은 방명록 응답을 items에 추가
                    for (WishExpectedItem wishExpectedItem : wishExpectedItems) {
                        items.add(new WishExpectedItem(
                                wishExpectedItem.getEmoji(), // 이모지
                                wishExpectedItem.getTitle(), // 제목
                                wishExpectedItem.getDate() // 날짜
                        ));
                    }

                    // 어댑터에 변경 사항을 알림
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "방명록 조회 실패: " + errorMessage);
            }
        });
    }

    private void showDatePickerDialog(final TextView targetTextView) {
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

                        targetTextView.setText(formattedDate);
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
