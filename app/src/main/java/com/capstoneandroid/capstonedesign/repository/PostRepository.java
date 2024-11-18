package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.PostApiService;
import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.item.AlarmItem;
import com.capstoneandroid.capstonedesign.item.PostItem;
import com.capstoneandroid.capstonedesign.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    private final PostApiService postApiService;

    public PostRepository() {
        this.postApiService = RetrofitClient.getClient()
                .create(PostApiService.class);
    }

    public interface PostPostCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface GetPostCallback {
        void onSuccess(List<PostItem> alarmItems);
        void onFailure(String errorMessage);
    }

    // 쪽지 POST
    public void sendPostDataToServer(Post post, PostPostCallback callback) {
        Call<Void> call = postApiService.savePost(post);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("쪽지 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("쪽지 추가 실패: " + response.errorBody());
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

    // 쪽지 GET
    public void getPostListFromServer(Long userId, GetPostCallback callback) {
        Call<List<PostItem>> call = postApiService.getPostList(userId);
        call.enqueue(new Callback<List<PostItem>>() {
            @Override
            public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<PostItem> postItems = response.body();
                    System.out.println("쪽지 조회 성공: 200 OK");
                    callback.onSuccess(postItems); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("쪽지 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<PostItem>> call, Throwable t) {
                // 네트워크 오류 등으로 요청 실패했을 때 처리
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());

            }
        });
    }
}
