package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.repository.UserRepository;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class StartActivity extends BaseActivity {
    private static final String TAG = "KakaoLogin";
    private long kakaoId;

    // 로그인 결과 처리
    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                Log.e(TAG, "로그인 성공: " + oAuthToken.getAccessToken());
                // 카카오 로그인 후 사용자 정보 조회 및 성공 후 처리
                fetchUserInfo(oAuthToken);
            } else {
                // 로그인 실패
                Log.e(TAG, "로그인 실패: " + (throwable != null ? throwable.getMessage() : "Unknown error"));
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KakaoSdk.init(this, "a06273008a3877ae89b4b37f85acaeab");

        // SharedPreferences에서 로그인 상태 확인
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);
        kakaoId = sharedPreferences.getLong("kakao_id", -1);

        if (isLoggedIn && kakaoId != -1) {
            // 로그인된 상태이며 카카오 ID가 저장되어 있다면 MainActivity로 이동
            UserInfoManager.getInstance().setUserId(kakaoId); // 카카오 ID 설정
            navigateToMainActivity();
        } else {
            // 로그인되지 않은 상태이거나 카카오 ID가 저장되지 않은 경우 온보딩 화면 보여주기
            setContentView(R.layout.activity_start);

            // 카카오 로그인 버튼 클릭 이벤트 설정
            findViewById(R.id.kakaologin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginWithKakao();
                }
            });
        }
    }

    private void fetchUserInfo(OAuthToken token) {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error);
            } else if (user != null) {
                kakaoId = user.getId(); // 카카오 사용자 고유 ID
                UserInfoManager.getInstance().setUserId(kakaoId); // UserInfoManager에 저장
                // 사용자 정보 요청이 완료된 후 로그인 성공 처리
                onLoginSuccess(token);
            }
            return null;
        });
    }

    private void loginWithKakao() {
        // 카카오 로그인 처리 (SDK 사용)
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(StartActivity.this)) {
            UserApiClient.getInstance().loginWithKakaoTalk(StartActivity.this, callback);
        } else {
            UserApiClient.getInstance().loginWithKakaoAccount(StartActivity.this, callback);
        }
    }

    private void onLoginSuccess(OAuthToken token) {
        // 로그인 성공 시 사용자 정보를 확인
        checkIfUserExists(new UserRepository.UserExistCallback() {
            @Override
            public void onExistCheckSuccess() {
                // 기존 사용자는 로그인 상태를 저장하고 MainActivity로 이동
                setLoginState(true, kakaoId);
                navigateToMainActivity();
            }

            @Override
            public void onExistCheckFailure(String errorMessage) {
                // 신규 사용자일 경우 SignUpFamilyIdActivity로 이동
                Intent intent = new Intent(StartActivity.this, SignUpFamilyIdActivity.class);
                intent.putExtra("kakaoId", kakaoId);
                startActivity(intent);

                // 로그인 상태를 저장하여 재실행 시 StartActivity를 건너뜀
                setLoginState(true, kakaoId);
                finish();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkIfUserExists(UserRepository.UserExistCallback callback) {
        UserRepository userRepository = new UserRepository();
        userRepository.isUserExist(kakaoId, new UserRepository.UserExistCallback() {
            @Override
            public void onExistCheckSuccess() {
                // 유저가 존재하면 콜백 호출
                callback.onExistCheckSuccess();
            }

            @Override
            public void onExistCheckFailure(String errorMessage) {
                // 유저가 존재하지 않는다면 콜백 호출
                callback.onExistCheckFailure(errorMessage);
            }
        });
    }

    private void setLoginState(boolean isLoggedIn, long kakaoId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.putLong("kakao_id", kakaoId);
        editor.apply();
    }
}