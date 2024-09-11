package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

//위시리스트 화면
public class Fragment2 extends Fragment {
    TextView fix1, fix2, count;
    WishExpectedFragment fragment1;
    WishCompletedFragment fragment2;
    FloatingActionButton fab;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        // 상단 멘트 관련 텍스트
        fix1 = rootView.findViewById(R.id.fix1);
        count = rootView.findViewById(R.id.count);
        fix2 = rootView.findViewById(R.id.fix2);

        // 상단탭
        fragment1 = new WishExpectedFragment();
        fragment2 = new WishCompletedFragment();

        //플로팅 액션 버튼
        fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WishCreateActivity.class);
                startActivity(intent);
            }
        });

        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // 탭 선택시
        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("예정된 위시리스트"));
        tabs.addTab(tabs.newTab().setText("완료된 위시리스트"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭: " + position);

                Fragment selected = null;

                if(position == 0) {
                    fix1.setText("하고 싶은 것들이");
                    count.setText("4"); //백엔드에서 가져오기
                    fix2.setText("개가 남았어요!💭");
                    selected = fragment1;
                }
                else {
                    fix1.setText("지금까지");
                    count.setText("4"); //백엔드에서 가져오기
                    fix2.setText("개를 이뤘어요!💭");
                    selected = fragment2;
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
    }
}
