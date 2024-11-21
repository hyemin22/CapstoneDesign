package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.capstoneandroid.capstonedesign.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryActivity extends BaseActivity {
    TextView albumname, nickname, date, place, title, content;
    CircleImageView profile;
    CardView card1, card2, card3, card4, card5;
    ImageView backBtn, img1, img2, img3, img4, img5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        // 위젯 초기화
        backBtn = findViewById(R.id.backBtn);
        albumname = findViewById(R.id.albumname);
        nickname = findViewById(R.id.nickname);
        date = findViewById(R.id.date);
        place = findViewById(R.id.place);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        profile = findViewById(R.id.profile);
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

        // backBtn 클릭 이벤트
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 어느 화면에서 넘어오는지 확인
        // !!!나중에 수정!!!
        Intent intent = getIntent();
        String sourceName = intent.getStringExtra("source");
        if (sourceName.equals("photo1")) {
            albumname.setText("우리 가족 나들이");
            nickname.setText("가족대표");
            profile.setImageResource(R.drawable.ch_apple);
            date.setText("2024.11.20(수)");
            place.setText("뚝섬 한강공원");
            title.setText("한강에서 피크닉🧺");
            content.setText("돗자리랑 텐트 빌려서 명당에 자리 잡고 치킨이랑 피자 이것저것 시켜 먹다보니까 시간 가는 줄 몰랐다😋 " +
                    "우리 다음은 반포 한강 공원 가자!! ");
            img1.setImageResource(R.drawable.picnic1);
            img2.setImageResource(R.drawable.picnic2);
        } else if (sourceName.equals("jeju")){
            date.setText("2023.05.20(월)");
            img1.setImageResource(R.drawable.diaryimg2);
            img2.setImageResource(R.drawable.diaryimg1);
        }
    }
}
