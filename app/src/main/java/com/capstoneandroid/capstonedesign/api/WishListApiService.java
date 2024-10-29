package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.item.WishExpectedItem;
import com.capstoneandroid.capstonedesign.model.GuestBook;
import com.capstoneandroid.capstonedesign.model.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WishListApiService {

    // WishList 데이터를 POST로 전송
    @POST("/wish")
    Call<Void> saveWishList(@Body WishList wishList);

    // WishList 작성한 가족 id로 접근
    @GET("/wish/category")
    Call<List<WishExpectedItem>> getFamilyWishList(@Query("familyId") Long familyId);

}
