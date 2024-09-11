package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmFragment extends Fragment {
    private static final String ARG_TAB_POSITION = "tab_position"; // 탭 포지션 키

    private int tabPosition; // 선택된 탭의 포지션 변수

    public AlarmFragment() {
        // Required empty public constructor
    }

    // 새로운 인스턴스를 생성하면서 탭 포지션을 전달받는 메서드
    public static AlarmFragment newInstance(int tabPosition) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_POSITION, tabPosition); // 포지션 전달
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tabPosition = getArguments().getInt(ARG_TAB_POSITION); // 전달된 포지션 값 가져오기
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);
        initUI(rootView);  // UI 초기화
        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.items);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearManager);
        AlarmAdapter adapter = new AlarmAdapter();

        // 탭 포지션에 따라 다른 아이템을 추가
        if (tabPosition == 0) {
            // 첫 번째 탭: 활동
            adapter.addItem(new AlarmItem(getContext(),"✏️", "하연님이 추억일기를 작성했어요.", "42분 전"));
            adapter.addItem(new AlarmItem(getContext(),"✏️", "아빠님이 방명록을 작성했어요.", "2시간 전"));
            adapter.addItem(new AlarmItem(getContext(),"💬", "아빠님이 내 일기에 댓글을 남겼어요.", "2시간 전"));
            adapter.addItem(new AlarmItem(getContext(),"❤️", "엄마님이 내 일기에 공감했어요.", "3시간 전"));
        } else if (tabPosition == 1) {
            // 두 번째 탭: 쪽지함
            adapter.addItem(new AlarmItem(getContext(),"✉️", "엄마가 쪽지를 보냈어요.", "방금 전"));
            adapter.addItem(new AlarmItem(getContext(),"✉️", "아빠가 쪽지를 보냈어요.", "2024.06.05"));

            // 클릭 리스너 설정
            adapter.setOnItemClickListener(new AlarmAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 아이템 클릭 시 실행할 코드 (쪽지 확인 화면으로 전환)
                    openDetailScreen(position);
                }
            });
        }

        recyclerView.setAdapter(adapter);
    }

    private void openDetailScreen(int position) {
        // 탭 포지션이 1일 때 아이템을 클릭하면 다른 화면으로 전환
        if (tabPosition == 1) {
            Intent intent = new Intent(getContext(), PostCheckActivity.class);
            intent.putExtra("item_position", position);
            startActivity(intent);
        }
    }
}

