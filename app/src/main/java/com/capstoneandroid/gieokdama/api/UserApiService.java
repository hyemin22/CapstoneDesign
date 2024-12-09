package com.capstoneandroid.gieokdama.api;

import com.capstoneandroid.gieokdama.model.User;

import java.util.List;

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

    // 가족 아이디, 닉네임, 캐릭터 조회
    @GET("/family/info1")
    Call<List<User>> getFamilyInfo1(@Query("userId") Long userId);
}
