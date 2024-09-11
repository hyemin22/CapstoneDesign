package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuestWriteActivity extends AppCompatActivity {
    ImageButton backBtn;
    EditText contentEdit;
    Button okBtn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guestbook_create);

        backBtn = findViewById(R.id.backBtn);
        contentEdit = findViewById(R.id.contentEdit);
        okBtn = findViewById(R.id.okBtn);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //확인버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = contentEdit.getText().toString();
                // 앨범명, 선택된 색상과 함께 다음 화면으로 이동
                Intent intent = new Intent(GuestWriteActivity.this, GuestBookCheckActivity.class);
                intent.putExtra("content", content);
                intent.putExtra("source_activity", "GuestWriteActivity"); //액티비티 구분 위한 식별자
                startActivity(intent);
            }
        });
    }
}
