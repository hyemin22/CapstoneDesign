package com.capstoneandroid.gieokdama.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarIconColor();
    }

    // 상태바 글씨 및 아이콘 색 설정 (어두운 아이콘)
    private void setStatusBarIconColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 상태바 아이콘 및 글씨 색상을 어둡게 설정
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            // 상태바 배경색을 유지하거나 명시적으로 설정 (여기서는 흰색으로 설정)
            getWindow().setStatusBarColor(Color.WHITE);  // 원하는 색상으로 설정
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && shouldHideKeyboard(view, ev)) {
                hideKeyboard(view);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 키보드 닫기 조건을 판단하는 메소드
    private boolean shouldHideKeyboard(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        return x < location[0] || x > location[0] + view.getWidth() ||
                y < location[1] || y > location[1] + view.getHeight();
    }

    // 키보드를 숨기는 메소드
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
