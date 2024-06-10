package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2.PageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class Fragment3 extends Fragment {
    private TabHost tabHost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        tabHost = rootView.findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("일자");
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("이벤트");
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("지도");
        tabHost.addTab(spec3);

        tabHost.setCurrentTab(0);

        initUI(rootView);

        return rootView;
    }

    //xml 레이아웃 안에 들어 있는 위젯이나 레이아웃을 찾아 변수에 할당하는 코드들을 넣기 위해 만듦
    private void initUI(ViewGroup rootView) {

        // 이미지 버튼 찾기
        ImageButton buttonPlus = rootView.findViewById(R.id.plusButton);

        // 이미지 버튼에 OnClickListener 등록
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // album_create.xml 화면으로 이동하는 Intent 생성
                Intent intent = new Intent(getActivity(), AlbumCreateActivity.class);
                startActivity(intent); // 액티비티 시작
            }
        });

        //아이템 추가
        ArrayList<AlbumItem> items = new ArrayList<>();

        items.add(new AlbumItem("2023가족여행", R.drawable.ch_cherry, 
                R.drawable.image2, R.drawable.image3, R.drawable.image1, "2023 제주여행"));
        items.add(new AlbumItem("두번째 가족여행", R.drawable.ch_pineapple,
                R.drawable.image2, R.drawable.image3, R.drawable.image1, "엄마 53번째 생일"));
        items.add(new AlbumItem("여름 여행", R.drawable.ch_mango,
                R.drawable.image2, R.drawable.image3, R.drawable.image1, "막내 졸업식"));

        AlbumAdapter adapter = new AlbumAdapter(items, getContext());
        ViewPager2 viewPager = rootView.findViewById(R.id.albumView);

        //앨범 보기
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(adapter);

        CompositePageTransformer transform = new CompositePageTransformer();
        transform.addTransformer(new MarginPageTransformer(8));
        transform.addTransformer(new PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                float v = 1 - Math.abs(position);

                // 중앙에 있는 페이지의 크기를 줄입니다.
                float scaleFactor = 0.8f + v * 0.2f;

                // 아이템을 겹치게 하기 위해 페이지 간격을 설정합니다.
                float pageOffset = 50; // 페이지 간격을 50픽셀로 설정합니다.
                float pageMargin = 20; // 페이지 마진을 20픽셀로 설정합니다.
                float myOffset = position * -(2 * pageOffset + pageMargin);

                // 페이지의 크기를 설정합니다.
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // 페이지의 위치를 조절하여 겹치도록 합니다.
                view.setTranslationX(myOffset);

                // 가운데 있는 페이지를 아래로 내리도록 설정합니다.
                if (position !=0 ) {
                    float yPosition = -60 * Math.abs(position); // 중앙으로 가까워질수록 더 많이 아래로 이동합니다.
                    view.setTranslationY(yPosition); // 페이지를 아래로 50픽셀 이동합니다.
                } else {
                    view.setTranslationY(0); // 다른 페이지는 원래 위치에 있도록 합니다.
                }
            }
        });

        //방명록
        viewPager.setPageTransformer(transform);

    }
}
