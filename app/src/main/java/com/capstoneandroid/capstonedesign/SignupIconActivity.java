package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupIconActivity extends AppCompatActivity {

    private GridLayout gridIcon;
    private Button okBtn;
    private ImageButton selectedIconButton;
    private TextView mainText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_icon);

        gridIcon = findViewById(R.id.gridIcon);
        okBtn = findViewById(R.id.okBtn2);
        mainText = findViewById(R.id.maintext);

        //앞 화면에서 보낸 이름 값 받아서 화면 상단에 출력
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mainText.setText("안녕하세요 " + name + "님!\n" + name + "님의 아이콘을 골라주세요.");

        okBtn.setEnabled(false);
        okBtn.setAlpha(0.5f);

        for(int i=0; i<gridIcon.getChildCount();i++){
            ImageButton iconButton = (ImageButton) gridIcon.getChildAt(i);
            iconButton.setAlpha(0.5f);
            iconButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton newSelectedIconButton = (ImageButton) view;

                    if (selectedIconButton != null && selectedIconButton != newSelectedIconButton) {
                        selectedIconButton.setAlpha(0.5f); // 이전에 선택된 아이콘을 반투명하게 설정
                    }

                    newSelectedIconButton.setAlpha(1.0f); // 현재 선택된 아이콘을 불투명하게 설정
                    selectedIconButton = newSelectedIconButton; // 선택된 아이콘 업데이트

                    okBtn.setEnabled(true);
                    okBtn.setAlpha(1.0f);
                }
            });
        }

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택된 아이콘과 함께 다음 화면으로 이동
                Intent intent = new Intent(SignupIconActivity.this, SignupTermsActivity.class);
                intent.putExtra("selectedIcon", (Integer)selectedIconButton.getTag());
                startActivity(intent);
            }
        });
    }
}
