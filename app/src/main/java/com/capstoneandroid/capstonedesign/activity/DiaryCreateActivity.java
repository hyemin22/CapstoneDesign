package com.capstoneandroid.capstonedesign.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Calendar;

public class DiaryCreateActivity extends AppCompatActivity {

    private EditText editTextDate, editPlace, content;
    private TextView albumPicker;
    private CardView card1, card2, card3, card4, card5;
    private ImageView img1, img2, img3, img4, img5;
    private static final int PICK_IMAGE = 100; //갤러리에서 이미지 선택할 때 사용하는 요청 코드
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_create);

        // XML에서 EditText 참조
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

        // EditText 클릭 시 DatePickerDialog 표시
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        // 이미지뷰 초기화
        ImageView[] imageViews = {img1, img2, img3, img4, img5};
        for (int i = 1; i < imageViews.length; i++) {
            imageViews[i].setVisibility(View.GONE);
            imageViews[i].setOnClickListener(v -> openGallery());
        }
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

    private void openGallery() {
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

                // 모든 이미지뷰를 초기화하고 숨기기
                for (ImageView imageView : imageViews) {
                    imageView.setVisibility(View.GONE);
                    imageView.setImageDrawable(null); // 기존 이미지 제거
                }

                if (data.getClipData() != null) { // 다중 선택된 경우
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count && i < imageViews.length; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageViews[i].setImageURI(imageUri);
                        imageViews[i].setVisibility(View.VISIBLE);
                    }
                    // 다음 이미지뷰도 보이도록 설정 (입력된 이미지보다 하나 더)
                    if (count < imageViews.length) {
                        imageViews[count].setVisibility(View.VISIBLE);
                    }
                } else if (data.getData() != null) { // 단일 선택된 경우
                    Uri imageUri = data.getData();
                    imageViews[0].setImageURI(imageUri);
                    imageViews[0].setVisibility(View.VISIBLE);
                    imageViews[1].setVisibility(View.VISIBLE);
                }
            }
        }

//        if (resultCode == RESULT_OK && data != null) {
//            if (requestCode == PICK_IMAGE){ //이미지뷰에 이미지 설정
//                imgUri = data.getData();
//                img1.setImageURI(imgUri);
//            }
//        }
    }
}