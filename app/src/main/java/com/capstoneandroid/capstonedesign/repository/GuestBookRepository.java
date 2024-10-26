package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.GuestBookApiService;
import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.model.GuestBook;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestBookRepository {

    public interface GuestBookCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public void sendGuestBookDataToServer(GuestBook guestBook, GuestBookCallback callback) {
        //Retrofit 클라이언트 생성, api 서비스 생성
        GuestBookApiService guestBookApiService = RetrofitClient.getClient("http://172.19.8.222:8080").create(GuestBookApiService.class);

        //방명록 데이터 post 요청
        Call<Void> call = guestBookApiService.saveGuestBook(guestBook);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("방명록 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("방명록 추가 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                t.printStackTrace();
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }
}
