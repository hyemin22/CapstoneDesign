package com.capstoneandroid.gieokdama.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.gieokdama.R;
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
        fragment4 = new ActivityFragment("야외");
        fragment5 = new ActivityFragment("실내");

        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // 탭 선택시
        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("맛집"));
        tabs.addTab(tabs.newTab().setText("여행"));
        tabs.addTab(tabs.newTab().setText("이색"));
        tabs.addTab(tabs.newTab().setText("야외"));
        tabs.addTab(tabs.newTab().setText("실내"));

        // 각 탭의 텍스트 뷰에 직접 폰트 적용
        tabs.post(() -> {
            for (int i = 0; i < tabs.getTabCount(); i++) {
                TabLayout.Tab tab = tabs.getTabAt(i);
                if (tab != null) {
                    TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                    Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendardsemibold);
                    tabText.setTypeface(typeface);
                }
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                // 탭 텍스트에 폰트 재적용
                TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendardsemibold);
                tabText.setTypeface(typeface);

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
                // 탭 텍스트에 폰트 재적용
                TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendardsemibold);
                tabText.setTypeface(typeface);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}