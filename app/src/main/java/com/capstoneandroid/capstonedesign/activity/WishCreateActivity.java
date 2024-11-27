package com.capstoneandroid.capstonedesign.activity;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.capstoneandroid.capstonedesign.EmojiFilter;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.fragment.WishExpectedFragment;
import com.capstoneandroid.capstonedesign.fragment.WishlistCompleteFragment;
import com.capstoneandroid.capstonedesign.item.WishCategoryItem;
import com.capstoneandroid.capstonedesign.item.WishListItem;
import com.capstoneandroid.capstonedesign.model.WishList;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WishCreateActivity extends BaseActivity {
    WishListRepository wishListRepository = new WishListRepository();
    ImageButton backBtn, hamBtn, spinnerBtn;
    EditText titleEdit, memoEdit, emojiSelect;
    TextView ment, startDay, endDay, plusText;
    Spinner spinner;
    Switch alarmSwitch;
    Button okBtn;
    ArrayAdapter<String> adapter;
    Integer categoryId = -1;
    Long userId = UserInfoManager.getInstance().getUserId();
    Long idNum; //사용자 아이디, 위시리스트 아이디

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_create);

        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        ment = findViewById(R.id.ment);
        spinnerBtn = findViewById(R.id.spinnerBtn);
        emojiSelect = findViewById(R.id.emojiSelect);
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
            hamBtn.setVisibility(View.VISIBLE);
            ment.setVisibility(View.GONE);
            emojiSelect.setEnabled(false);
            titleEdit.setEnabled(false);
            memoEdit.setEnabled(false);
            startDay.setEnabled(false);
            endDay.setEnabled(false);
            plusText.setEnabled(false);
            spinner.setEnabled(false);
            alarmSwitch.setEnabled(false);
            okBtn.setVisibility(View.GONE);

            //정보 채우기
            idNum = intent.getLongExtra("id", -1L);
            String titleText = intent.getStringExtra("title");
            String startDateText = intent.getStringExtra("start_date");
            String endDateText = intent.getStringExtra("end_date");
            categoryId = intent.getIntExtra("category", -1); //카테고리 스피너 초기화 (setCategorySelection)
            String iconText = intent.getStringExtra("icon");
            String memoText = intent.getStringExtra("memo");
            boolean alarm = intent.getBooleanExtra("alarm", false);

            titleEdit.setText(titleText); //제목 채우기
            startDay.setText(startDateText); //성취 예정일 채우기
            endDay.setText(endDateText);
            emojiSelect.setText(iconText); //아이콘 채우기
            emojiSelect.setBackground(null);
            memoEdit.setText(memoText); //메모 채우기
            alarmSwitch.setChecked(alarm); //알림 채우기

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
                                emojiSelect.setEnabled(true);
                                titleEdit.setEnabled(true);
                                memoEdit.setEnabled(true);
                                startDay.setEnabled(true);
                                endDay.setEnabled(true);
                                plusText.setEnabled(true);
                                spinner.setEnabled(true);
                                alarmSwitch.setEnabled(true);
                                okBtn.setVisibility(View.VISIBLE);
                                okBtn.setText("수정하기");

                                // 수정하기 버튼
                                okBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // 예정된 위시리스트 목록에 추가
                                        String title = titleEdit.getText().toString(); // wishlist 제목
                                        String startday = startDay.getText().toString(); // wishlist 시작날짜
                                        String endday = endDay.getText().toString(); // wishlist 종료날짜
                                        String emoji = emojiSelect.getText().toString(); // wishlist 이모지
                                        String memo = memoEdit.getText().toString(); // wishlist 메모
                                        boolean alarmswitch = alarmSwitch.isChecked(); // wishlist 알람여부

                                        // POJO 클래스를 사용하여 위시 데이터 생성
                                        WishListItem wishListItem = new WishListItem(idNum, title, startday, endday, emoji, memo, categoryId, alarmswitch);

                                        // 서버로 PUT 요청 보내기
                                        updateWishListData(wishListItem);
                                    }
                                });

                                return true;
                            } else if (itemId == R.id.delete) { // 삭제
                                // DB에서 현재 위시 삭제
                                deleteWishData();

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

        } else if ("Fragment2".equals(source)) {
            // Fragment2에서 넘어온 경우 - 작성 화면
            hamBtn.setVisibility(View.INVISIBLE);
            ment.setVisibility(View.VISIBLE);
            emojiSelect.setEnabled(true);
            titleEdit.setEnabled(true);
            memoEdit.setEnabled(true);
            startDay.setEnabled(true);
            endDay.setEnabled(true);
            plusText.setEnabled(true);
            spinner.setEnabled(true);
            alarmSwitch.setEnabled(true);
            okBtn.setVisibility(View.VISIBLE);
            okBtn.setText("등록하기");

            //등록하기 버튼
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // DB에 새로운 위시 저장
                    String title = titleEdit.getText().toString(); // 제목
                    String startday = startDay.getText().toString(); // 시작날짜
                    String endday = endDay.getText().toString(); // 종료날짜
                    String emoji = emojiSelect.getText().toString(); // 이모지
                    Boolean alarm = alarmSwitch.isChecked(); // 알람여부
                    String memo = memoEdit.getText().toString(); // 메모

                    // POJO 클래스를 사용하여 위시 데이터 생성
                    WishList wishList = new WishList(userId, title, startday, endday, categoryId, emoji, alarm, memo, false);

                    // 서버로 POST 요청 보내기
                    sendWishListData(wishList);

                    // WishlistCompleteFragment 생성
                    WishlistCompleteFragment fragment = new WishlistCompleteFragment();

                    // Activity의 루트 뷰를 숨기기
                    View mainView = findViewById(R.id.main);
                    mainView.setVisibility(View.INVISIBLE); // 루트 뷰를 INVISIBLE 상태로 설정

                    // 프래그먼트를 현재 Activity 화면에 표시
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, fragment) // 전체 화면을 프래그먼트로 교체
                            .commit();
                }
            });
        }

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        // DB에서 카테고리 데이터 가져오는 요청 보내기
        fetchCategoryDataFromDB();

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
                Intent intent = new Intent(WishCreateActivity.this, WishCategoryCreateActivity.class);
                startActivity(intent);
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
    }

    private void sendWishListData(WishList wishList) {
        // 서버로 POST 요청 보내기
        wishListRepository.sendWishListDataToServer(wishList, new WishListRepository.WishListCallback() {
            @Override
            public void onSuccess() {
                // 위시리스트 추가 성공
                Log.d("WishListCreateActivity", "위시리스트가 성공적으로 추가되었습니다");
            }
            @Override
            public void onFailure(String errorMessage) {
                // 위시리스트 추가 실패
                Log.e("WishListCreateActivity", "위시리스트 추가 실패: " + errorMessage);
            }
        });
    }

    // 위시리스트 내용 수정
    private void updateWishListData(WishListItem wishList) {
        // 서버로 PUT 요청 보내기
        wishListRepository.sendWishListUpdateToServer(wishList, new WishListRepository.WishListCallback() {
            @Override
            public void onSuccess() {
                // 위시리스트 추가 성공
                Log.d("WishListCreateActivity", "위시리스트가 성공적으로 수정되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 위시리스트 추가 실패
                Log.e("WishListCreateActivity", "위시리스트 수정 실패: " + errorMessage);
            }
        });
    }

    private void deleteWishData() {
        wishListRepository.deleteWishDataToServer(idNum, new WishListRepository.WishListCallback() {
            @Override
            public void onSuccess() {
                Log.d("WishListCreateActivity", "위시리스트가 성공적으로 삭제되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("WishListCreateActivity", "위시리스트 삭제 실패: " + errorMessage);
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

    // DB에서 카테고리 데이터 가져오는 요청 보내기
    private void fetchCategoryDataFromDB() {
        sendGetWishListCategory(); // 카테고리 요청
    }

    // 위시리스트 카테고리 이름 리스트 가져와서 스피너에 넣기
    private void sendGetWishListCategory() {
        wishListRepository.getWishListByCategory(userId, new WishListRepository.GetCategoryListCallback() {
            @Override
            public void onListGetSuccess(List<WishCategoryItem> wishCategories) {
                runOnUiThread(() -> {
                    // 서버에서 받은 위시리스트 카테고리 응답을 기반으로 스피너에 리스트 넣기
                    List<String> category = new ArrayList<>();

                    // 첫 번째 항목으로 "카테고리를 선택해주세요" 추가
                    category.add("카테고리를 선택해주세요");

                    // 카테고리 아이템을 리스트에 추가
                    for (WishCategoryItem wishCategory : wishCategories) {
                        category.add(wishCategory.getName());
                    }

                    // 스피너 어댑터에 카테고리 이름 리스트 추가
                    adapter.clear();
                    adapter.addAll(category);
                    adapter.notifyDataSetChanged();

                    // 카테고리 아이디를 기반으로 초기 선택값 설정
                    setCategorySelection(wishCategories, categoryId);

                    //카테고리 스피너
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // 선택된 카테고리의 ID를 저장
                            if (position != 0) {
                                // 카테고리 아이템을 리스트에서 가져와서 처리
                                categoryId = wishCategories.get(position - 1).getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // 선택되지 않은 상태에 대한 처리
                        }
                    });
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "위시 카테고리 리스트 조회 실패: " + errorMessage);
            }
        });
    }

    private void setCategorySelection(List<WishCategoryItem> wishCategories, int selectedCategoryId) {
        // 선택할 스피너 위치를 위한 변수
        int position = 0;

        // categoryId가 -1이 아니면 for문 실행
        if (selectedCategoryId != -1) {
            for (int i = 0; i < wishCategories.size(); i++) {
                if (wishCategories.get(i).getId() == selectedCategoryId) {
                    position = i + 1; // 첫 번째 항목이 "카테고리를 선택해주세요"이므로 +1
                    break;
                }
            }
        }

        // 스피너에서 해당 위치 선택
        spinner.setSelection(position);
    }
}