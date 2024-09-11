package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuestBookCheckActivity extends AppCompatActivity {
    ImageButton backBtn;
    TextView name, content;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guestbook_check); // 액티비티의 레이아웃 파일 설정

        backBtn = findViewById(R.id.backBtn);
        name = findViewById(R.id.name);
        content = findViewById(R.id.content);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //사용자 이름 DB에서 가져와서 상단에 띄우기

        //작성된 내용 가져오기
        Intent outIntent = getIntent();
        String getContent = outIntent.getStringExtra("content");
        content.setText(getContent);
    }
}
