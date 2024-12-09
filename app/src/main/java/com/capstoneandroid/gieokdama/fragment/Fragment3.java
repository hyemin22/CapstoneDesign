package com.capstoneandroid.gieokdama.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.activity.AlbumCreateActivity;
import com.capstoneandroid.gieokdama.activity.DiaryCreateActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

//추억 화면
public class Fragment3 extends Fragment {
    FeedCalMonthFragment fragment1;
    FeedEventRootFragment fragment2;
    FeedMapFragment fragment3;
    SearchingFragment searchingFragment;
    private FloatingActionButton fab, sub1, sub2;
    private boolean isMenuOpen = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        initUI(rootView);

        fab = rootView.findViewById(R.id.fab);
        sub1 = rootView.findViewById(R.id.sub1);
        sub2 = rootView.findViewById(R.id.sub2);

        // FAB 클릭 리스너 설정 - 수정 필요(현재는 버튼 누르면 바로 앨범 생성 화면으로 이동)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        //일기 작성 버튼
        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DiaryCreateActivity.class);
                startActivity(intent);
            }
        });

        //앨범 추가 버튼
        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlbumCreateActivity.class);
                startActivity(intent);
            }
        });

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
                Log.d("MainActivity", "선택된 탭: " + position);

                // 탭 텍스트에 폰트 재적용
                TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendardsemibold);
                tabText.setTypeface(typeface);

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
                // 탭 텍스트에 폰트 재적용
                TextView tabText = (TextView) ((ViewGroup) tab.view).getChildAt(1); // TabLayout의 텍스트 부분
                Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.pretendardsemibold);
                tabText.setTypeface(typeface);
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

    private void openMenu() {
        sub1.setVisibility(View.VISIBLE);
        sub2.setVisibility(View.VISIBLE);

        // 버튼들이 펼쳐지면서 나오는 애니메이션
        AnimatorSet animatorSet1 = new AnimatorSet();
        ObjectAnimator translateYAnim1 = ObjectAnimator.ofFloat(sub1, "translationY", 0f, -235f); // 위로 이동

        AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator translateYAnim2 = ObjectAnimator.ofFloat(sub2, "translationY", 0f, -450f);

        animatorSet1.play(translateYAnim1);
        animatorSet1.setDuration(400);
        animatorSet1.start();

        animatorSet2.play(translateYAnim2);
        animatorSet2.setDuration(400);
        animatorSet2.start();

        isMenuOpen = true;
    }

    private void closeMenu() {
        // 버튼들이 접히면서 사라지는 애니메이션
        AnimatorSet animatorSet1 = new AnimatorSet();
        ObjectAnimator translateYAnim1 = ObjectAnimator.ofFloat(sub1, "translationY", -235f, 0f);

        AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator translateYAnim2 = ObjectAnimator.ofFloat(sub2, "translationY", -450f, 0f);

        animatorSet1.play(translateYAnim1);
        animatorSet1.setDuration(400);
        animatorSet1.start();

        animatorSet2.play(translateYAnim2);
        animatorSet2.setDuration(400);
        animatorSet2.start();

        // 애니메이션 끝난 후 버튼 숨기기
        sub1.postDelayed(new Runnable() {
            @Override
            public void run() {
                sub1.setVisibility(View.GONE);
            }
        }, 500);

        sub2.postDelayed(new Runnable() {
            @Override
            public void run() {
                sub2.setVisibility(View.GONE);
            }
        }, 500);

        isMenuOpen = false;
    }
}
