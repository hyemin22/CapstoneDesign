package com.capstoneandroid.capstonedesign;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WishExpectedFragment extends Fragment {

    private Button selectedButton;

    public WishExpectedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_wish_expected, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        LinearLayout linearLayout = rootView.findViewById(R.id.linearLayout);
        ImageButton hamBtn = rootView.findViewById(R.id.hamBtn);
        hamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button category = new Button(getContext());
                category.setText("버튼"); //사용자가 입력한 이름 넣기
                category.setBackgroundResource(R.drawable.button_selector);

                // TextColor 상태 리스트 적용
                category.setTextColor(getResources().getColorStateList(R.color.button_color));

                // 텍스트 크기 설정
                category.setTextSize(17);

                // 폰트 패밀리 설정 (커스텀 폰트)
                category.setTypeface(ResourcesCompat.getFont(getContext(), R.font.pretendardbold));

                // 크기 조절을 위해 LayoutParams 설정
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        190,
                        105
                );
                params.setMargins(0, 0, 25, 0);
                category.setLayoutParams(params);

                category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 현재 선택된 버튼이 있는 경우
                        if (selectedButton != null) {
                            // 현재 선택된 버튼을 unselected 상태로 변경
                            selectedButton.setSelected(false);
                        }
                        // 새로 클릭된 버튼을 selected 상태로 변경
                        category.setSelected(true);
                        selectedButton = category;

                        // 화면 전환 또는 추가 작업 수행
                        //어떤 카테고리를 클릭했느냐에 따라 보여지는 위시리스트 목록이 다름 - 백엔드
                        //updateForSelectedButton(category);
                    }
                });

                linearLayout.addView(category);
            }
        });

        RecyclerView recyclerView2 = rootView.findViewById(R.id.wishExpectedView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        recyclerView2.setLayoutManager(gridManager);
        WishExpectedAdapter adapter = new WishExpectedAdapter();

        adapter.addItem(new WishExpectedItem(getContext(), "🍗", "한강 가서 치킨 시켜 먹기", "D-2", "2024년 5월 24일 예정"));
        adapter.addItem(new WishExpectedItem(getContext(), "🍝", "대전 유명 파스타 가게 가보기", "D-10", "2024년 6월 20일 예정"));
        adapter.addItem(new WishExpectedItem(getContext(), "🍔", "다같이 인앤아웃 버거 먹어보기", "D-13", "2024년 6월 23일 예정"));
        adapter.addItem(new WishExpectedItem(getContext(), "🍚", "춘천 유명 미슐랭 닭갈비 먹어보기", "D-15", "2024년 6월 25일 예정"));
        adapter.addItem(new WishExpectedItem(getContext(), "✈️", "여름 푸켓 여행 가기", "미정", ""));
        adapter.addItem(new WishExpectedItem(getContext(), "✈️", "겨울 삿포로 여행 가기", "미정", ""));

        recyclerView2.setAdapter(adapter);
    }
}