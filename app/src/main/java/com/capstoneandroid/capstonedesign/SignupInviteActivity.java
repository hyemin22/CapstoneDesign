package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SignupInviteActivity extends AppCompatActivity {

    private TextView textInviteCode;
    private static final int LETTER_COUNT = 6; // 영어 소문자 개수
    private static final int NUMBER_COUNT = 3; // 숫자 개수
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_invite);

        textInviteCode = findViewById(R.id.invitecode);
    }

    protected void onResume() {
        super.onResume();
        // 화면이 표시될 때마다 새로운 초대 코드 생성
        generateInviteCode();
    }

    private void generateInviteCode() {
        String inviteCode = getRandomCode(LETTER_COUNT, NUMBER_COUNT);

        // 밑줄이 추가된 SpannableString 생성
        SpannableString content = new SpannableString(inviteCode);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        textInviteCode.setText(content);
    }

    private String getRandomCode(int letterCount, int numberCount) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // 영어 소문자 6자리 추가
        for (int i = 0; i < letterCount; i++) {
            int index = random.nextInt(letters.length());
            code.append(letters.charAt(index));
        }

        // 숫자 3자리 추가
        for (int i = 0; i < numberCount; i++) {
            int index = random.nextInt(numbers.length());
            code.append(numbers.charAt(index));
        }

        // 코드 섞기
        char[] codeArray = code.toString().toCharArray();
        for (int i = 0; i < codeArray.length; i++) {
            int randomIndex = random.nextInt(codeArray.length);
            char temp = codeArray[i];
            codeArray[i] = codeArray[randomIndex];
            codeArray[randomIndex] = temp;
        }

        return new String(codeArray);
    }
}
