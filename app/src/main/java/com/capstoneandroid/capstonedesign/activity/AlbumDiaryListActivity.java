package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.DiaryListAdapter;
import com.capstoneandroid.capstonedesign.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;

public class AlbumDiaryListActivity extends BaseActivity {
    Long userId = UserInfoManager.getInstance().getUserId();
    Long albumId; // 현재 선택된 앨범 아이디
    Button backBtn, editBtn;
    TextView albumname;
    boolean isEditMode = false;
    private DiaryListAdapter adapter;
    private ArrayList<DiaryListItem> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarylist);

        backBtn = findViewById(R.id.album_back);
        editBtn = findViewById(R.id.album_edit);
        albumname = findViewById(R.id.albumname);

        Intent intent = getIntent();
        albumId = intent.getLongExtra("albumId", -1);
        albumname.setText(intent.getStringExtra("albumname"));

        // 서버로 일기 get 요청 보내기
        sendGetDiaryInAlbum();

        RecyclerView recyclerView = findViewById(R.id.diarylistView);
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridManager);
        adapter = new DiaryListAdapter(items, this);

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

    private void sendGetDiaryInAlbum() {
        DiaryRepository diaryRepository = new DiaryRepository();
        diaryRepository.getDiaryInAlbum(userId, albumId, new DiaryRepository.GetDiaryListCallback() {
            @Override
            public void onSuccess(List<DiaryListItem> diaries) {
                runOnUiThread(() -> {
                    items.clear();
                    for (DiaryListItem diary : diaries) {
                        items.add(new DiaryListItem(
                                getApplicationContext(),
                                diary.getId(),
                                diary.getTitle(),
                                diary.getDiary_date(),
                                diary.getPhoto1(),
                                diary.getPhoto2(),
                                diary.getPhoto3(),
                                diary.getPhoto4(),
                                diary.getPhoto5(),
                                diary.getPhoto6(),
                                diary.getPhoto7(),
                                diary.getPhoto8(),
                                diary.getPhoto9(),
                                diary.getPhoto10(),
                                diary.getContent(),
                                diary.getAddress(),
                                diary.getLatitude(),
                                diary.getLongitude(),
                                diary.getAlbum_title(),
                                diary.getUser_character(),
                                diary.getUser_nickname()
                        ));
                    }

                    // 어댑터에 변경 사항을 알림
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "앨범별 일기 조회 실패: " + errorMessage);
            }
        });
    }
}
