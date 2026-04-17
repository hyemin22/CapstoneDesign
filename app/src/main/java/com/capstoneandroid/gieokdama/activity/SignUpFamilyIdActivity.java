package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.repository.UserRepository;

public class SignUpFamilyIdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_familyid);

        UserRepository userRepository = new UserRepository();

        ImageButton backBtn = findViewById(R.id.backBtn);
        EditText editNum = findViewById(R.id.editNum);
        Button newBtn = findViewById(R.id.newBtn);
        Button okBtn = findViewById(R.id.okBtn1);

        okBtn.setEnabled(false);
        okBtn.setAlpha(0.5f);

        // 번호 입력 시 버튼 활성화
        editNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                okBtn.setEnabled(charSequence.length() > 0);
                okBtn.setAlpha(1.0f);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 이전화면버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // 이전 화면으로 이동
            }
        });

        // 카카오톡 고유 id 가져오기
        Long kakaoId = getIntent().getLongExtra("kakaoId",-1L);

        // 가족 생성하기 버튼
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long familyid = null;
                Intent intent = new Intent(SignUpFamilyIdActivity.this, SignupNameActivity.class);
                intent.putExtra("familyid", familyid);
                intent.putExtra("kakaoId", kakaoId);
                startActivity(intent);
            }
        });

        // 확인 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long familyId = Long.parseLong(editNum.getText().toString());

                // 가족 아이디가 유효한지 확인
                userRepository.checkFamilyIdValidity(familyId, new UserRepository.FamilyIdValidationCallback() {
                    public void onValidationSuccess() {
                        // 가족 아이디가 유효하면 다음 화면으로 이동
                        Intent intent = new Intent(SignUpFamilyIdActivity.this, SignupNameActivity.class);
                        intent.putExtra("familyid", familyId);
                        intent.putExtra("kakaoId", kakaoId);
                        startActivity(intent);
                    }

                    public void onValidationFailure(String errorMessage) {
                        // 가족 아이디가 유효하지 않을 때 메시지 표시
                        Toast.makeText(SignUpFamilyIdActivity.this, "잘못된 가족 아이디를 입력했습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
