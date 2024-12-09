package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.model.User;
import com.capstoneandroid.gieokdama.repository.GuestBookRepository;
import com.capstoneandroid.gieokdama.repository.UserRepository;

public class GuestBookCheckActivity extends BaseActivity {
    Long userId = UserInfoManager.getInstance().getUserId();
    Long getId; // 방명록 아이디
    ImageButton backBtn, hamBtn;
    TextView name, content;
    String getNickname, userName;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_check); // 액티비티의 레이아웃 파일 설정

        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        name = findViewById(R.id.name);
        content = findViewById(R.id.content);

        //작성된 내용 가져오기
        Intent outIntent = getIntent();
        getId = outIntent.getLongExtra("id", -1L);
        String getContent = outIntent.getStringExtra("content");
        getNickname = outIntent.getStringExtra("nickname");
        content.setText(getContent);
        name.setText(getNickname);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getUserName();

        //햄버거버튼(수정및삭제)
        hamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PopupMenu 생성
                PopupMenu popupMenu = new PopupMenu(GuestBookCheckActivity.this, hamBtn);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());

                // 메뉴 항목 클릭 리스너 설정
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.edit) { // 수정
                            Intent intent = new Intent(GuestBookCheckActivity.this, GuestBookCreateActivity.class);
                            intent.putExtra("id", getId);
                            intent.putExtra("content", getContent);
                            intent.putExtra("source_activity", "GuestCheckActivity"); //액티비티 구분 위한 식별자
                            startActivity(intent);
                            finish();
                            return true;
                        } else if (itemId == R.id.delete) { // 삭제
                            // db에서 현재 방명록 삭제
                            deleteGuestBookData();

                            // 삭제할 아이템의 인덱스를 intent로 넘김
                            Intent resultIntent = new Intent("DELETE_GUESTBOOK_ITEM");
                            resultIntent.putExtra("delete_position", getIntent().getIntExtra("position", -1));
                            LocalBroadcastManager.getInstance(GuestBookCheckActivity.this).sendBroadcast(resultIntent);
                            finish(); // 현재 액티비티 종료
                            return true;
                        }
                        return false;
                    }
                });

                // 팝업 메뉴 보여주기
                popupMenu.show();
            }
        });
    }

    public void getUserName() {
        UserRepository userRepository = new UserRepository();
        userRepository.getUserInfo(userId, new UserRepository.GetInfoCallback() {
            @Override
            public void onInfoGetSuccess(User user) {
                userName = user.getNickname();

                //내가 쓴 방명록이 아니면 햄버거 버튼 보이지 않음
                if (getNickname.equals(userName)) { // 서버에서 가져오는 사용자 이름
                    hamBtn.setVisibility(View.VISIBLE);
                } else {
                    hamBtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onInfoGetFailure(String errorMessage) {
                Log.e("Error", "유저 이름 조회 실패: " + errorMessage);
            }
        });
    }

    public void deleteGuestBookData() {
        // 서버로 DELETE 요청 보내기
        GuestBookRepository repository = new GuestBookRepository();
        repository.deleteGuestBook(getId, new GuestBookRepository.GuestBookCallback() {
            @Override
            public void onSuccess() {
                // 방명록 삭제 성공
                Log.d("GuestBookCreateActivity", "방명록이 성공적으로 삭제되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 방명록 삭제 실패
                Log.e("GuestBookCreateActivity", "방명록 삭제 실패: " + errorMessage);
            }
        });
    }
}
