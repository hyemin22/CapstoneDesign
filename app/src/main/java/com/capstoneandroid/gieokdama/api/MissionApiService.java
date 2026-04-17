package com.capstoneandroid.gieokdama.api;

import com.capstoneandroid.gieokdama.item.MyMissionItem;
import com.capstoneandroid.gieokdama.item.RecMissionItem;
import com.capstoneandroid.gieokdama.model.Mission;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MissionApiService {

    @POST("/mission")
    Call<Void> saveMission(@Body Mission mission);

    // 내가 작성한 미션 모두 가져오기
    @GET("/mission")
    Call<List<MyMissionItem>> getMyMission(@Query("userId") Long userId);

    // 오늘에 해당하는 미션만 가져오기
    @GET("/mission/today")
    Call<List<MyMissionItem>> getTodayMission(@Query("userId") Long userId);

    // 추천 미션 리스트 가져오기(내가 추가하지 않은 미션만)
    @GET("/mission/rec")
    Call<List<RecMissionItem>> getRecMission(@Query("userId") Long userId);

    // 미션 수정
    @PUT("/mission")
    Call<Void> updateMission(@Body Mission mission);

    // 미션 진행률 수정
    @PUT("/mission/progress")
    Call<Void> updateMissionProgress(@Query("missionId") Long missionId);

    // 미션 삭제
    @DELETE("/mission")
    Call<Void> deleteMission(@Query("id") Long id);
}
