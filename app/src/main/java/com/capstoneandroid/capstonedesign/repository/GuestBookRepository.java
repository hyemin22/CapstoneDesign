package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.GuestBookApiService;
import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.model.GuestBook;
import com.capstoneandroid.capstonedesign.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestBookRepository {

    //Retrofit 클라이언트 생성, api 서비스 생성
    private final GuestBookApiService guestBookApiService;

    public GuestBookRepository() {
        // Retrofit 클라이언트 초기화
        this.guestBookApiService = RetrofitClient.getClient("http://172.19.14.35:8080")
                .create(GuestBookApiService.class);
    }

    public interface GuestBookCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface GetIDCallback {
        void onIDGetSuccess(List<GuestbookItem> guestbookItems);
        void onIDGetFailure(String errorMessage);
    }

    public interface GetAllGuestBooksCallback {
        void onGetAllSuccess(List<User> users);
        void onGetAllFailure(String errorMessage);
    }

    public interface DeleteGuestBookCallback {
        void onDeleteSuccess();
        void onDeleteFailure(String errorMessage);
    }

    public void sendGuestBookDataToServer(GuestBook guestBook, GuestBookCallback callback) {
        //Retrofit 클라이언트 생성, api 서비스 생성
        GuestBookApiService guestBookApiService = RetrofitClient.getClient("http://172.19.14.35:8080").create(GuestBookApiService.class);

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
    // 방명록 조회
    public void getUsersGuestBook(Long userId, GetIDCallback callback) {
        // 인스턴스의 guestBookApiService를 통해 호출
        // get요청
        Call<List<GuestbookItem>> call = guestBookApiService.getUsersGuestBook(userId);

        call.enqueue(new Callback<List<GuestbookItem>>() {
            @Override
            public void onResponse(Call<List<GuestbookItem>> call, Response<List<GuestbookItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<GuestbookItem> guestbookItems = response.body();
                    System.out.println("방명록 조회 성공: 200 OK");
                    callback.onIDGetSuccess(guestbookItems); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("방명록 조회 실패: " + response.errorBody().toString());
                    callback.onIDGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<GuestbookItem>> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onIDGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

}
