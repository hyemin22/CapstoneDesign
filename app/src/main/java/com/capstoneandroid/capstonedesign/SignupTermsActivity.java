package com.capstoneandroid.capstonedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupTermsActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private CheckBox checkBoxAll, essential1, essential2, optional;
    private Button okBtn; 

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_terms);

        backBtn = findViewById(R.id.backBtn);
        checkBoxAll = findViewById(R.id.checkbox_all);
        essential1 = findViewById(R.id.essential1);
        essential2 = findViewById(R.id.essential2);
        optional = findViewById(R.id.optional);
        okBtn = findViewById(R.id.okBtn3);

        //확인 버튼 비활성화, 반투명
        okBtn.setEnabled(false);
        okBtn.setAlpha(0.5f);

        //체크박스 초기 설정
        checkBoxAll.setChecked(false);
        essential1.setChecked(false);
        essential2.setChecked(false);
        optional.setChecked(false);
        checkBoxAll.setAlpha(0.5f);
        essential1.setAlpha(0.5f);
        essential2.setAlpha(0.5f);
        optional.setAlpha(0.5f);


        //전체 선택 체크박스 체크 상태 변경 시 호출되는 리스너
        checkBoxAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 전체 선택 체크되면 모든 체크박스를 체크 상태로 설정
            essential1.setChecked(isChecked);
            essential2.setChecked(isChecked);
            optional.setChecked(isChecked);

            // 개별 체크박스 투명도 설정
            essential1.setAlpha(isChecked ? 1.0f : 0.5f);
            essential2.setAlpha(isChecked ? 1.0f : 0.5f);
            optional.setAlpha(isChecked ? 1.0f : 0.5f);

            // 확인 버튼 상태 업데이트
            updateConfirmButtonState();
        });

        //개별 체크박스 체크 상태 변경 시 호출되는 리스너
        CheckBox.OnCheckedChangeListener individualCheckBoxListener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 체크 상태가 변경될 때 호출되는 메서드
                if (!isChecked && checkBoxAll.isChecked()) {
                    // 개별 체크박스가 해제되었고, 전체 선택 체크박스가 체크된 상태인 경우
                    // 전체 선택 체크박스 투명도를 조절
                    checkBoxAll.setAlpha(0.5f);
                } else if (!isChecked && !essential1.isChecked() && !essential2.isChecked() && !optional.isChecked()) {
                    // 개별 체크박스가 해제되고, 모든 개별 체크박스가 모두 해제된 상태인 경우
                    // 전체 선택 체크박스를 해제하고 투명도를 조절
                    checkBoxAll.setChecked(false);
                    checkBoxAll.setAlpha(0.5f);
                } else if (isChecked && essential1.isChecked() && essential2.isChecked() && optional.isChecked()) {
                    // 모든 개별 체크박스가 체크되면 전체 선택 체크박스를 체크
                    checkBoxAll.setChecked(true);
                    checkBoxAll.setAlpha(1.0f);
                }

                // 개별 체크박스 체크 시 투명도 조정
                buttonView.setAlpha(isChecked ? 1.0f : 0.5f);

                //확인 버튼 상태 업데이트
                updateConfirmButtonState();
            }
        };

        //개별 체크박스 리스너 설정
        essential1.setOnCheckedChangeListener(individualCheckBoxListener);
        essential2.setOnCheckedChangeListener(individualCheckBoxListener);
        optional.setOnCheckedChangeListener(individualCheckBoxListener);

        //이전화면버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // 이전 화면으로 이동
            }
        });

        //확인 버튼 클릭 시 다음 화면으로
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupTermsActivity.this, SignupSuccessActivity.class);
                startActivity(intent);
            }
        });
    }

    //확인 버튼 상태 업데이트
    private void updateConfirmButtonState() {
        //필수 항목 체크박스가 모두 체크된 경우 확인 버튼 활성화 및 불투명하게 설정
        okBtn.setEnabled(essential1.isChecked() && essential2.isChecked());
        okBtn.setAlpha(okBtn.isEnabled() ? 1.0f : 0.5f); //버튼 활성화 상태에 따라 투명도 설정
    }
}
