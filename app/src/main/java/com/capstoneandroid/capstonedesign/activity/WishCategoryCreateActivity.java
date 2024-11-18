package com.capstoneandroid.capstonedesign.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.item.WishCategoryItem;
import com.capstoneandroid.capstonedesign.model.GuestBook;
import com.capstoneandroid.capstonedesign.model.WishCategory;
import com.capstoneandroid.capstonedesign.model.WishList;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.user.UserApiClient;

public class WishCategoryCreateActivity extends BaseActivity {
    Long userId = UserInfoManager.getInstance().getUserId();
    ImageButton backBtn;
    EditText nameEdit;
    TextView pageTitle;
    Button okBtn;
    WishListRepository wishListRepository = new WishListRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_category_create);

        backBtn = findViewById(R.id.backBtn);
        pageTitle = findViewById(R.id.pageTitle);
        nameEdit = findViewById(R.id.nameEdit);
        okBtn = findViewById(R.id.okBtn);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        // 어떤 화면에서 넘어왔는지에 따라 보여지는 화면이 다름
        if ("WishCategoryActivity".equals(source)) { //새로운 카테고리 생성
            pageTitle.setText("카테고리 추가          ");
            okBtn.setText("카테고리 추가");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEdit.getText().toString();

                    // POJO 클래스를 사용하여 위시 데이터 생성
                    WishCategory wishCategory = new WishCategory(userId, name);

                    // 서버로 POST 요청 보내기
                    sendWishListCategory(wishCategory);

                    // 화면 종료
                    finish();
                }
            });
        } else if ("WishCategoryAdapter".equals(source)) {//카테고리 수정
            pageTitle.setText("카테고리 수정          ");
            okBtn.setText("카테고리 수정");
            Integer id = intent.getIntExtra("id", -1);
            String name = intent.getStringExtra("name");
            nameEdit.setText(name);

            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String updateName = nameEdit.getText().toString();

                    // POJO 클래스를 사용하여 위시 데이터 생성
                    WishCategoryItem wishCategoryItem = new WishCategoryItem(getApplicationContext(), id, updateName);

                    // 서버로 PUT 요청 보내기
                    updateWishListCategory(wishCategoryItem);

                    finish();
                }
            });
        }

    }

    // 서버로 POST 요청 보내기
    private void sendWishListCategory(WishCategory wishCategory) {
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

    // 서버로 PUT 요청 보내기
    private void updateWishListCategory(WishCategoryItem wishCategoryItem) {
        wishListRepository.updateWishCategoryToServer(wishCategoryItem, new WishListRepository.WishListCallback() {
            @Override
            public void onSuccess() {
                Log.d("WishCategoryActivity", "위시 카테고리 이름 변경 성공");
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("WishCategoryActivity", "위시 카테고리 이름변경 실패: " + errorMessage);
            }
        });

    }
}