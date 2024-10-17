package com.capstoneandroid.capstonedesign.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.item.ActivityItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.ActivityAdapter;

public class ActivityFragment extends Fragment {
    private ActivityAdapter adapter;
    ArrayAdapter<String> adapter1, adapter2;
    private TextView ment, region1, region2;
    private String activityType; // 탭 타입 저장

    public ActivityFragment(String activityType) {
        this.activityType = activityType;
    }

    public ActivityFragment(ActivityAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_activity, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        region1 = rootView.findViewById(R.id.spinner1);
        region2 = rootView.findViewById(R.id.spinner2);
        ment = rootView.findViewById(R.id.ment);

        //지역 선택
        region1.setOnClickListener(v -> {
            // 다이얼로그를 띄울 때 사용할 커스텀 레이아웃을 설정
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_region1, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);

            // 다이얼로그 생성
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();

            // 다이얼로그 크기 및 위치 설정
            dialog.getWindow().setLayout(970, 600); // 원하는 크기로 설정 (px 단위)
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.x = 0;  // x 위치 조정
            params.y = -70;  // y 위치 조정
            dialog.getWindow().setAttributes(params);

            GridLayout gridLayout = dialogView.findViewById(R.id.gridLayout); // GridLayout 가져오기
            int childCount = gridLayout.getChildCount(); // GridLayout의 자식 수

            for (int i = 0; i < childCount; i++) {
                View child = gridLayout.getChildAt(i);

                Button button = (Button) child;
                
                // 클릭 리스너 설정
                button.setOnClickListener(view -> {
                    // 선택한 버튼의 텍스트를 spinnerButton에 설정
                    region1.setText(button.getText().toString());
                    dialog.dismiss(); // 다이얼로그 닫기
                });

            }
        });

        region2.setOnClickListener(v -> {
            // 다이얼로그를 띄울 때 사용할 커스텀 레이아웃을 설정
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_region2, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);

            // 다이얼로그 생성
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();

            // 다이얼로그 크기 및 위치 설정
            dialog.getWindow().setLayout(970, 800); // 원하는 크기로 설정 (px 단위)
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.x = 0;  // x 위치 조정
            params.y = 30;  // y 위치 조정
            dialog.getWindow().setAttributes(params);

            GridLayout gridLayout = dialogView.findViewById(R.id.gridLayout); // GridLayout 가져오기
            int childCount = gridLayout.getChildCount(); // GridLayout의 자식 수

            for (int i = 0; i < childCount; i++) {
                View child = gridLayout.getChildAt(i);

                Button button = (Button) child;

                // 클릭 리스너 설정
                button.setOnClickListener(view -> {
                    // 선택한 버튼의 텍스트를 spinnerButton에 설정
                    region2.setText(button.getText().toString());
                    dialog.dismiss(); // 다이얼로그 닫기
                });

            }
        });

        //리사이클러뷰 설정 - 추천 활동 요소들이 보일 뷰
        RecyclerView recyclerView = rootView.findViewById(R.id.activityView);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        adapter = new ActivityAdapter(getContext());
        recyclerView.setAdapter(adapter);

        // 탭에 따라 다른 내용 설정
        updateContentBasedOnType(activityType);
    }

    private void updateContentBasedOnType(String type) {
        switch (type) {
            case "맛집":
                ment.setText("가족끼리 먹기 좋은\n근처 맛집");
                //아이템 추가
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image1, "아지오", "이탈리아 음식점", "90",
                        R.drawable.image1, R.drawable.image2, R.drawable.image3,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image2, "어글리스토브", "이탈리아 음식점", "40",
                        R.drawable.image2, R.drawable.image3, R.drawable.image4,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image3, "애슐리 퀸즈", "이탈리아 음식점", "10",
                        R.drawable.image4, R.drawable.image2, R.drawable.image1,
                        "친절해요", "청결해요", "부모님이 좋아하세요"));
                break;
            case "여행":
                ment.setText("가족과 함께 추억을\n쌓을 수 있는 여행지");
                //아이템 추가
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image1, "롯데월드", "놀이공원", "90",
                        R.drawable.image1, R.drawable.image2, R.drawable.image3,
                        "아이와 가기 좋아요", "재밌어요", "풍경이 예뻐요"));
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image2, "강릉 1박 2일 여행", "지역 여행", "40",
                        R.drawable.image2, R.drawable.image3, R.drawable.image4,
                        "음식이 맛있어요", "풍경이 예뻐요", "부모님이 좋아하세요"));
                break;
            case "이색":
                ment.setText("색다른 경험을 제공하는\n이색 활동");
                //아이템 추가
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image1, "이색활동", "이탈리아 음식점", "90",
                        R.drawable.image1, R.drawable.image2, R.drawable.image3,
                        "재밌어요", "친절해요", "부모님이 좋아하세요"));
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image2, "어글리스토브", "이탈리아 음식점", "40",
                        R.drawable.image2, R.drawable.image3, R.drawable.image4,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                break;
            case "야외활동":
                ment.setText("가족과 함께하는\n다양한 야외 활동");
                //아이템 추가
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image1, "야외활동", "이탈리아 음식점", "90",
                        R.drawable.image1, R.drawable.image2, R.drawable.image3,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image2, "어글리스토브", "이탈리아 음식점", "40",
                        R.drawable.image2, R.drawable.image3, R.drawable.image4,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                break;
            case "실내활동":
                ment.setText("편안하게 함께 할 수\n있는 실내 활동");
                //아이템 추가
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image1, "실내활동", "이탈리아 음식점", "90",
                        R.drawable.image1, R.drawable.image2, R.drawable.image3,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                adapter.addItem(new ActivityItem(getContext(), R.drawable.image2, "어글리스토브", "이탈리아 음식점", "40",
                        R.drawable.image2, R.drawable.image3, R.drawable.image4,
                        "음식이 맛있어요", "친절해요", "부모님이 좋아하세요"));
                break;
            default: //디폴트는 어떻게 해야할지 생각
                break;
        }

        adapter.notifyDataSetChanged(); // 데이터 변경 알림
    }
}
