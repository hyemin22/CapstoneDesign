package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostCheckActivity extends AppCompatActivity {
    ImageButton backBtn;
    TextView to, message, date, from;
    Button okBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_check);

        backBtn = findViewById(R.id.backBtn);
        to = findViewById(R.id.to);
        message = findViewById(R.id.message);
        date = findViewById(R.id.date);
        from = findViewById(R.id.from);
        okBtn = findViewById(R.id.okBtn);

        // 이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 받는사람 설정

        // 메시지 설정

        // 날짜 설정

        // 보낸사람 설정

        // 답장쓰러가기버튼 클릭 시 동작
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostCheckActivity.this, PostWriteActivity.class);
                intent.putExtra("source_activity", "PostCheckActivity");
                startActivity(intent);
            }
        });
    }
}
