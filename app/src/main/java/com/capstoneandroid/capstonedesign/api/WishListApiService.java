package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.item.WishCategoryItem;
import com.capstoneandroid.capstonedesign.item.WishListItem;
import com.capstoneandroid.capstonedesign.model.WishCategory;
import com.capstoneandroid.capstonedesign.model.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface WishListApiService {

    // WishList 데이터를 POST로 전송
    @POST("/wish")
    Call<Void> saveWishList(@Body WishList wishList);

    // WishList 카테고리 데이터를 POST로 전송
    @POST("/wish/category")
    Call<Void> saveWishListCategory(@Body WishCategory wishCategory);

    // 카테고리 이름 put
    @PUT("/wish/category")
    Call<Void> updateWishListCategory(@Body WishCategoryItem wishCategoryItem);

    // 예정된 위시 리스트 get
    @GET("/wish/expected")
    Call<List<WishListItem>> getFamilyWishListExpected(@Query("userId") Long userId, @Query("category") Integer category);

    // 완료된 위시 리스트 get
    @GET("/wish/completed")
    Call<List<WishListItem>> getFamilyWishListCompleted(@Query("userId") Long userId);

    // 위시 카테고리 리스트 get
    @GET("/wish/category")
    Call<List<WishCategoryItem>> getFamilyWishListCategory(@Query("userId") Long userId);

    // wishList completed true로 변경
    @PUT("/wish/state")
    Call<Void> updateWishState(@Query("id") Long id);

    // 예정된 위시 개수
    @GET("/wish/num")
    Call<Integer> getWishExpectedCount(@Query("userId") Long userId, @Query("completed") boolean completed);

    // 위시 내용 수정
    @PUT("/wish")
    Call<Void> updateWish(@Body WishListItem wishListItem);

    // 위시 삭제
    @DELETE("/wish")
    Call<Void> deleteWish(@Query("id") Long id);

}
