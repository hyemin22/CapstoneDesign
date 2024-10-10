package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class Fragment4 extends Fragment {
    ActivityFragment fragment1, fragment2, fragment3, fragment4, fragment5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        fragment1 = new ActivityFragment("맛집");
        fragment2 = new ActivityFragment("여행");
        fragment3 = new ActivityFragment("이색");
        fragment4 = new ActivityFragment("야외활동");
        fragment5 = new ActivityFragment("실내활동");

        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // 탭 선택시
        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("맛집"));
        tabs.addTab(tabs.newTab().setText("여행"));
        tabs.addTab(tabs.newTab().setText("이색"));
        tabs.addTab(tabs.newTab().setText("야외활동"));
        tabs.addTab(tabs.newTab().setText("실내활동"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;

                if(position == 0) {
                    selected = fragment1;
                } else if(position == 1){
                    selected  = fragment2;
                } else if(position == 2){
                    selected  = fragment3;
                } else if(position == 3){
                    selected  = fragment4;
                } else {
                    selected  = fragment5;
                }
                if (selected != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, selected).commit(); // 동기화된 커밋
                } else {
                    // null일 경우 기본 프래그먼트로 대체하거나 오류 처리
                    Fragment defaultFragment = new ActivityFragment("맛집"); // 기본 프래그먼트 설정
                    getChildFragmentManager().beginTransaction().replace(R.id.container, defaultFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}