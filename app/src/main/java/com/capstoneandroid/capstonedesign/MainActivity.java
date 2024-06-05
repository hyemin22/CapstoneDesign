package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    FeedCalMonthFragment fragment1;
    FeedCalWeekFragment fragment2;

    // 이벤트 피드 추가해서 fragment2로 만들면 됨

    FeedMapFragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 상단탭
        fragment1 = new FeedCalMonthFragment();
        fragment2 = new FeedCalWeekFragment();
        fragment3 = new FeedMapFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // 탭 선택시
        TabLayout tabs = findViewById(R.id.tabs);
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

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
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