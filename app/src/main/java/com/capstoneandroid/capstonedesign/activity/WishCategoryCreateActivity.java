package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.capstoneandroid.capstonedesign.R;

public class WishCategoryCreateActivity extends AppCompatActivity {

    ImageButton backBtn;
    EditText titleEdit;
    Button okBtn;
    EditText selectedEmoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wish_category_create);

        backBtn = findViewById(R.id.backBtn);
        titleEdit = findViewById(R.id.titleEdit);
        selectedEmoji = findViewById(R.id.selectedEmoji);
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
                String title = titleEdit.getText().toString();
                String emoji = selectedEmoji.getText().toString();
                // 앨범명, 선택된 이모지와 함께 다음 화면으로 이동
                Intent resultIntent = new Intent();
                resultIntent.putExtra("categoryTitle", title);
                resultIntent.putExtra("categoryEmoji", emoji);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}