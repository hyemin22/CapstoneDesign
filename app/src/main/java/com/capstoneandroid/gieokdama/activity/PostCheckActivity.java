package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.capstoneandroid.gieokdama.R;

public class PostCheckActivity extends BaseActivity {
    ImageButton backBtn;
    TextView to, message, date, from;
    Button okBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_check);

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

        // 이전 화면에서 받아오는 쪽지 정보들
        Intent intent = getIntent();
        String senderName = intent.getStringExtra("sender_name");
        String anonymousName = intent.getStringExtra("anonymous_name");
        String receiverName = intent.getStringExtra("receiver_name");
        String content = intent.getStringExtra("content");
        String createdAt = intent.getStringExtra("created_at");

        // 받는사람 설정
        to.setText(receiverName);

        // 메시지 설정
        message.setText(content);

        // 날짜 설정
        date.setText(createdAt);

        // 보낸사람 설정
        if (anonymousName != null) { //익명 닉네임이 따로 있다면
            from.setText(anonymousName);
            okBtn.setVisibility(View.INVISIBLE);
        } else {
            from.setText(senderName);
        }

        // 답장쓰러가기버튼 클릭 시 동작
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostCheckActivity.this, PostCreateActivity.class);
                intent.putExtra("sender_name", senderName);
                intent.putExtra("source", "PostCheckActivity");
                startActivity(intent);
            }
        });
    }
}
