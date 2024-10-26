package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.model.GuestBook;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GuestBookApiService {

    // 방명록 데이터를 POST로 전송
    @POST("/guestbook")
    Call<Void> saveGuestBook(@Body GuestBook guestBook);
}
