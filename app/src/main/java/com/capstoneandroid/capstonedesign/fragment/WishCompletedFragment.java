package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.WishExpectedAdapter;
import com.capstoneandroid.capstonedesign.item.WishCompletedItem;
import com.capstoneandroid.capstonedesign.adapter.WishCompletedAdapter;
import com.capstoneandroid.capstonedesign.item.WishExpectedItem;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;

import java.util.ArrayList;
import java.util.List;

public class WishCompletedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int REQUEST_CODE = 100;  // 요청 코드
    private ArrayList<WishCompletedItem> items = new ArrayList<>(); // 위시리시트 아이템 추가

    private WishExpectedAdapter adapter;
    public WishCompletedFragment() {
        // Required empty public constructor
    }

    public static FeedCalMonthFragment newInstance(String param1, String param2) {
        FeedCalMonthFragment fragment = new FeedCalMonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_wish_completed, container, false);

        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        //위시리스트
        RecyclerView recyclerView1 = rootView.findViewById(R.id.wishCompletedView);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(linearManager);
        WishCompletedAdapter adapter = new WishCompletedAdapter(getContext());

        adapter.addItem(new WishCompletedItem(getContext(),"❤️","다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"🔥","대전 랑골로에서 파스타 먹기", "2024년 5월 11일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"✈️","다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"🖌️","대전 랑골로에서 파스타 먹기", "2024년 5월 11일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"🎧","다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정"));

        recyclerView1.setAdapter(adapter);
    }
    private void sendGetCompletedWishListData(Long familyId) {
        WishListRepository wishListRepository = new WishListRepository();
        // 완료된 위시리스트 데이터 가져오기
        wishListRepository.getFamilyCompletedWishList(familyId, new WishListRepository.GetCompletedListCallback() {
            @Override
            public void onListGetSuccess(List<WishCompletedItem> wishCompletedItems) {
                getActivity().runOnUiThread(() -> {
                    // items 리스트에 서버에서 받아온 응답 데이터 추가
                    items.clear(); // 기존 데이터 초기화 (필요 시)
                    // 서버에서 받은 위시리스트 응답을 items에 추가
                    for (WishCompletedItem wishCompletedItem : wishCompletedItems) {
                        items.add(new WishCompletedItem(
                                wishCompletedItem.getEmoji(), // 이모지
                                wishCompletedItem.getTitle(), // 제목
                                wishCompletedItem.getDate() // 날짜
                        ));
                    }
                    // 어댑터에 변경 사항을 알림
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "예정된 위시리스트 조회 실패: " + errorMessage);
            }
        });
    }
}