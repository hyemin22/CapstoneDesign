package com.capstoneandroid.capstonedesign;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView2;
    GuestbookAdapter adapter;
    HomeWishAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.guestView);
        recyclerView2 = findViewById(R.id.wishlistView);

        //방명록
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);

        adapter = new GuestbookAdapter();
        adapter2 = new HomeWishAdapter();

        adapter.addItem(new GuestbookItem(R.drawable.default_profile_image,
                "오늘 비온대요 다들 우산 챙겨요~ 아 그리고 내일 나 출장 가는 거", "from.아빠"));
        adapter.addItem(new GuestbookItem(R.drawable.character1_image,
                "다들 뭐하니~ 오늘 다들 저녁 먹고 오나?", "from. 엄마"));
        adapter.addItem(new GuestbookItem(R.drawable.character3_image,
                "이번 주에 다 같이 영화보는 거 어때!", "from. 언니"));

        adapter2.addItem(new HomeWishItem("다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정", "D-3"));
        adapter2.addItem(new HomeWishItem("대전 랑골로에서 파스타 먹기", "2024년 5월 11일 예정", "D-6"));
        adapter2.addItem(new HomeWishItem("주말에 범죄도시4 보기", "2024년 5월 15일","D-10"));

        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
    }
}