package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.adapter.WishCategoryAdapter;
import com.capstoneandroid.gieokdama.item.WishCategoryItem;
import com.capstoneandroid.gieokdama.repository.WishListRepository;

import java.util.ArrayList;
import java.util.List;

public class WishCategoryActivity extends BaseActivity{
    Long userId = UserInfoManager.getInstance().getUserId();
    ImageButton backBtn, plusBtn;
    RecyclerView wishCategoryView;
    private ArrayList<WishCategoryItem> wishCategoryItems = new ArrayList<>();
    private WishCategoryAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        // 서버로 카테고리 get 요청 보내기
        sendGetWishListCategory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_category);

        backBtn = findViewById(R.id.backBtn);
        plusBtn = findViewById(R.id.plusBtn);
        wishCategoryView = findViewById(R.id.wishCategoryView);

        //이전버튼
        backBtn.setOnClickListener(view -> onBackPressed());

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishCategoryActivity.this, WishCategoryCreateActivity.class);
                intent.putExtra("source", "WishCategoryActivity");
                startActivity(intent);
            }
        });

        // 서버로 카테고리 get 요청 보내기
        sendGetWishListCategory();

        LinearLayoutManager linearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        wishCategoryView.setLayoutManager(linearManager);

        adapter = new WishCategoryAdapter(wishCategoryItems, this);
        adapter.setOnItemDeletedListener(position -> {
            // 필요 시 삭제 이후 추가 작업 가능
            Log.d("WishCategoryActivity", "아이템 삭제됨: " + position);
        });

        wishCategoryView.setAdapter(adapter);
    }

    // 위시리스트 카테고리 get
    private void sendGetWishListCategory() {
        WishListRepository wishListRepository = new WishListRepository();
        // 위시리스트 카테고리 리스트 가져오기
        wishListRepository.getWishListByCategory(userId, new WishListRepository.GetCategoryListCallback() {
            @Override
            public void onListGetSuccess(List<WishCategoryItem> categories) {
                runOnUiThread(() -> {
                    wishCategoryItems.clear(); // 기존 데이터 초기화
                    wishCategoryItems.addAll(categories); // 받아온 데이터를 wishCategories에 저장
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "위시 카테고리 조회 실패: " + errorMessage);
            }
        });
    }
}
