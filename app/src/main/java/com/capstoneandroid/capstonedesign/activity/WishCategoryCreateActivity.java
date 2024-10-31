package com.capstoneandroid.capstonedesign.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.model.GuestBook;
import com.capstoneandroid.capstonedesign.model.WishCategory;
import com.capstoneandroid.capstonedesign.model.WishList;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.user.UserApiClient;

public class WishCategoryCreateActivity extends AppCompatActivity {

    ImageButton backBtn;
    EditText titleEdit;
    Button okBtn;
    EditText selectedEmoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wish_category_create);

        backBtn = findViewById(R.id.backBtn);
        titleEdit = findViewById(R.id.titleEdit);
        selectedEmoji = findViewById(R.id.selectedEmoji);
        okBtn = findViewById(R.id.okBtn);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //확인버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인한 사용자 정보 조회
                UserApiClient.getInstance().me((user, error) -> {
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error);
                    } else if (user != null) {
                        Long user_id = user.getId(); // 카카오 사용자 고유 ID
                        String name = titleEdit.getText().toString();

                        // POJO 클래스를 사용하여 위시 데이터 생성
                        WishCategory wishCategory = new WishCategory(user_id, name);

                        // 서버로 POST 요청 보내기
                        sendWishListCategory(wishCategory);
                    }
                    return null;
                });
                finish();
            }
        });
    }

    private void sendWishListCategory(WishCategory wishCategory) {
        // 서버로 POST 요청 보내기
        WishListRepository wishListRepository = new WishListRepository();
        wishListRepository.sendWishListCategoryToServer(wishCategory, new WishListRepository.WishListCallback() {
            @Override
            public void onSuccess() {
                // 위시리스트 카테고리 추가 성공
                Log.d("WishListCategoryCreateActivity", "위시리스트 카테고리가 성공적으로 추가되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 위시리스트 카테고리 추가 실패
                Log.e("WishListCategoryCreateActivity", "위시리스트 카테고리 추가 실패: " + errorMessage);
            }
        });
    }
}