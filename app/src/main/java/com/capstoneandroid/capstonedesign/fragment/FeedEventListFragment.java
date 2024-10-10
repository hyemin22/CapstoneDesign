package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
