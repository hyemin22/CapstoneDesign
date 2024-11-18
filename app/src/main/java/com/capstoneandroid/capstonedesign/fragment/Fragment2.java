package com.capstoneandroid.capstonedesign.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.activity.WishCreateActivity;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kakao.sdk.user.UserApiClient;

//위시리스트 화면
public class Fragment2 extends Fragment {
    Long userId = UserInfoManager.getInstance().getUserId();
    Integer wishCount;
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
                intent.putExtra("source", "Fragment2");
                startActivity(intent);
            }
        });

        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // 탭 선택시
        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("예정된 위시리스트"));
        tabs.addTab(tabs.newTab().setText("완료된 위시리스트"));

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

                if (position == 0) {
                    fix1.setText("하고 싶은 것들이");
                    getWishExpectedCount(false);
                    fix2.setText("개가 남았어요!💭");
                    selected = fragment1;
                } else {
                    fix1.setText("지금까지");
                    getWishExpectedCount(true);
                    fix2.setText("개를 이뤘어요!💭");
                    selected = fragment2;
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
    }

    public void getWishExpectedCount(boolean completed) {
        WishListRepository wishListRepository = new WishListRepository();
        wishListRepository.getWishCountFromServer(userId, completed, new WishListRepository.GetListCountCallback() {
            @Override
            public void onListCountGetSuccess(Integer num) {
                wishCount = num;
                count.setText(String.valueOf(wishCount));
            }

            @Override
            public void onListCountGetFailure(String errorMessage) {
                Log.e(TAG, "위시 개수 조회 실패: " + errorMessage);
            }
        });
    }
}
