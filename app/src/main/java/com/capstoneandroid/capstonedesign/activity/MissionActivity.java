package com.capstoneandroid.capstonedesign.activity;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.adapter.MyMissionAdapter;
import com.capstoneandroid.capstonedesign.item.MyMissionItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.RecMissionAdapter;
import com.capstoneandroid.capstonedesign.item.RecMissionItem;
import com.capstoneandroid.capstonedesign.model.Mission;
import com.capstoneandroid.capstonedesign.model.User;
import com.capstoneandroid.capstonedesign.repository.MissionRepository;
import com.capstoneandroid.capstonedesign.repository.UserRepository;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MissionActivity extends BaseActivity {
    Long userId = UserInfoManager.getInstance().getUserId();
    ImageButton backBtn;
    TextView name, month, emptyTextView, emptyTextView2;
    Button addBtn, okBtn;
    RecyclerView mymissionView, recommendView;
    Dialog dialogCreate;
    private ArrayList<MyMissionItem> myMissionItems = new ArrayList<>();
    private ArrayList<RecMissionItem> recMissionItems = new ArrayList<>();
    private MyMissionAdapter adapter;
    private RecMissionAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daymission);

        backBtn = findViewById(R.id.backBtn);
        name = findViewById(R.id.name);
        month = findViewById(R.id.month);
        addBtn = findViewById(R.id.addBtn);
        okBtn = findViewById(R.id.okBtn);
        mymissionView = findViewById(R.id.myMissionView);
        recommendView = findViewById(R.id.recommendView);
        emptyTextView = findViewById(R.id.emptyTextView);
        emptyTextView2 = findViewById(R.id.emptyTextView2);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //사용자 이름 백엔드에서 가져와서 띄우기
        getUserInfoData();

        //월 띄우기
        Calendar calendar = Calendar.getInstance(); //현재 날짜 가져오기
        int thisMonth = calendar.get(Calendar.MONTH) + 1;
        month.setText(String.valueOf(thisMonth));

        // 모달창
        dialogCreate = new Dialog(MissionActivity.this);
        dialogCreate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCreate.setContentView(R.layout.activity_custom_dialog_create);

        //추가하기 버튼
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCreate();
            }
        });

        //미션 만들기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCreate();
            }
        });

        // 서버로 미션 get 요청 보내기
        sendGetMyMissionList();
        // 추천 미션 get 요청 보내기
        sendGetRecMissionList();

        //추가한 미션
        LinearLayoutManager linearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mymissionView.setLayoutManager(linearManager);
        adapter = new MyMissionAdapter(myMissionItems, this);

        mymissionView.setAdapter(adapter);

        //추천 미션
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        recommendView.setLayoutManager(gridManager);
        adapter2 = new RecMissionAdapter(recMissionItems, this);

        // RecMissionAdapter에 리스너 설정
        adapter2.setOnItemClickListener(new RecMissionAdapter.OnRecMissionItemClickListener() {
            @Override
            public void onAddMission(RecMissionItem item) {
                // RecMissionItem을 Mission으로 변환 후 추가
                Mission newMission = new Mission(
                        userId, item.getTitle(), item.getEmoji(),
                        null, null, null,
                        false, null);
                sendMissionData(newMission);
            }
        });

        recommendView.setAdapter(adapter2);
    }

    // 어댑터 데이터 확인 후 UI 업데이트
    void checkRecyclerViewData(RecyclerView view, TextView text) {
        if (view.getAdapter() != null && view.getAdapter().getItemCount() == 0) {
            // 아이템이 없을 때: 메시지 표시, RecyclerView 숨김
            text.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        } else {
            // 아이템이 있을 때: 메시지 숨김, RecyclerView 표시
            text.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void getUserInfoData() {
        // 서버로 Get 요청 보내기
        UserRepository userRepository = new UserRepository();
        userRepository.getUserInfo(userId, new UserRepository.GetInfoCallback() {
            @Override
            public void onInfoGetSuccess(User user) {
                name.setText(user.getNickname());
            }

            @Override
            public void onInfoGetFailure(String errorMessage) {
                Log.e("MissionActivity", "유저 이름 가져오기 실패: " + errorMessage);
            }
        });
    }

    private void sendGetMyMissionList() {
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.getMyMissionFromServer(userId, new MissionRepository.GetMyMissionListCallback() {
            @Override
            public void onSuccess(List<MyMissionItem> myMissionItems) {
                runOnUiThread(() -> {
                    MissionActivity.this.myMissionItems.clear();
                    for (MyMissionItem myMissionItem : myMissionItems) {
                        MissionActivity.this.myMissionItems.add(new MyMissionItem(
                                getApplicationContext(),
                                myMissionItem.getId(),
                                myMissionItem.getTitle(),
                                myMissionItem.getEmoji(),
                                myMissionItem.getCycle(),
                                myMissionItem.getRepeat_day(),
                                myMissionItem.getRepeat_time(),
                                myMissionItem.getNow_time(),
                                myMissionItem.getGoal_time(),
                                myMissionItem.getPercent(),
                                myMissionItem.getAlarm(),
                                myMissionItem.getAlarm_time()
                        ));
                    }
                    // 어댑터에 변경 사항을 알림
                    adapter.notifyDataSetChanged();

                    // 내 미션 리사이클러뷰 데이터 있는지 확인하고 없으면 빈 공간에 메시지 표시
                    checkRecyclerViewData(mymissionView, emptyTextView);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "내 미션 조회 실패: " + errorMessage);
            }
        });
    }

    private void sendGetRecMissionList() {
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.getRecMissionFromServer(userId, new MissionRepository.GetRecMissionListCallback() {
            @Override
            public void onSuccess(List<RecMissionItem> recMissionItems) {
                runOnUiThread(() -> {
                    MissionActivity.this.recMissionItems.clear();
                    for (RecMissionItem recMissionItem : recMissionItems) {
                        MissionActivity.this.recMissionItems.add(new RecMissionItem(
                                getApplicationContext(),
                                recMissionItem.getTitle(),
                                recMissionItem.getEmoji(),
                                recMissionItem.getDescription()
                        ));
                    }
                    // 어댑터에 변경 사항을 알림
                    adapter2.notifyDataSetChanged();

                    // 내 미션 리사이클러뷰 데이터 있는지 확인하고 없으면 빈 공간에 메시지 표시
                    checkRecyclerViewData(recommendView, emptyTextView2);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "추천 미션 조회 실패: " + errorMessage);
            }
        });
    }

    private void sendMissionData(Mission mission) {
        // 서버로 POST 요청 보내기
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.sendMissionToServer(mission, new MissionRepository.MissionCallback() {
            @Override
            public void onSuccess() {
                Log.d("MissionActivity", "추천 미션이 성공적으로 추가되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("MissionActivity", "추천 미션 추가 실패: " + errorMessage);
            }
        });
    }

    // 모달창 보이게 설정
    public void showDialogCreate(){
        dialogCreate.show();

        // askTextView의 텍스트를 변경
        TextView askTextView = dialogCreate.findViewById(R.id.askTextView);
        if (askTextView != null) {
            askTextView.setText("미션을 추가하시겠어요?");
        }

        // explainTextView의 텍스트를 변경
        TextView explainTextView = dialogCreate.findViewById(R.id.explainTextView);
        if (explainTextView != null) {
            explainTextView.setText("미션 수정 및 삭제는 언제든지 가능해요.");
        }

        Button noBtn = dialogCreate.findViewById(R.id.noButton);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreate.dismiss();
            }
        });

        dialogCreate.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MissionCreateActivity로 이동하는 Intent 생성
                Intent intent = new Intent(MissionActivity.this, MissionCreateActivity.class);
                intent.putExtra("source", "MissionActivity");
                view.getContext().startActivity(intent);

                // 현재
                if (view.getContext() instanceof Activity) {
                    ((Activity) view.getContext()).finish();
                }
            }
        });
    }
}