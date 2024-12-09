package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.adapter.WishCategoryAdapter;
import com.capstoneandroid.gieokdama.item.WishCategoryItem;

import java.util.ArrayList;

public class WishCategoryActivity extends BaseActivity{
    ImageButton backBtn, plusBtn;
    RecyclerView wishCategoryView;
    private ArrayList<WishCategoryItem> wishCategoryItems = new ArrayList<>();
    private WishCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_category);

        backBtn = findViewById(R.id.backBtn);
        plusBtn = findViewById(R.id.plusBtn);
        wishCategoryView = findViewById(R.id.wishCategoryView);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishCategoryActivity.this, WishCategoryCreateActivity.class);
                intent.putExtra("source", "WishCategoryActivity");
                startActivity(intent);
            }
        });

        wishCategoryItems = (ArrayList<WishCategoryItem>) getIntent().getSerializableExtra("categoryList");


        // 카테고리 리스트가 비어있지 않다면 RecyclerView에 아이템 추가
        if (wishCategoryItems != null && !wishCategoryItems.isEmpty()) {
            LinearLayoutManager linearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            wishCategoryView.setLayoutManager(linearManager);
            adapter = new WishCategoryAdapter(wishCategoryItems, this);
            wishCategoryView.setAdapter(adapter);
        } else {
            Log.e("WishCategoryActivity", "Received category list is empty or null.");
        }
    }
}
