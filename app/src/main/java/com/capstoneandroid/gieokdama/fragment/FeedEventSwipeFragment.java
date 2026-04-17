package com.capstoneandroid.gieokdama.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.adapter.AlbumAdapter;

public class FeedEventSwipeFragment extends Fragment {
    private AlbumAdapter adapter;
    private ImageButton toggleBtn;
    private Spinner spinner;

    public FeedEventSwipeFragment() {
        // Required empty public constructor
    }
    public FeedEventSwipeFragment(AlbumAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_event_swipe, container, false);
        toggleBtn = rootView.findViewById(R.id.toggleBtn);
        spinner = rootView.findViewById(R.id.spinner);

        // 토글 버튼 설정
        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FeedEventRootFragment의 showSwipeView 호출
                FeedEventRootFragment rootFragment = (FeedEventRootFragment) getParentFragment();
                if (rootFragment != null) {
                    rootFragment.showListView();
                }
            }
        });

        // 커스텀 레이아웃을 사용한 ArrayAdapter 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_item, getResources().getStringArray(R.array.selectEvent));

        // 드롭다운 항목의 레이아웃 설정
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        // 어댑터를 스피너에 적용
        spinner.setAdapter(adapter);

        // 드롭다운이 스피너 아래에 생성되도록 설정
        spinner.setDropDownVerticalOffset(100); // 드롭다운이 스피너에서 떨어져서 보이는 오프셋 설정

        initSwipeView(rootView);
        return rootView;

    }

    private void initSwipeView(View swipeView) {
        // 스와이프 뷰 초기화
        ViewPager2 viewPager = swipeView.findViewById(R.id.eventSwipeView);
        viewPager.setOffscreenPageLimit(3);
        //viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
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
}
