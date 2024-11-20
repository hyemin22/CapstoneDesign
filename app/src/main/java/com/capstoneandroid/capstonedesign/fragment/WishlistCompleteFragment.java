package com.capstoneandroid.capstonedesign.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.activity.MissionActivity;

public class WishlistCompleteFragment extends Fragment {

    Button okBtn, shareBtn;

    public WishlistCompleteFragment() {
        // Required empty public constructor
    }

    public static WishlistCompleteFragment newInstance(String param1, String param2) {
        WishlistCompleteFragment fragment = new WishlistCompleteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 레이아웃을 인플레이트하고 뷰를 반환
        View view = inflater.inflate(R.layout.fragment_wishlist_complete, container, false);

        // 버튼 초기화
        shareBtn = view.findViewById(R.id.shareBtn);
        okBtn = view.findViewById(R.id.okBtn);

        // Share Sheet 호출
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWishlistDetails();
            }
        });

        // okBtn 클릭 리스너 설정
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 액티비티 종료
                requireActivity().finish();
            }
        });

        return view;
    }

    // Share Sheet 호출 메서드
    private void shareWishlistDetails() {
        String shareText = "위시리스트 추가! 🎉\n추가한 위시리스트를 가족들과 공유해보세요!";

        // Intent 생성
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 공유할 데이터 타입 설정
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText); // 공유할 텍스트 추가

        // Share Sheet 호출
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
