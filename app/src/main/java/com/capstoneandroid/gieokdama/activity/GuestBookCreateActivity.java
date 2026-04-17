package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.fragment.GuestbookCompleteFragment;
import com.capstoneandroid.gieokdama.model.GuestBook;
import com.capstoneandroid.gieokdama.repository.GuestBookRepository;

public class GuestBookCreateActivity extends BaseActivity {
    GuestBookRepository repository = new GuestBookRepository();
    Long userId = UserInfoManager.getInstance().getUserId();
    Long getId; //방명록 아이디
    ImageButton backBtn, hamBtn;
    TextView pagename, ment, count;
    EditText contentEdit;
    Button okBtn;
    private int position;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestbook_create);

        // Fragment1으로부터 방명록 position 받기
        position = getIntent().getIntExtra("position", -1);

        pagename = findViewById(R.id.pagename);
        backBtn = findViewById(R.id.backBtn);
        hamBtn = findViewById(R.id.hamBtn);
        ment = findViewById(R.id.ment);
        contentEdit = findViewById(R.id.contentEdit);
        count = findViewById(R.id.count);
        okBtn = findViewById(R.id.okBtn);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //햄버거버튼(삭제)
        hamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PopupMenu 생성
                PopupMenu popupMenu = new PopupMenu(GuestBookCreateActivity.this, hamBtn,
                        Gravity.END, 0, R.style.CustomPopupMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_delete, popupMenu.getMenu());

                // 메뉴 항목 클릭 리스너 설정
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.delete) { // 삭제
                            // db에서 현재 방명록 삭제
                            deleteGuestBookData();

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

        //글자수 표시
        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                count.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Intent에서 식별자 가져오기
        String sourceActivity = getIntent().getStringExtra("source_activity");

        // 식별자에 따라 다른 동작을 수행
        if ("Fragment1".equals(sourceActivity)) {
            // 1. 홈화면 - 방명록 추가 버튼 클릭해서 넘어온거면 - 새 방명록 작성
            // 햄버거버튼 가리기
            hamBtn.setVisibility(View.INVISIBLE);
            //텍스트 변경
            pagename.setText("방명록 작성");
            ment.setText("소소한 일상을\n방명록으로 남겨보아요✍🏻");
            okBtn.setText("올리기");
            // 저장 버튼
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String content = contentEdit.getText().toString(); // 방명록에 입력한 내용

                    // POJO 클래스를 사용하여 방명록 데이터 생성
                    GuestBook guestBook = new GuestBook(userId, content);

                    // 서버로 POST 요청 보내기
                    sendGuestBookData(guestBook);
                }
            });
        } else if ("GuestCheckActivity".equals(sourceActivity)) {
            // 2. 방명록 확인 화면에서 넘어온거면 - 방명록 수정
            //작성된 내용 가져오기
            Intent outIntent = getIntent();
            getId = outIntent.getLongExtra("id", -1L);
            String getContent = outIntent.getStringExtra("content");
            contentEdit.setText(getContent);
            // 햄버거버튼 보이기
            hamBtn.setVisibility(View.VISIBLE);
            //텍스트 변경
            pagename.setText("방명록 수정");
            ment.setText("방명록을\n수정해봐요✍🏻");
            okBtn.setText("수정하기");
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = contentEdit.getText().toString(); // 입력한 내용

                    GuestBook guestBook = new GuestBook(getId, content); // 방명록 아이디와 수정된 내용 전송

                    updateGuestBookData(guestBook);

                    // GuestbookCompleteFragment 생성
                    GuestbookCompleteFragment fragment = new GuestbookCompleteFragment();

                    // Activity의 루트 뷰를 숨기기
                    View mainView = findViewById(R.id.main);
                    mainView.setVisibility(View.INVISIBLE); // 루트 뷰를 INVISIBLE 상태로 설정

                    // 프래그먼트를 현재 Activity 화면에 표시
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, fragment) // 전체 화면을 프래그먼트로 교체
                            .commit();
                }
            });
        }
    }

    private void sendGuestBookData(GuestBook guestBook) {
        // 서버로 POST 요청 보내기
        repository.sendGuestBookDataToServer(guestBook, new GuestBookRepository.GuestBookCallback() {
            @Override
            public void onSuccess() {
                // 방명록 추가 성공
                Log.d("GuestBookCreateActivity", "방명록이 성공적으로 추가되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 방명록 추가 실패
                Log.e("GuestBookCreateActivity", "방명록 추가 실패: " + errorMessage);
            }
        });
    }

    private void updateGuestBookData(GuestBook guestBook) {
        // 서버로 PUT 요청 보내기
        repository.updateGuestBook(guestBook, new GuestBookRepository.GuestBookCallback() {
            @Override
            public void onSuccess() {
                // 방명록 수정 성공
                Log.d("GuestBookCreateActivity", "방명록이 성공적으로 수정되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 방명록 수정 실패
                Log.e("GuestBookCreateActivity", "방명록 수정 실패: " + errorMessage);
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
