package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlbumDiaryList extends AppCompatActivity {
    Button backBtn, editBtn;
    ImageView listimg;
    boolean isEditMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        backBtn = findViewById(R.id.album_back);
        editBtn = findViewById(R.id.album_edit);
        listimg = findViewById(R.id.listimg);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // 이전 화면으로 이동
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditMode = !isEditMode; // 상태 변경
                if (isEditMode) {
                    listimg.setImageResource(R.drawable.img_list4edit);
                    editBtn.setText("완료"); // 버튼 텍스트 변경
                } else {
                    listimg.setImageResource(R.drawable.img_list4); // 원래 이미지 리소스로 변경
                    editBtn.setText("편집"); // 버튼 텍스트 원래대로
                }
            }
        });

        listimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //특정추억일기화면으로 이동
                Intent intent = new Intent(AlbumDiaryList.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
