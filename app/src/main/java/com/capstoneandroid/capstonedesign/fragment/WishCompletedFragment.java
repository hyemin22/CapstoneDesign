package com.capstoneandroid.capstonedesign.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.adapter.WishCompletedAdapter;
import com.capstoneandroid.capstonedesign.item.WishListItem;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.List;

public class WishCompletedFragment extends Fragment {

    Long userId = UserInfoManager.getInstance().getUserId();
    private ArrayList<WishListItem> items = new ArrayList<>(); // 위시리시트 아이템 추가
    private WishCompletedAdapter adapter;
    public WishCompletedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_wish_completed, container, false);

        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {
        RecyclerView recyclerView1 = rootView.findViewById(R.id.wishCompletedView);

        // 로그인한 사용자 가족 정보 조회 -> 카테고리 리스트 get 요청 보내기
        // 서버로 카테고리 get 요청 보내기
        sendGetCompletedWishListData();

        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(linearManager);
        adapter = new WishCompletedAdapter(items, getContext());

        recyclerView1.setAdapter(adapter);
    }
    private void sendGetCompletedWishListData() {
        WishListRepository wishListRepository = new WishListRepository();
        // 완료된 위시리스트 데이터 가져오기
        wishListRepository.getFamilyCompletedWishList(userId, new WishListRepository.GetCompletedListCallback() {
            @Override
            public void onListGetSuccess(List<WishListItem> wishCompletedItems) {
                getActivity().runOnUiThread(() -> {
                    // items 리스트에 서버에서 받아온 응답 데이터 추가
                    items.clear(); // 기존 데이터 초기화 (필요 시)
                    // 서버에서 받은 위시리스트 응답을 items에 추가
                    for (WishListItem wishListItem : wishCompletedItems) {
                        items.add(new WishListItem(
                                getContext(),
                                wishListItem.getId(), // 아이디
                                wishListItem.getTitle(), // 제목
                                wishListItem.getStartDate(), // 시작날짜
                                wishListItem.getEndDate(), // 끝 날짜
                                wishListItem.getCategory(), // 카테고리
                                wishListItem.getEmoji(), // 이모지
                                wishListItem.getAlarm(), // 알람 여부
                                wishListItem.getMemo(), // 메모
                                wishListItem.getCompletedDate(), // 완료일
                                wishListItem.getDday() // 디데이
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