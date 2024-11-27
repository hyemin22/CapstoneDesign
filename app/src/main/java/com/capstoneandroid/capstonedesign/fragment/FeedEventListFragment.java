package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.AlbumAdapter;

public class FeedEventListFragment extends Fragment {
    private AlbumAdapter adapter;
    private ImageButton toggleBtn;
    private Spinner spinner;

    public FeedEventListFragment() {
        // Required empty public constructor
    }

    public FeedEventListFragment(AlbumAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_event_list, container, false);
        toggleBtn = rootView.findViewById(R.id.toggleBtn);
        spinner = rootView.findViewById(R.id.spinner);

        // 토글 버튼 설정
        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FeedEventRootFragment의 showSwipeView 호출
                FeedEventRootFragment rootFragment = (FeedEventRootFragment) getParentFragment();
                if (rootFragment != null) {
                    rootFragment.showSwipeView();
                }
            }
        });

        // 커스텀 레이아웃을 사용한 ArrayAdapter 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_item, getResources().getStringArray(R.array.selectEvent));

        // 드롭다운 항목의 레이아웃 설정
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        // 어댑터를 스피너에 적용
        spinner.setAdapter(adapter);

        // 드롭다운이 스피너 아래에 생성되도록 설정
        spinner.setDropDownVerticalOffset(100); // 드롭다운이 스피너에서 떨어져서 보이는 오프셋 설정

        initListView(rootView);
        return rootView;
    }

    private void initListView(View listView) {
        // 리스트 뷰 초기화
        RecyclerView recyclerView = listView.findViewById(R.id.eventListView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.setAdapter(adapter);
    }
}
