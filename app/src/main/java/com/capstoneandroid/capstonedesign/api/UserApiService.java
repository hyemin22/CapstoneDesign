package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.model.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    // 새로운 유저 데이터를 POST로 전송
    @POST("/user")
    Call<Long> saveUser(@Body User user);

    // 가족 아이디 유효성 검사
    @GET("/user/familyid")
    Call<Boolean> getUserByFamilyId(@Query("familyId") Long familyId);

    // 유저 존재 여부 검사
    @GET("/user")
    Call<Boolean> getUserExist(@Query("userId") Long userId);

    // 유저 이름, 캐릭터, 번호 조회
    @GET("/user/info")
    Call<User> getUserInfo(@Query("userId") Long userId);
}
