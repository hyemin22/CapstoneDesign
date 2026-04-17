package com.capstoneandroid.gieokdama.api;

import com.capstoneandroid.gieokdama.item.ActivityItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ActivityApiService {
    //카테고리별 활동 조회
    @GET("/activity")
    Call<List<ActivityItem>> getActivity(@Query("category") Integer category);
}
