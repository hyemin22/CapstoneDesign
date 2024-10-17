package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.capstoneandroid.capstonedesign.R;
import com.google.android.material.tabs.TabLayout;

//추억 화면
public class Fragment3 extends Fragment {
    FeedCalMonthFragment fragment1;
    FeedEventRootFragment fragment2;
    FeedMapFragment fragment3;
    SearchingFragment searchingFragment;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        // 상단탭
        fragment1 = new FeedCalMonthFragment();
        fragment2 = new FeedEventRootFragment();
        fragment3 = new FeedMapFragment();
        searchingFragment = new SearchingFragment(); // SearchingFragment 초기화

        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // 탭 선택시
        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("일자"));
        tabs.addTab(tabs.newTab().setText("이벤트"));
        tabs.addTab(tabs.newTab().setText("지도"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭: " + position);

                Fragment selected = null;

                if(position == 0){
                    selected = fragment1;
                } else if(position == 1){
                    selected = fragment2;
                } else {
                    selected = fragment3;
                }

                if (selected != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, selected).commitNow(); // 동기화된 커밋
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // SearchingBtn 클릭 리스너 설정
        ImageButton searchingBtn = rootView.findViewById(R.id.SearchingBtn);
        searchingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SearchingFragment로 전환
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, searchingFragment)
                        .addToBackStack(null) // 뒤로 가기 버튼을 위한 백스택 추가
                        .commit();
            }
        });

    }
}
