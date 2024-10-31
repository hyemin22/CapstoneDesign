package com.capstoneandroid.capstonedesign.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.api.UserApiService;
import com.capstoneandroid.capstonedesign.model.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    //Retrofit 클라이언트 생성, api 서비스 생성
    private final UserApiService userApiService;

    public UserRepository() {
        // Retrofit 클라이언트 초기화
        this.userApiService = RetrofitClient.getClient()
                .create(UserApiService.class);
    }

    public interface SignupCallback {
        void onSignupSuccess(Long familyId);
        void onSignupFailure(String errorMessage);
    }

    public interface FamilyIdValidationCallback {
        void onValidationSuccess();
        void onValidationFailure(String errorMessage);
    }

    public interface  UserExistCallback {
        void onExistCheckSuccess();
        void onExistCheckFailure(String errorMessage);
    }

    public interface GetInfoCallback {
        void onInfoGetSuccess(User user);
        void onInfoGetFailure(String errorMessage);
    }

    // 회원가입 시 가족 아이디 유효한지 검사
    public void checkFamilyIdValidity(Long familyId, FamilyIdValidationCallback callback) {

        //가족 아이디로 get 요청
        Call<Boolean> call = userApiService.getUserByFamilyId(familyId);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null && response.body()) {
                    System.out.println("가족 ID 유효성 검사 성공: 가족 ID가 존재합니다.");
                    callback.onValidationSuccess();
                } else {
                    System.out.println("가족 ID 유효성 검사 실패: 가족 ID가 존재하지 않습니다.");
                    callback.onValidationFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onValidationFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }

    // 회원가입 시 유저 정보 존재하는지 확인 get
    public void isUserExist(Long userId, UserExistCallback callback) {

        // 유저 아이디로 get 요청
        Call<Boolean> call = userApiService.getUserExist(userId);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body()) {
                    System.out.println("유저 존재 여부 검사 성공: 유저가 존재합니다.");
                    callback.onExistCheckSuccess();
                } else {
                    System.out.println("유저 존재 여부 검사 실패: 유저가 존재하지 않습니다.");
                    callback.onExistCheckFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onExistCheckFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }

    // 회원가입 데이터 전달 post
    public void sendSignupDataToServer(User user, SignupCallback callback) {

        //유저 데이터 post 요청
        Call<Long> call = userApiService.saveUser(user);

        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("유저 등록 성공: 200 OK, family_id: " + response.body());
                    callback.onSignupSuccess(response.body()); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("유저 등록 실패: " + response.errorBody().toString());
                    callback.onSignupFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onSignupFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 유저 닉네임, 캐릭터, 전화번호 조회
    public void getUserInfo(Long userId, GetInfoCallback callback) {

        // 유저 데이터 get 요청
        Call<User> call = userApiService.getUserInfo(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    User user = response.body();
                    System.out.println("유저 조회 성공: 200 OK");
                    callback.onInfoGetSuccess(user); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("유저 조회 실패: " + response.errorBody().toString());
                    callback.onInfoGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onInfoGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }
}
