package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.api.WishListApiService;
import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.item.WishCompletedItem;
import com.capstoneandroid.capstonedesign.item.WishExpectedItem;
import com.capstoneandroid.capstonedesign.model.WishList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListRepository {
    //Retrofit 클라이언트 생성, api 서비스 생성
    private final WishListApiService wishListApiService;

    public WishListRepository() {
        // Retrofit 클라이언트 초기화
        this.wishListApiService = RetrofitClient.getClient("http://172.19.8.222:8080")
                .create(WishListApiService.class);
    }


    public interface WishListCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface GetListCallback {
        void onListGetSuccess(List<WishExpectedItem> wishExpectedItems);
        void onListGetFailure(String errorMessage);
    }


    public void sendWishListDataToServer(WishList wishList, WishListRepository.WishListCallback callback) {
        //Retrofit 클라이언트 생성, api 서비스 생성
        WishListApiService wishListApiService = RetrofitClient.getClient("http://172.19.8.222:8080").create(WishListApiService.class);

        //위시리스트 데이터 post 요청
        Call<Void> call = wishListApiService.saveWishList(wishList);

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

    // 위시리스트 조회
    public void getFamilyWishList(Long familyId, GetListCallback callback) {
        // 인스턴스의 WishListApiService를 통해 호출
        // get요청
        Call<List<WishExpectedItem>> call = wishListApiService.getFamilyWishList(familyId);

        call.enqueue(new Callback<List<WishExpectedItem>>() {
            @Override
            public void onResponse(Call<List<WishExpectedItem>> call, Response<List<WishExpectedItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<WishExpectedItem> wishExpectedItems = response.body();
                    System.out.println("위시리스트 조회 성공: 200 OK");
                    callback.onListGetSuccess(wishExpectedItems); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시리스트 조회 실패: " + response.errorBody().toString());
                    callback.onListGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<WishExpectedItem>> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onListGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }
}
