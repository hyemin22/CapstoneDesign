package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.item.PostItem;
import com.capstoneandroid.capstonedesign.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostApiService {

    // 쪽지 데이터를 POST로 전송
    @POST("/post")
    Call<Void> savePost(@Body Post post);

    // 받은 쪽지 조회
    @GET("/post")
    Call<List<PostItem>> getPostList(@Query("receiver_id") Long userId);
}
