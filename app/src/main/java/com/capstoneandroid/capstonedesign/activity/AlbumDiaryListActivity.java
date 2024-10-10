package com.capstoneandroid.capstonedesign.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.item.DiaryListItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.DiaryListAdapter;

import java.util.Arrays;

public class AlbumDiaryListActivity extends AppCompatActivity {
    Button backBtn, editBtn;
    boolean isEditMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarylist);

        backBtn = findViewById(R.id.album_back);
        editBtn = findViewById(R.id.album_edit);

        RecyclerView recyclerView = findViewById(R.id.diarylistView);
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridManager);
        DiaryListAdapter adapter = new DiaryListAdapter();

        adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.image1), "협재바다에서 피크닉", "2024.05.09"));
        adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.image1, R.drawable.image2), "협재바다에서 피크닉", "2024.05.09"));
        adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.image1, R.drawable.image2, R.drawable.image3), "협재바다에서 피크닉", "2024.05.09"));
        adapter.addItem(new DiaryListItem(this, Arrays.asList(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4), "협재바다에서 피크닉", "2024.05.09"));

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
