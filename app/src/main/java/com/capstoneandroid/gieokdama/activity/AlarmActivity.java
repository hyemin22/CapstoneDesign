package com.capstoneandroid.gieokdama.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.gieokdama.fragment.AlarmFragment;
import com.capstoneandroid.gieokdama.R;
import com.google.android.material.tabs.TabLayout;

public class AlarmActivity extends BaseActivity {
    ImageButton backBtn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_check);

        backBtn = findViewById(R.id.backBtn);

        // 이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 처음 앱이 시작될 때 기본으로 첫 번째 탭을 보여주기 위해 탭 포지션을 0으로 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, AlarmFragment.newInstance(0)).commit();

        // 탭 선택시
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("활동"));
        tabs.addTab(tabs.newTab().setText("쪽지함"));

        // 각 탭의 텍스트 뷰에 직접 폰트 적용
        tabs.post(() -> {
            for (int i = 0; i < tabs.getTabCount(); i++) {
                TabLayout.Tab tab = tabs.getTabAt(i);
                if (tab != null) {
                    TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.pretendardsemibold);
                    tabText.setTypeface(typeface);
                }
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭: " + position);

                // 탭 텍스트에 폰트 재적용
                TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.pretendardsemibold);
                tabText.setTypeface(typeface);

                // 선택된 탭의 위치에 따라 새로운 프래그먼트 생성
                Fragment selectedFragment = AlarmFragment.newInstance(position);

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commitNow();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 탭 텍스트에 폰트 재적용
                TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.pretendardsemibold);
                tabText.setTypeface(typeface);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
