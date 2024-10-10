package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstoneandroid.capstonedesign.R;

public class AlbumCreateActivity extends AppCompatActivity{

    ImageButton backBtn;
    EditText titleEdit;
    Button okBtn;
    Button[] colorPicks = new Button[5]; //5개 컬러 선택 버튼 배열
    Integer[] colorBtnIds = {R.id.yellow, R.id.red, R.id.blue, R.id.purple, R.id.white}; //5개 컬러 버튼 id값 배열
    private Button selectedColorButton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_create); // 액티비티의 레이아웃 파일 설정

        backBtn = findViewById(R.id.backBtn);
        titleEdit = findViewById(R.id.titleEdit);
        okBtn = findViewById(R.id.okBtn);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //컬러버튼 5개 대입
        for(int i=0; i<colorBtnIds.length; i++){
            colorPicks[i] = findViewById(colorBtnIds[i]); //각 버튼에 아이디 대입
        }

        // 태그 값 설정 (예: RGB 값이나 리소스 ID를 사용할 수 있습니다)
        colorPicks[0].setTag(R.color.album_yellow);  // 노란색 버튼에 태그 설정
        colorPicks[1].setTag(R.color.album_red);     // 빨간색 버튼에 태그 설정
        colorPicks[2].setTag(R.color.album_blue);    // 파란색 버튼에 태그 설정
        colorPicks[3].setTag(R.color.album_purple);  // 보라색 버튼에 태그 설정
        colorPicks[4].setTag(R.color.album_white);   // 흰색 버튼에 태그 설정

        //컬러버튼에 클릭 이벤트 처리
        for(int i=0; i<colorBtnIds.length; i++){
            final int index = i;
            colorPicks[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedColorButton != null) {
                        selectedColorButton.setSelected(false);
                    }
                    selectedColorButton = colorPicks[index];
                    selectedColorButton.setSelected(true);
                }
            });
        }

        //확인버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdit.getText().toString();
                // 앨범명, 선택된 색상과 함께 다음 화면으로 이동
                Intent resultIntent = new Intent();
                resultIntent.putExtra("albumTitle", title);
                if(selectedColorButton != null) {
                    resultIntent.putExtra("selectedColor", (Integer)selectedColorButton.getTag());
                }
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
