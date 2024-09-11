package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.PageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import java.util.ArrayList;

//홈화면
public class Fragment1 extends Fragment {

    private RelativeLayout missionLayout;
    private CheckBox missionCheck;
    private ImageButton post, guestWrite, imgBtnArroaw, imgBtnArrow2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        initUI(rootView);

        return rootView;
    }

    //xml 레이아웃 안에 들어 있는 위젯이나 레이아웃을 찾아 변수에 할당하는 코드들을 넣기 위해 만듦
    private void initUI(ViewGroup rootView) {

        //알림 및 쪽지 버튼 클릭 시 동작
        post = rootView.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        //방명록 추가 버튼 클릭 시 동작
        guestWrite = rootView.findViewById(R.id.guestWrite);
        guestWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GuestWriteActivity.class);
                startActivity(intent);
            }
        });

        //방명록 아이템 추가
        ArrayList<GuestbookItem> items = new ArrayList<>();

        items.add(new GuestbookItem(R.drawable.default_profile_image,
                "오늘 비온대요 다들 우산 챙겨요~ 아 그리고 내일 나 출장 가는 거", "from.아빠"));
        items.add(new GuestbookItem(R.drawable.character1_image,
                "다들 뭐하니~ 오늘 다들 저녁 먹고 오나?", "from. 엄마"));
        items.add(new GuestbookItem(R.drawable.character3_image,
                "이번 주에 다 같이 영화보는 거 어때!", "from. 언니"));

        GuestbookAdapter adapter = new GuestbookAdapter(items, getContext());
        ViewPager2 viewPager = rootView.findViewById(R.id.guestView);

        //위시리스트 화면으로 넘어가는 > 버튼 클릭 시 동작
        imgBtnArroaw = rootView.findViewById(R.id.imgBtnArrow);
        imgBtnArroaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent 대신 FragmentTransaction 사용
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                Fragment2 fragment2 = new Fragment2();

                // Fragment2로 전환
                transaction.replace(R.id.container, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //위시리스트
        RecyclerView recyclerView1 = rootView.findViewById(R.id.wishlistView);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(linearManager);
        HomeWishAdapter adapter2 = new HomeWishAdapter();

        adapter2.addItem(new HomeWishItem(getContext(),"다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정", "D-3", R.color.lightPink, R.color.pink));
        adapter2.addItem(new HomeWishItem(getContext(),"대전 랑골로에서 파스타 먹기", "2024년 5월 11일 예정", "D-6", R.color.lightGreen, R.color.green));

        //하루 미션 리사이클러뷰
        RecyclerView recyclerView2 = rootView.findViewById(R.id.daymissionView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        recyclerView2.setLayoutManager(gridManager);
        DayMissionAdapter adapter3 = new DayMissionAdapter();

        adapter3.addItem(new DayMissionItem(R.drawable.ic_hand, "하루 시작 굿모닝 인사 보내기", "100%"));
        adapter3.addItem(new DayMissionItem(R.drawable.ic_moon, "하루 끝 굿나잇 인사 보내기", "0%"));

        //방명록
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(adapter);

        //위시리스트
        recyclerView1.setAdapter(adapter2);

        //하루 미션
        recyclerView2.setAdapter(adapter3);

        //방명록
        CompositePageTransformer transform = new CompositePageTransformer();
        transform.addTransformer(new MarginPageTransformer(8));
        transform.addTransformer(new PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                float v = 1 - Math.abs(position);

                // 중앙에 있는 페이지의 크기를 줄입니다.
                float scaleFactor = 0.8f + v * 0.2f;

                // 아이템을 겹치게 하기 위해 페이지 간격을 설정합니다.
                float pageOffset = 200; // 페이지 간격을 150픽셀로 설정합니다.
                float pageMargin = 20; // 페이지 마진을 20픽셀로 설정합니다.
                float myOffset = position * -(2 * pageOffset + pageMargin);

                // 페이지의 크기를 설정합니다.
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // 페이지의 위치를 조절하여 겹치도록 합니다.
                view.setTranslationX(myOffset);

                // 페이지 투명도를 조절합니다.
                float alphafront = 1.0f;
                float alphaback = 0.5f; // 뒤에 보이는 페이지 투명도
                float alphaelse = 0.0f; // 그 외의 페이지 투명도

                // 현재 보이는 페이지는 투명도를 1로 설정하고, 바로 앞, 뒤 페이지들은 투명도를 조절합니다. 그 외의 페이지는 보이지 않도록 설정합니다.
                if (position == 0) {
                    view.setAlpha(alphafront);
                    view.setTranslationZ(0); // 현재 페이지는 최상위에 위치
                } else if((position > 0 && position <= 1) || (position < 0 && position >= -1)){
                    view.setAlpha(alphaback);
                    view.setTranslationZ(-1); // 다른 페이지들을 뒤에 위치시킵니다.
                } else {
                    view.setAlpha(alphaelse);
                }
            }
        });

        //방명록
        viewPager.setPageTransformer(transform);

    }
}
