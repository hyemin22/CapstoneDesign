package com.capstoneandroid.capstonedesign.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.model.WishList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryCreateActivity extends AppCompatActivity {

    private EditText editTextDate, editPlace, content;
    private TextView backBtn;
    private Button okBtn;
    private Spinner albumPicker;
    ArrayAdapter<String> adapter;
    private CardView card1, card2, card3, card4, card5;
    private ImageView img1, img2, img3, img4, img5;
    private static final int PICK_IMAGE = 100; //갤러리에서 이미지 선택할 때 사용하는 요청 코드
    private int currentImageIndex = 0; // 현재 이미지가 추가될 이미지뷰의 인덱스
    private static final int MAX_IMAGES = 5; // 이미지뷰 최대 개수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_create);

        // XML에서 EditText 참조
        backBtn = findViewById(R.id.backBtn);
        okBtn = findViewById(R.id.okBtn);
        editTextDate = findViewById(R.id.editTextDate);
        editPlace = findViewById(R.id.editPlace);
        albumPicker = findViewById(R.id.albumPicker);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        content = findViewById(R.id.content);

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        albumPicker.setAdapter(adapter);

        // DB에서 앨범 리스트 가져오는 요청 보내기
        fetchAlbumListFromDB();

        // 취소(뒤로가기) 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // 이전 화면으로 이동
            }
        });

        // 저장하기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DB에 새로운 일기 저장
//                String title = titleEdit.getText().toString(); // 제목
//                String startday = startDay.getText().toString(); // 시작날짜
//                String endday = endDay.getText().toString(); // 종료날짜
//                String emoji = emojiSelect.getText().toString(); // 이모지
//                Boolean alarm = alarmSwitch.isChecked(); // 알람여부
//                String memo = memoEdit.getText().toString(); // 메모
//
//                // POJO 클래스를 사용하여 위시 데이터 생성
//                WishList wishList = new WishList(userId, title, startday, endday, categoryId, emoji, alarm, memo, false);
//
//                // 서버로 POST 요청 보내기
//                sendWishListData(wishList);
                finish();
            }
        });

        // EditText 클릭 시 DatePickerDialog 표시
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        // 이미지뷰 초기화
        ImageView[] imageViews = {img1, img2, img3, img4, img5};
        for (int i = 1; i < imageViews.length; i++) {
            imageViews[i].setVisibility(View.GONE);
        }

        // 모든 이미지뷰에 클릭 리스너 설정
        for (int i = 0; i < imageViews.length; i++) {
            final int index = i; // 클릭한 이미지뷰의 인덱스를 기억하도록 final 변수 사용
            imageViews[i].setOnClickListener(v -> {
                openGalleryForImageView(index); // 클릭된 이미지뷰 인덱스 전달
            });
        }
    }

    private void fetchAlbumListFromDB() {
        //앨범 리스트 GET 요청
        List<String> albumList = new ArrayList<>();
        albumList.add("앨범 · 선택");
        albumList.add("우리가족 나들이");
        albumList.add("2023 제주여행");
        albumList.add("2022 하와이");
        albumList.add("첫째딸 생일");

        adapter.clear();
        adapter.addAll(albumList);
        adapter.notifyDataSetChanged();

        albumPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 앨범 아이디 저장
                // albumId = albumInfos.get(position - 1).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                        editTextDate.setText(formattedDate);
                    }
                },
                year, month, day);

        // 다이얼로그를 스피너 모드로 설정
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

    private void openGalleryForImageView(int imageViewIndex) {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        try {
            startActivityForResult(gallery, PICK_IMAGE);
        } catch (SecurityException e) {
            // 보안 예외가 발생한 경우 사용자에게 메시지를 표시하여 앱이 외부 저장소에 접근할 수 없음을 알립니다.
            Toast.makeText(this, "갤러리에 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //다른 페이지에서 받은 값 처리
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                // 선택한 이미지를 담을 리스트
                ImageView[] imageViews = {img1, img2, img3, img4, img5};

                if (data.getClipData() != null) { // 다중 선택된 경우
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count && i < imageViews.length; i++) {
                        if (currentImageIndex >= MAX_IMAGES) break; // 최대 이미지 개수 초과 시 중단
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageViews[currentImageIndex].setImageURI(imageUri);
                        imageViews[currentImageIndex].setVisibility(View.VISIBLE);
                        currentImageIndex++; // 다음 이미지뷰로 이동
                    }
                } else if (data.getData() != null) { // 단일 선택된 경우
                    if (currentImageIndex < MAX_IMAGES) {
                        Uri imageUri = data.getData();
                        imageViews[currentImageIndex].setImageURI(imageUri);
                        imageViews[currentImageIndex].setVisibility(View.VISIBLE);
                        currentImageIndex++; // 다음 이미지뷰로 이동
                    }
                }
                // 다음 빈 이미지뷰를 보이도록 설정
                if (currentImageIndex < MAX_IMAGES) {
                    imageViews[currentImageIndex].setVisibility(View.VISIBLE);
                }
            }
        }
    }
}