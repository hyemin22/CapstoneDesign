package com.capstoneandroid.gieokdama.repository;

import com.capstoneandroid.gieokdama.api.ActivityApiService;
import com.capstoneandroid.gieokdama.api.RetrofitClient;
import com.capstoneandroid.gieokdama.item.ActivityItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRepository {

    //Retrofit 클라이언트 생성, api 서비스 생성
    private final ActivityApiService activityApiService;

    public ActivityRepository() {
        this.activityApiService = RetrofitClient.getClient()
                .create(ActivityApiService.class);
    }

    public interface GetActivityCallBack {
        void onSuccess(List<ActivityItem> activityItems);
        void onFailure(String errorMessage);
    }

    public void getActivityDataFromServer(Integer category, GetActivityCallBack callback) {
        Call<List<ActivityItem>> call = activityApiService.getActivity(category);

        call.enqueue(new Callback<List<ActivityItem>>() {
            @Override
            public void onResponse(Call<List<ActivityItem>> call, Response<List<ActivityItem>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<ActivityItem> activityItems = response.body();
                    System.out.println("활동 조회 성공");
                    callback.onSuccess(activityItems);
                } else {
                    System.out.println("활동 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ActivityItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }
}
