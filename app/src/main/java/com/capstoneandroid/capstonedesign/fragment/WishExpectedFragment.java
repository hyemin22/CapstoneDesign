package com.capstoneandroid.capstonedesign.fragment;

import static android.content.ContentValues.TAG;

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

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.activity.WishCategoryCreateActivity;
import com.capstoneandroid.capstonedesign.activity.WishCreateActivity;
import com.capstoneandroid.capstonedesign.api.UserApiService;
import com.capstoneandroid.capstonedesign.item.WishExpectedItem;
import com.capstoneandroid.capstonedesign.adapter.WishExpectedAdapter;
import com.capstoneandroid.capstonedesign.model.User;
import com.capstoneandroid.capstonedesign.model.WishCategory;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.network.ApiCallback;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.List;

public class WishExpectedFragment extends Fragment {

    private Button selectedButton;
    private String selectedCategory;
    private static final int REQUEST_CODE = 100;  // 요청 코드
    private ArrayList<WishExpectedItem> items = new ArrayList<>(); // 위시리시트 아이템 추가
    private ArrayList<WishCategory> categories = new ArrayList<>(); // 위시리시트 아이템 추가
    private List<Button> categoryButtons = new ArrayList<>(); // 카테고리 버튼 리스트 추가

    private WishExpectedAdapter adapter;

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

        LinearLayout linearLayout = rootView.findViewById(R.id.linearLayout);
        ImageButton hamBtn = rootView.findViewById(R.id.hamBtn);
        hamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카테고리 추가 화면
                Intent intent = new Intent(getActivity(), WishCategoryCreateActivity.class);
                startActivity(intent);
            }
        });

        // 로그인한 사용자 가족 정보 조회
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error);
            } else if (user != null) {
                Long user_id = user.getId(); // 카카오 사용자 고유 ID

                // 서버로 get 요청 보내기
                sendGetWishListData(user_id);
                sendGetWishListCategory(user_id);

            }
            return null;
        });

        adapter = new WishExpectedAdapter(items, getContext());

        RecyclerView recyclerView2 = rootView.findViewById(R.id.wishExpectedView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        recyclerView2.setLayoutManager(gridManager);

        recyclerView2.setAdapter(adapter);
    }

    private void sendGetWishListData(Long userId) {
        WishListRepository wishListRepository = new WishListRepository();
        // 예정된 위시리스트 데이터 가져오기
        wishListRepository.getFamilyExpectedWishList(userId, selectedCategory, new WishListRepository.GetListCallback() {
            @Override
            public void onListGetSuccess(List<WishExpectedItem> wishExpectedItems) {
                getActivity().runOnUiThread(() -> {
                    // items 리스트에 서버에서 받아온 응답 데이터 추가
                    items.clear(); // 기존 데이터 초기화 (필요 시)
                    // 서버에서 받은 위시리스트 응답을 items에 추가
                    for (WishExpectedItem wishExpectedItem : wishExpectedItems) {
                        items.add(new WishExpectedItem(
                                getContext(),
                                wishExpectedItem.getEmoji(), // 이모지
                                wishExpectedItem.getTitle(), // 제목
                                wishExpectedItem.getDday(),
                                wishExpectedItem.getDate() // 날짜
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

    private void sendGetWishListCategory(Long familyId) {
        WishListRepository wishListRepository = new WishListRepository();
        // 방명록 데이터 가져오기
        wishListRepository.getWishListByCategory(familyId, new WishListRepository.GetCategoryListCallback() {
            @Override
            public void onListGetSuccess(List<WishCategory> wishCategories) {
                getActivity().runOnUiThread(() -> {
                    // 서버에서 받은 위시리스트 카테고리 응답을 기반으로 버튼 생성
                    for (WishCategory wishCategory : wishCategories) {
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

                                // 선택된 카테고리 텍스트 저장
                                selectedCategory = categoryButton.getText().toString(); // 버튼 텍스트 저장
                            }
                        });

                        // 카테고리 버튼 리스트에 추가하고 레이아웃에 추가
                        categoryButtons.add(categoryButton);
                        ((LinearLayout) getView().findViewById(R.id.linearLayout)).addView(categoryButton);
                    }
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "위시 조회 실패: " + errorMessage);
            }
        });
    }
}