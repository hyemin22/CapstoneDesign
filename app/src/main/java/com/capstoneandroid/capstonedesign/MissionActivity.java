package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class MissionActivity extends AppCompatActivity {
    ImageButton backBtn;
    TextView name, month;
    Button addBtn, okBtn;
    RecyclerView daymissionView, recommendView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daymission);

        backBtn = findViewById(R.id.backBtn);
        name = findViewById(R.id.name);
        month = findViewById(R.id.month);
        addBtn = findViewById(R.id.addBtn);
        okBtn = findViewById(R.id.okBtn);
        daymissionView = findViewById(R.id.daymissionView);
        recommendView = findViewById(R.id.recommendView);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //사용자 이름 백엔드에서 가져와서 띄우기

        //월 띄우기
        Calendar calendar = Calendar.getInstance(); //현재 날짜 가져오기
        int thisMonth = calendar.get(Calendar.MONTH) + 1;
        month.setText(String.valueOf(thisMonth));

        //추가하기 버튼
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissionActivity.this, MissionCreateActivity.class);
                intent.putExtra("source", "MissionActivity");
                startActivity(intent);
            }
        });

        //미션 만들기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MissionActivity.this, MissionCreateActivity.class);
                intent.putExtra("source", "MissionActivity");
                startActivity(intent);
            }
        });

        //추가한 미션
        LinearLayoutManager linearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        daymissionView.setLayoutManager(linearManager);
        MyMissionAdapter adapter = new MyMissionAdapter(this);

        adapter.addItem(new MyMissionItem("🚶🏻‍♀","가족과 산책하기","주간 목표", "50", "2","4"));
        adapter.addItem(new MyMissionItem("🖐🏻","굿나잇 인사하기","일일 목표", "100", "7","7"));

        daymissionView.setAdapter(adapter);

        //추천 미션
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        recommendView.setLayoutManager(gridManager);
        RecMissionAdapter adapter2 = new RecMissionAdapter();

        adapter2.addItem(new RecMissionItem("🍳", "요리해주기", "가족을 위해 한끼 식사 만들어볼까요?"));
        adapter2.addItem(new RecMissionItem("🍳", "요리해주기", "가족을 위해 한끼 식사 만들어볼까요?"));
        adapter2.addItem(new RecMissionItem("☎️", "굿모닝 인사하기", "하루 시작 잊지 말고 연락해보아요!"));
        adapter2.addItem(new RecMissionItem("☎️", "굿모닝 인사하기", "하루 시작 잊지 말고 연락해보아요!"));

        // RecMissionAdapter에 리스너 설정
        adapter2.setOnItemClickListener(new RecMissionAdapter.OnRecMissionItemClickListener() {
            @Override
            public void onAddMission(RecMissionItem item) {
                // RecMissionItem을 MyMissionItem으로 변환 후 추가
                MyMissionItem newMission = new MyMissionItem(item.getIcon(), item.getTitle(), "주기 미정", "0", "0", "0");
                adapter.addItem(newMission);
                adapter.notifyDataSetChanged();
            }
        });

        recommendView.setAdapter(adapter2);
    }
}