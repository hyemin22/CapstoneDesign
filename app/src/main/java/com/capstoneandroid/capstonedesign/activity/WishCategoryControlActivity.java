package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.CategoryListAdapter;
import com.capstoneandroid.capstonedesign.adapter.DiaryListAdapter;
import com.capstoneandroid.capstonedesign.item.CategoryItem;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;

import java.util.Arrays;

public class WishCategoryControlActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageButton categoryPlusBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wish_category_control);

        backBtn = findViewById(R.id.backBtn);
        categoryPlusBtn = findViewById(R.id.categoryPlusBtn);

        RecyclerView recyclerView = findViewById(R.id.albumCategoryView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CategoryListAdapter adapter = new CategoryListAdapter();

        adapter.addItem(new CategoryItem("🍴", "맛집", "하연"));
        adapter.addItem(new CategoryItem("✈️", "여행", "형주"));
        adapter.addItem(new CategoryItem("\uD83C\uDFA8", "전시회", "수진"));

        recyclerView.setAdapter(adapter);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // + 버튼 눌렀을 때 카테고리 추가 액티비티로 이동
        categoryPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WishCategoryControlActivity.this, WishCategoryCreateActivity.class);
                startActivity(intent);
            }
        });
    }
}