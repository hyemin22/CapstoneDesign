package com.capstoneandroid.capstonedesign.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.WishCategoryItem;
import com.capstoneandroid.capstonedesign.model.GuestBook;
import com.capstoneandroid.capstonedesign.model.Post;
import com.capstoneandroid.capstonedesign.model.User;
import com.capstoneandroid.capstonedesign.model.WishList;
import com.capstoneandroid.capstonedesign.repository.PostRepository;
import com.capstoneandroid.capstonedesign.repository.UserRepository;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.List;

public class PostCreateActivity extends BaseActivity {
    ImageButton backBtn, spinnerBtn;
    EditText contentEdit, anonName;
    ImageView myChar, receiverChar;
    TextView myName, receiverName;
    CheckBox anonChk;
    Spinner spinner;
    Button okBtn;
    ArrayAdapter<String> adapter;
    Long receiverId, user_id;
    String sender_name, charInfo;
    boolean activityInfo;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        backBtn = findViewById(R.id.backBtn);
        contentEdit = findViewById(R.id.contentEdit);
        anonName = findViewById(R.id.anonName);
        myChar = findViewById(R.id.myChar);
        myName = findViewById(R.id.myName);
        receiverChar = findViewById(R.id.receiverChar);
        receiverName = findViewById(R.id.receiverName);
        anonChk = findViewById(R.id.anonChk);
        spinner = findViewById(R.id.spinner);
        spinnerBtn = findViewById(R.id.spinnerBtn);
        okBtn = findViewById(R.id.okBtn);

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        //이전버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //보내는 사람 나로 자동 설정
        anonChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //익명 체크 시 텍스트상자 띄우기
                myChar.setVisibility(View.INVISIBLE);
                myName.setVisibility(View.INVISIBLE);
                anonName.setVisibility(View.VISIBLE);
            } else {
                //익명 체크 해제 시
                myChar.setVisibility(View.VISIBLE);
                myName.setVisibility(View.VISIBLE);
                anonName.setVisibility(View.INVISIBLE);
            }
        });

        // Intent로 전달된 데이터 받기
        Intent intent = getIntent();
        String sourceActivity = intent.getStringExtra("source");

        // // 어떤 화면에서 넘어왔는지에 따라 다른 동작을 수행
        if ("AlarmFragment".equals(sourceActivity)) {
            activityInfo = false;
            // 1. 쪽지 작성 버튼 클릭해서 넘어온거면 -> 받는 사람 선택
            spinner.setVisibility(View.VISIBLE);
            spinnerBtn.setVisibility(View.VISIBLE);

        } else if ("PostCheckActivity".equals(sourceActivity)) {
            activityInfo = true;
            // 2. 답장 보내러가기 버튼 클릭해서 넘어온거면 -> 받는 사람 자동 설정
            receiverChar.setVisibility(View.VISIBLE);
            receiverName.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            spinnerBtn.setVisibility(View.INVISIBLE);

            sender_name = intent.getStringExtra("sender_name"); // 쪽지 보낸 사람 -> 받는 사람

            // 받는 사람 이름 띄우기
            receiverName.setText(sender_name);
        }

        // DB에서 가족 리스트 가져오는 요청 보내기, userId 저장
        fetchFamilyDataFromDB();

        //확인버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DB에 새로운 쪽지 저장
                String anonymous = anonName.getText().toString();
                String content = contentEdit.getText().toString();

                // POJO 클래스를 사용하여 쪽지 데이터 생성
                Post post = new Post(user_id, anonymous, receiverId, content);

                // 서버로 POST 요청 보내기
                sendPostData(post);
            }
        });
    }

    // DB에서 사용자 정보 가져오는 요청 보내기
    private void getUserInfoData() {
        // 서버로 Get 요청 보내기
        UserRepository userRepository = new UserRepository();
        userRepository.getUserInfo(user_id, new UserRepository.GetInfoCallback() {
            @Override
            public void onInfoGetSuccess(User user) {
                myName.setText(user.getNickname());

                //선택한 캐릭터 띄워주기
                String ch_name = user.getCharacter_choice();
                int drawableId = getResources().getIdentifier(ch_name, "drawable", getPackageName());
                System.out.println("drawable" + drawableId);

                if (drawableId != 0) {
                    myChar.setImageResource(drawableId);
                } else {
                    myChar.setImageResource(R.drawable.ic_my);
                }
            }

            @Override
            public void onInfoGetFailure(String errorMessage) {
                Log.e("PostCreateActivity", "유저 정보 가져오기 실패: " + errorMessage);
            }
        });
    }

    // DB에서 가져온 가족 리스트를 스피너에 넣기, 유저 정보 띄우기
    private void fetchFamilyDataFromDB() {
        // 유저 아이디 보내서 카테고리 가져오기
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error);
            } else if (user != null) {
                user_id = user.getId(); // 카카오 사용자 고유 ID 저장

                sendGetFamilyList(); // 카테고리 요청
                getUserInfoData(); // 유저 닉네임, 캐릭터 정보 DB에서 받아와서 띄우기
            }
            return null;
        });
    }

    // 위시리스트 카테고리 이름 리스트 가져와서 스피너에 넣기
    private void sendGetFamilyList() {
        UserRepository userRepository = new UserRepository();
        userRepository.getFamilyInfo1(user_id, new UserRepository.GetFamilyInfoCallback() {
            @Override
            public void onInfoGetSuccess(List<User> familyInfos) {
                runOnUiThread(() -> {
                    // 서버에서 받은 가족 리스트 응답을 기반으로 스피너에 리스트 넣기
                    List<String> familyList = new ArrayList<>();

                    familyList.add("받는 사람을 선택해주세요");

                    // 가족 이름을 리스트에 추가
                    for (User family : familyInfos) {
                        familyList.add(family.getNickname());

                        // *답장 쓰러가기에서만 해당* 받는 사람 캐릭터, 아이디 정보 저장
                        if (family.getNickname().equals(sender_name)) {
                            charInfo = family.getCharacter_choice(); // 캐릭터 정보 저장
                            receiverId = family.getId(); // 받는 사람 아이디 저장
                            break;
                        }
                    }

                    // *답장 쓰러가기에서만 해당* 받는 사람 캐릭터 띄우기
                    if (activityInfo) {
                        int drawableId = getResources().getIdentifier(charInfo, "drawable", getPackageName());
                        System.out.println("drawable" + drawableId);

                        if (drawableId != 0) {
                            receiverChar.setImageResource(drawableId);
                        } else {
                            receiverChar.setImageResource(R.drawable.ic_my);
                        }
                    }

                    // 스피너 어댑터에 가족 이름 리스트 추가
                    adapter.clear();
                    adapter.addAll(familyList);
                    adapter.notifyDataSetChanged();

                    //받는 사람 선택 스피너
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // 선택된 가족의 ID를 저장
                            if (position != 0) {
                                // 가족 아이디를 리스트에서 가져와서 처리
                                receiverId = familyInfos.get(position - 1).getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // 선택되지 않은 상태에 대한 처리
                        }
                    });
                });
            }

            @Override
            public void onInfoGetFailure(String errorMessage) {
                Log.e("Error", "가족 리스트 조회 실패: " + errorMessage);
            }
        });
    }


    private void sendPostData(Post post) {
        // 서버로 POST 요청 보내기
        PostRepository postRepository = new PostRepository();
        postRepository.sendPostDataToServer(post, new PostRepository.PostPostCallback() {
            @Override
            public void onSuccess() {
                // 쪽지 추가 성공
                Log.d("PostCreateActivity", "쪽지가 성공적으로 추가되었습니다");
                finish(); //현재 액티비티 종료
            }

            @Override
            public void onFailure(String errorMessage) {
                // 위시리스트 추가 실패
                Log.e("PostCreateActivity", "쪽지 추가 실패: " + errorMessage);
            }
        });
    }
}
