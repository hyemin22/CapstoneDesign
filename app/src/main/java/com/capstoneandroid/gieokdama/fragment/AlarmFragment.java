package com.capstoneandroid.gieokdama.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.adapter.PostAdapter;
import com.capstoneandroid.gieokdama.item.AlarmItem;
import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.activity.PostCheckActivity;
import com.capstoneandroid.gieokdama.activity.PostCreateActivity;
import com.capstoneandroid.gieokdama.adapter.AlarmAdapter;
import com.capstoneandroid.gieokdama.item.PostItem;
import com.capstoneandroid.gieokdama.repository.PostRepository;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment {
    private static final String ARG_TAB_POSITION = "tab_position"; // 탭 포지션 키
    private int tabPosition; // 선택된 탭의 포지션 변수
    private ArrayList<AlarmItem> items = new ArrayList<>();
    private ArrayList<PostItem> items2 = new ArrayList<>(); // 쪽지 리스트
    private AlarmAdapter adapter;
    private PostAdapter adapter2;
    Long user_id;

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

        Button okBtn = rootView.findViewById(R.id.okBtn);

        // 탭 포지션에 따라 다른 아이템을 추가
        if (tabPosition == 0) {
            // 첫 번째 탭: 활동
//            adapter.addItem(new AlarmItem(getContext(),"✏️", "하연님이 추억일기를 작성했어요.", "42분 전"));
//            adapter.addItem(new AlarmItem(getContext(),"✏️", "아빠님이 방명록을 작성했어요.", "2시간 전"));
//            adapter.addItem(new AlarmItem(getContext(),"💬", "아빠님이 내 일기에 댓글을 남겼어요.", "2시간 전"));
//            adapter.addItem(new AlarmItem(getContext(),"❤️", "엄마님이 내 일기에 공감했어요.", "3시간 전"));

            okBtn.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        } else if (tabPosition == 1) {
            // 두 번째 탭: 쪽지함

            // 로그인한 사용자 정보 조회 -> 내가 받은 쪽지 리스트 get 요청 보내기
            UserApiClient.getInstance().me((user, error) -> {
                if (error != null) {
                    Log.e(TAG, "사용자 정보 요청 실패", error);
                } else if (user != null) {
                    user_id = user.getId(); // 카카오 사용자 고유 ID

                    // 서버로 쪽지 get 요청 보내기
                    getPostList();
                }
                return null;
            });

            adapter2 = new PostAdapter(items2, getContext());

            // 클릭 리스너 설정
            adapter2.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    // 아이템 클릭 시 실행할 코드 (쪽지 확인 화면으로 전환)
                    openDetailScreen(position);
                }
            });

            okBtn.setVisibility(View.VISIBLE);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), PostCreateActivity.class);
                    intent.putExtra("source", "AlarmFragment"); //액티비티 구분 위한 식별자
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(adapter2);
        }


    }

    private void openDetailScreen(int position) {
        // 탭 포지션이 1일 때 아이템을 클릭하면 다른 화면으로 전환
        if (tabPosition == 1) {
            Intent intent = new Intent(getContext(), PostCheckActivity.class);
            intent.putExtra("item_position", position);
            startActivity(intent);
        }
    }

    private void getPostList() {
        PostRepository postRepository = new PostRepository();
        postRepository.getPostListFromServer(user_id, new PostRepository.GetPostCallback() {
            @Override
            public void onSuccess(List<PostItem> postItems) {
                getActivity().runOnUiThread(() -> {
                    items2.clear();
                    for (PostItem postItem : postItems) {
                        items2.add(new PostItem(
                                getContext(),
                                postItem.getSender_name(),
                                postItem.getAnonymous_name(),
                                postItem.getReceiver_name(),
                                postItem.getContent(),
                                postItem.getCreated_at()
                        ));
                        System.out.println("items2: " + items2.size()); // 이건 잘 출력됨
                    }
                    // 어댑터에 변경 사항을 알림
                    adapter2.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "쪽지 리스트 조회 실패: " + errorMessage);
            }
        });
    }
}

