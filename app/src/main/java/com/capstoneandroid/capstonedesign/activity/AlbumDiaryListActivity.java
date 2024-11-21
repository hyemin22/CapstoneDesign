package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.item.DiaryListItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.DiaryListAdapter;

import java.util.Arrays;

public class AlbumDiaryListActivity extends BaseActivity {
    Button backBtn, editBtn;
    TextView albumname;
    boolean isEditMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarylist);

        backBtn = findViewById(R.id.album_back);
        editBtn = findViewById(R.id.album_edit);
        albumname = findViewById(R.id.albumname);

        RecyclerView recyclerView = findViewById(R.id.diarylistView);
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridManager);
        DiaryListAdapter adapter = new DiaryListAdapter();

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        if(source.equals("제주")) {
            adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.diaryimg2), "협재바다에서 피크닉", "2023.05.20"));
            adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.i1, R.drawable.i2, R.drawable.i3), "오늘의 제주 먹부림!", "2023.05.21"));
            adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.i4), "아빠 생신파티", "2023.05.21"));
            adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.i5, R.drawable.i6, R.drawable.i7, R.drawable.i8), "제주 마지막 날", "2023.05.23"));
        } else if (source.equals("서울여대")) {
            albumname.setText("  서울여대 구경");
            adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.k1, R.drawable.k2, R.drawable.k3, R.drawable.k4), "서울여대에서 단풍 구경한 날", "2024.11.21"));
        }
        recyclerView.setAdapter(adapter);

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
                adapter.setEditMode(isEditMode); // Adapter에 모드 전달
                if (isEditMode) {
                    editBtn.setText("완료"); // 버튼 텍스트 변경
                } else {
                    editBtn.setText("편집"); // 버튼 텍스트 원래대로
                }
            }
        });
    }
}
