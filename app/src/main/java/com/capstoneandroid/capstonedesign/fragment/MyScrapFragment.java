package com.capstoneandroid.capstonedesign.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.capstonedesign.R;

public class MyScrapFragment extends Fragment {

    private TextView restaurantTextView, travelTextView;
    private ImageView familyImageView;
    private ImageButton backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my_scrap, container, false);

    // ImageButton을 찾아서 뒤로 가기 이벤트 처리
    backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 백 스택에서 이전 프래그먼트로 돌아가기
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        }
    });

    // TextView와 ImageView 초기화
    restaurantTextView = view.findViewById(R.id.restaurant);
    travelTextView = view.findViewById(R.id.travel);
    familyImageView = view.findViewById(R.id.familyfood);

    // 각 TextView에 클릭 리스너 설정
    setTextViewClickListener(restaurantTextView);
    setTextViewClickListener(travelTextView);

        return view;
}

    // TextView 클릭 리스너 설정
    private void setTextViewClickListener(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모든 TextView 색상을 회색으로 설정
                resetAllTextViewColors();

                // 선택된 TextView의 텍스트 색상만 검은색으로 변경
                textView.setTextColor(Color.BLACK);

                // '맛집' 클릭 시 이미지를 다시 보여줌
                if (textView == restaurantTextView) {
                    familyImageView.setVisibility(View.VISIBLE); // 이미지 다시 보이기
                } else {
                    // '여행'이나 '이색' 클릭 시 이미지를 숨김
                    familyImageView.setVisibility(View.GONE); // 이미지 숨기기
                }
            }
        });
    }

    // 모든 TextView 색상을 기본 회색으로 리셋하는 메서드
    private void resetAllTextViewColors() {
        restaurantTextView.setTextColor(Color.parseColor("#B4B4B4")); // 회색
        travelTextView.setTextColor(Color.parseColor("#B4B4B4")); // 회색
    }
}