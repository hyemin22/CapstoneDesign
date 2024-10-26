package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.capstoneandroid.capstonedesign.R;


public class SignupInviteActivity extends BaseActivity {

    private TextView familyid;
    private Button signupBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_invite);

        familyid = findViewById(R.id.familyid);
        signupBtn = findViewById(R.id.signupBtn);

        // 가족 아이디 띄워주기
        Intent intent = getIntent();
        Long family_id = intent.getLongExtra("familyId", -1L);
        familyid.setText(String.valueOf(family_id));

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 성공 화면으로 이동
                Intent intent = new Intent(SignupInviteActivity.this, SignupSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}
