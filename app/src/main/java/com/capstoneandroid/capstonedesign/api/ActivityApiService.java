package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.item.ActivityItem;
import com.capstoneandroid.capstonedesign.item.GuestbookItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ActivityApiService {
    //카테고리별 활동 조회
    @GET("/activity")
    Call<List<ActivityItem>> getActivity(@Query("category") Integer category);
}
