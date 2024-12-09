package com.capstoneandroid.gieokdama.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.activity.WishCategoryActivity;
import com.capstoneandroid.gieokdama.adapter.WishExpectedAdapter;
import com.capstoneandroid.gieokdama.item.WishCategoryItem;
import com.capstoneandroid.gieokdama.item.WishListItem;
import com.capstoneandroid.gieokdama.repository.WishListRepository;

import java.util.ArrayList;
import java.util.List;

public class WishExpectedFragment extends Fragment {

    Long userId = UserInfoManager.getInstance().getUserId();
    private Button selectedButton;
    private ImageButton hamBtn;
    private Integer selectedCategory;
    private ArrayList<WishListItem> items = new ArrayList<>(); // 위시리스트 아이템 추가
    private List<WishCategoryItem> wishCategories = new ArrayList<>(); // 위시 카테고리 리스트 아이템
    private List<Button> categoryButtons = new ArrayList<>(); // 카테고리 버튼 리스트 추가
    private WishExpectedAdapter adapter;
    private LinearLayout linearLayout;

    public WishExpectedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_wish_expected, container, false);

        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        linearLayout = rootView.findViewById(R.id.linearLayout);
        hamBtn = rootView.findViewById(R.id.hamBtn);
        hamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카테고리 추가 화면
                Intent intent = new Intent(getActivity(), WishCategoryActivity.class);
                intent.putExtra("categoryList", (ArrayList<WishCategoryItem>) wishCategories);
                startActivity(intent);
            }
        });

        // 로그인한 사용자 정보 조회 -> 카테고리 리스트 get 요청 보내기
        // 서버로 카테고리 get 요청 보내기
        sendGetWishListCategory();

        adapter = new WishExpectedAdapter(items, getContext());

        RecyclerView recyclerView2 = rootView.findViewById(R.id.wishExpectedView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        recyclerView2.setLayoutManager(gridManager);

        recyclerView2.setAdapter(adapter);
    }

    private void sendGetWishListData(Integer categoryId) {
        WishListRepository wishListRepository = new WishListRepository();
        // 예정된 위시리스트 데이터 가져오기
        wishListRepository.getFamilyExpectedWishList(userId, categoryId, new WishListRepository.GetListCallback() {
            @Override
            public void onListGetSuccess(List<WishListItem> wishListItems) {
                if (getActivity()!=null && isAdded()) {
                    getActivity().runOnUiThread(() -> {
                        // items 리스트에 서버에서 받아온 응답 데이터 추가
                        items.clear(); // 기존 데이터 초기화 (필요 시)
                        // 서버에서 받은 위시리스트 응답을 items에 추가
                        for (WishListItem wishListItem : wishListItems) {
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

                        // 위시리스트 개수 업데이트
                        int itemCount = items.size();

                        // Fragment2로 count 값 전달
                        Bundle result = new Bundle();
                        result.putInt("itemCount", itemCount); // 위시리스트 개수 전달

                        getParentFragmentManager().setFragmentResult("requestKey", result);
                    });
                }
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "예정된 위시리스트 조회 실패: " + errorMessage);
            }
        });
    }

    // 위시리스트 카테고리 get
    private void sendGetWishListCategory() {
        WishListRepository wishListRepository = new WishListRepository();
        // 위시리스트 카테고리 리스트 가져오기
        wishListRepository.getWishListByCategory(userId, new WishListRepository.GetCategoryListCallback() {
            @Override
            public void onListGetSuccess(List<WishCategoryItem> categories) {
                getActivity().runOnUiThread(() -> {
                    wishCategories.clear(); // 기존 데이터 초기화
                    wishCategories.addAll(categories); // 받아온 데이터를 wishCategories에 저장

                    // 서버에서 받은 위시리스트 카테고리 응답을 기반으로 버튼 생성
                    for (int i = 0; i < wishCategories.size(); i++) {
                        WishCategoryItem wishCategory = wishCategories.get(i);
                        Button categoryButton = new Button(getContext());
                        categoryButton.setText(wishCategory.getName()); // 카테고리 이름 설정
                        categoryButton.setBackgroundResource(R.drawable.button_selector);
                        categoryButton.setTextColor(getResources().getColorStateList(R.color.button_color));
                        categoryButton.setTextSize(12);
                        categoryButton.setTypeface(ResourcesCompat.getFont(getContext(), R.font.pretendardmedium));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                210,
                                105
                        );
                        params.setMargins(0, 0, 25, 0);
                        categoryButton.setLayoutParams(params);

                        categoryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 현재 선택된 버튼이 있는 경우
                                if (selectedButton != null) {
                                    selectedButton.setSelected(false);
                                }
                                // 새로 클릭된 버튼을 selected 상태로 변경
                                categoryButton.setSelected(true);
                                selectedButton = categoryButton;

                                // 선택된 카테고리 아이디 저장
                                selectedCategory = wishCategory.getId(); // 버튼 아이디 저장

                                // 카테고리별 위시리스트 get 요청
                                updateItemsBasedOnCategory(selectedCategory);
                            }
                        });

                        // 카테고리 버튼 리스트에 추가하고 레이아웃에 추가
                        categoryButtons.add(categoryButton);
                        linearLayout.addView(categoryButton);

                        // 첫 번째 버튼일 경우 기본 선택
                        if (i == 0) {
                            categoryButton.setSelected(true);
                            selectedButton = categoryButton;
                            selectedCategory = wishCategory.getId();
                            updateItemsBasedOnCategory(selectedCategory); // 첫 번째 버튼에 해당하는 데이터 로드
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "예정된 위시 조회 실패: " + errorMessage);
            }
        });
    }

    // 카테고리에 따른 아이템 업데이트 메서드 -> 위시리스트 get 요청 보내기
    private void updateItemsBasedOnCategory(Integer category) {
        items.clear(); // 기존 아이템 초기화

        sendGetWishListData(category);

        // 어댑터에 변경 사항 알림
        adapter.notifyDataSetChanged();
    }

}