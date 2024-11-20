package com.capstoneandroid.capstonedesign.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.activity.MainActivity;
import com.capstoneandroid.capstonedesign.activity.MissionActivity;

public class PostCompleteFragment extends Fragment {

    Button okBtn;
    public PostCompleteFragment() {
        // Required empty public constructor
    }

    public static PostCompleteFragment newInstance(String param1, String param2) {
        PostCompleteFragment fragment = new PostCompleteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // 필요한 데이터 처리
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 레이아웃을 인플레이트하고 뷰를 반환
        View view = inflater.inflate(R.layout.fragment_post_complete, container, false);

        // 버튼 초기화
        okBtn = view.findViewById(R.id.okBtn);

        // okBtn 클릭 리스너 설정
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 이동
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);

                // 현재 액티비티 종료
                requireActivity().finish();
            }
        });

        return view;
    }
}