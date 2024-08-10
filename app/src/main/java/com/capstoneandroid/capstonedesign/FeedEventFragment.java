package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FeedEventFragment extends Fragment {
    private FrameLayout swipeViewContainer;
    private FrameLayout listViewContainer;
    private boolean isSwipeView = true; // 초기 뷰 상태를 스와이프뷰로 설정
    private AlbumAdapter adapter;
    private ImageButton toggleBtn;
    private FloatingActionButton fab;
    private Animation fabOpenAnim;
    private Animation fabCloseAnim;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_event_root, container, false);
        swipeViewContainer = rootView.findViewById(R.id.swipeViewContainer);
        listViewContainer = rootView.findViewById(R.id.listViewContainer);
        toggleBtn = rootView.findViewById(R.id.toggleBtn);
        fab = rootView.findViewById(R.id.fab);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View swipeView = layoutInflater.inflate(R.layout.fragment_feed_event_swipe, swipeViewContainer, false);
        View listView = layoutInflater.inflate(R.layout.fragment_feed_event_list, listViewContainer, false);
        swipeViewContainer.addView(swipeView);
        listViewContainer.addView(listView);

        // 애니메이션 변수 초기화
//        fabOpenAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//        fabCloseAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        // FAB 클릭 리스너 설정 - 수정 필요(현재는 버튼 누르면 바로 앨범 생성 화면으로 이동)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlbumCreateActivity.class);
                startActivity(intent);
            }
        });

//        //앨범 추가 버튼
//        Button plus = swipeViewRoot.findViewById(R.id.plus);
//        plus.setOnClickListener(new View.OnClickListener() {
//            boolean isExpanded = false; // 버튼 상태를 저장할 변수
//
//            @Override
//            public void onClick(View view) {
//                if (!isExpanded) {
//                    // 배경 리소스 변경
//                    plus.setBackgroundResource(R.drawable.ic_floatplusexpand);
//                    // 버튼 크기 변경
//                    ViewGroup.LayoutParams params = plus.getLayoutParams();
//                    params.width = 320; // 너비 설정
//                    params.height = 800; // 높이 설정
//                    plus.setLayoutParams(params);
//
//                    // 버튼 위치 변경
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) plus.getLayoutParams();
//                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
//                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    layoutParams.setMargins(0, 0, -5, 320); // 오른쪽 아래 마진 설정
//                    plus.setLayoutParams(layoutParams);
//
//                    isExpanded = true;
//                } else {
//                    // 다음 화면으로 이동
//                    Intent intent = new Intent(getContext(), AlbumCreateActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });


        // 아이템 추가
        ArrayList<AlbumItem> items = new ArrayList<>();

        items.add(new AlbumItem("우리 가족\n나들이", R.drawable.album_yellow));
        items.add(new AlbumItem("2023\n제주여행", R.drawable.album_blue));
        items.add(new AlbumItem("2022\n하와이 여행", R.drawable.album_white));
        items.add(new AlbumItem("\n첫째 생일", R.drawable.album_red));

        adapter = new AlbumAdapter(items, getContext());

        // 초기 뷰 설정
        if (isSwipeView) {
            initSwipeView(swipeView);
        } else {
            initListView(listView);
        }

        // 토글 버튼 설정
        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSwipeView) {
                    isSwipeView = false;
                    showListView();
                    toggleBtn.setImageResource(R.drawable.ic_event_list);
                } else {
                    isSwipeView = true;
                    showSwipeView();
                    toggleBtn.setImageResource(R.drawable.ic_event_swipe);
                }
            }
        });
        return rootView;
    }

    private void initSwipeView(View swipeView) {
        // 스와이프 뷰 초기화
        ViewPager2 viewPager = swipeView.findViewById(R.id.eventSwipeView);

        // 스와이프 뷰 설정
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(adapter);

        CompositePageTransformer transform = new CompositePageTransformer();
        transform.addTransformer(new MarginPageTransformer(8));
        transform.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                float v = 1 - Math.abs(position);

                // 중앙에 있는 페이지의 크기를 줄입니다.
                float scaleFactor = 0.85f + v * 0.15f;

                // 아이템을 겹치게 하기 위해 페이지 간격을 설정합니다.
                float pageOffset = 10;
                float pageMargin = 10;
                float myOffset = position * -(2 * pageOffset + pageMargin);

                // 페이지의 크기를 설정합니다.
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // 페이지의 위치를 조절하여 겹치도록 합니다.
                view.setTranslationX(myOffset);

                // 가운데 있는 페이지를 위로 올리도록 설정합니다.
                if (position != 0) {
                    float yPosition = -10 * Math.abs(position); // 중앙으로 가까워질수록 더 많이 위로 이동합니다.
                    view.setTranslationY(yPosition);
                } else {
                    view.setTranslationY(0); // 다른 페이지는 원래 위치에 있도록 합니다.
                }
            }
        });

        // 방명록
        viewPager.setPageTransformer(transform);
    }

    private void initListView(View listView) {
        // 리스트 뷰 초기화
        RecyclerView recyclerView = listView.findViewById(R.id.eventListView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 여기서 position에 따라서 span size를 반환
                return 1; // 기본적으로 한 열을 차지하도록 설정
            }
        });
        recyclerView.setLayoutManager(gridManager);
        recyclerView.setAdapter(adapter);
    }

    private void showSwipeView() {
        swipeViewContainer.setVisibility(View.VISIBLE);
        listViewContainer.setVisibility(View.GONE);
    }

    private void showListView() {
        swipeViewContainer.setVisibility(View.GONE);
        listViewContainer.setVisibility(View.VISIBLE);
    }

}

