package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.api.WishListApiService;
import com.capstoneandroid.capstonedesign.item.WishCategoryItem;
import com.capstoneandroid.capstonedesign.item.WishListItem;
import com.capstoneandroid.capstonedesign.model.WishCategory;
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
        this.wishListApiService = RetrofitClient.getClient()
                .create(WishListApiService.class);
    }

    public interface WishListCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }

    public interface GetListCallback {
        void onListGetSuccess(List<WishListItem> wishLists);

        void onListGetFailure(String errorMessage);
    }

    public interface GetCategoryListCallback {
        void onListGetSuccess(List<WishCategoryItem> wishCategories);

        void onListGetFailure(String errorMessage);
    }

    public interface GetCompletedListCallback {
        void onListGetSuccess(List<WishListItem> wishCompletedItems);

        void onListGetFailure(String errorMessage);
    }

    public interface GetListCountCallback {
        void onListCountGetSuccess(Integer wishCount);

        void onListCountGetFailure(String errorMessage);
    }


    public void sendWishListDataToServer(WishList wishList, WishListRepository.WishListCallback callback) {
        //위시리스트 데이터 post 요청
        Call<Void> call = wishListApiService.saveWishList(wishList);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("위시리스트 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시리스트 추가 실패: " + response.errorBody());
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

    public void getFamilyExpectedWishList(Long userId, Integer category, WishListRepository.GetListCallback callback) {
        // 인스턴스의 wishListApiService 통해 호출
        // get요청
        Call<List<WishListItem>> call = wishListApiService.getFamilyWishListExpected(userId, category);

        call.enqueue(new Callback<List<WishListItem>>() {
            @Override
            public void onResponse(Call<List<WishListItem>> call, Response<List<WishListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<WishListItem> wishListItems = response.body();
                    System.out.println("예정된 위시 조회 성공: 200 OK");
                    callback.onListGetSuccess(wishListItems); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("예정된 위시 조회 실패: " + response.errorBody().toString());
                    callback.onListGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<WishListItem>> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onListGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    public void getFamilyCompletedWishList(Long userId, GetCompletedListCallback callback) {
        // 인스턴스의 wishListApiService 통해 호출
        // get요청
        Call<List<WishListItem>> call = wishListApiService.getFamilyWishListCompleted(userId);

        call.enqueue(new Callback<List<WishListItem>>() {
            @Override
            public void onResponse(Call<List<WishListItem>> call, Response<List<WishListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<WishListItem> wishCompletedItems = response.body();
                    System.out.println("완료된 위시 조회 성공: 200 OK");
                    callback.onListGetSuccess(wishCompletedItems); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("완료된 위시 조회 실패: " + response.errorBody().toString());
                    callback.onListGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<WishListItem>> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onListGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    public void sendWishListCategoryToServer(WishCategory wishCategory, WishListCallback callback) {
        //위시 카테고리 데이터 post 요청
        Call<Void> call = wishListApiService.saveWishListCategory(wishCategory);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("위시 카테고리 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시 카테고리 추가 실패: " + response.errorBody());
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

    public void getWishListByCategory(Long userId, GetCategoryListCallback callback) {
        // 인스턴스의 wishListApiService를 통해 호출
        // get요청
        Call<List<WishCategoryItem>> call = wishListApiService.getFamilyWishListCategory(userId);

        call.enqueue(new Callback<List<WishCategoryItem>>() {
            @Override
            public void onResponse(Call<List<WishCategoryItem>> call, Response<List<WishCategoryItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<WishCategoryItem> wishCategories = response.body();
                    System.out.println("위시 카테고리 조회 성공: 200 OK");
                    callback.onListGetSuccess(wishCategories); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시 카테고리 조회 실패: " + response.errorBody().toString());
                    callback.onListGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<WishCategoryItem>> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onListGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 위시 카테고리 이름 변경
    public void updateWishCategoryToServer(WishCategoryItem wishCategoryItem, WishListCallback callback) {
        Call<Void> call = wishListApiService.updateWishListCategory(wishCategoryItem);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("위시 카테고리 이름 변경 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시 카테고리 이름 변경 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }

    public void updateWishStateToServer(Long id, WishListCallback callback) {
        //위시리스트 데이터 put 요청 (완료로 변경)
        Call<Void> call = wishListApiService.updateWishState(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("위시 상태 변경 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시 상태 변경 실패: " + response.errorBody());
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

    public void getWishCountFromServer(Long userId, boolean completed, WishListRepository.GetListCountCallback callback) {
        // 인스턴스의 wishListApiService 통해 호출
        // get요청
        Call<Integer> call = wishListApiService.getWishExpectedCount(userId, completed);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    Integer itemsCount = response.body();
                    System.out.println("위시 개수 조회 성공: 200 OK");
                    callback.onListCountGetSuccess(itemsCount); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시 개수 조회 실패: " + response.errorBody().toString());
                    callback.onListCountGetFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onListCountGetFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    public void sendWishListUpdateToServer(WishListItem wishList, WishListRepository.WishListCallback callback) {
        //위시리스트 데이터 put 요청
        Call<Void> call = wishListApiService.updateWish(wishList);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("위시리스트 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("위시리스트 추가 실패: " + response.errorBody());
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
