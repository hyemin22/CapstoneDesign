package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.model.GuestBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GuestBookApiService {

    // 방명록 데이터를 POST로 전송
    @POST("/guestbook")
    Call<Void> saveGuestBook(@Body GuestBook guestBook);

    // 방명록 작성한 유저 id 조회
    @GET("/guestbook/family")
    Call<List<GuestbookItem>> getUsersGuestBook(@Query("writer_id") Long userId);

}
