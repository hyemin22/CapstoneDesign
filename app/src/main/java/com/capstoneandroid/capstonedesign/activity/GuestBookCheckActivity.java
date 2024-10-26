package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.capstoneandroid.capstonedesign.R;

public class GuestBookCheckActivity extends BaseActivity {
    ImageButton backBtn, hamBtn;
    TextView name, content;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_check); // 액티비티의 레이아웃 파일 설정

        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        name = findViewById(R.id.name);
        content = findViewById(R.id.content);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //햄버거버튼(수정및삭제)
        hamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PopupMenu 생성
                PopupMenu popupMenu = new PopupMenu(GuestBookCheckActivity.this, hamBtn);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());

                // 메뉴 항목 클릭 리스너 설정
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.edit) { // 수정
                            String write = content.getText().toString();
                            Intent intent = new Intent(GuestBookCheckActivity.this, GuestBookCreateActivity.class);
                            intent.putExtra("content", write);
                            intent.putExtra("source_activity", "GuestCheckActivity"); //액티비티 구분 위한 식별자
                            startActivity(intent);
                            finish();
                            return true;
                        } else if (itemId == R.id.delete) { // 삭제
                            // 삭제할 아이템의 인덱스를 intent로 넘김
                            Intent resultIntent = new Intent("DELETE_GUESTBOOK_ITEM");
                            resultIntent.putExtra("delete_position", getIntent().getIntExtra("position", -1));
                            LocalBroadcastManager.getInstance(GuestBookCheckActivity.this).sendBroadcast(resultIntent);
                            finish(); // 현재 액티비티 종료
                            return true;
                        }
                        return false;
                    }
                });

                // 팝업 메뉴 보여주기
                popupMenu.show();
            }
        });

        //사용자 이름 DB에서 가져와서 상단에 띄우기

        //작성된 내용 가져오기
        Intent outIntent = getIntent();
        String getContent = outIntent.getStringExtra("content");
        content.setText(getContent);
    }
}
