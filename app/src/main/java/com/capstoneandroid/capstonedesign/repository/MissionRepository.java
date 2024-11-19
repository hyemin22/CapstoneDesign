package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.MissionApiService;
import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.item.MyMissionItem;
import com.capstoneandroid.capstonedesign.item.RecMissionItem;
import com.capstoneandroid.capstonedesign.model.Mission;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionRepository {
    private final MissionApiService missionApiService;

    public MissionRepository() {
        // Retrofit 클라이언트 초기화
        this.missionApiService = RetrofitClient.getClient()
                .create(MissionApiService.class);
    }

    public interface MissionCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface GetMyMissionListCallback {
        void onSuccess(List<MyMissionItem> myMissionItems);
        void onFailure(String errorMessage);
    }

    public interface GetRecMissionListCallback {
        void onSuccess(List<RecMissionItem> recMissionItems);
        void onFailure(String errorMessage);
    }

    public void sendMissionToServer(Mission mission, MissionCallback callback) {
        Call<Void> call = missionApiService.saveMission(mission);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("미션 추가 성공");
                    callback.onSuccess();
                } else {
                    System.out.println("미션 추가 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }

    public void updateMissionToServer(Mission mission, MissionCallback callback) {
        Call<Void> call = missionApiService.updateMission(mission);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("미션 수정 성공");
                    callback.onSuccess();
                } else {
                    System.out.println("미션 수정 실패: " + response.errorBody());
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

    public void deleteMissionToServer(Long id, MissionCallback callback) {
        Call<Void> call = missionApiService.deleteMission(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("미션 삭제 성공");
                    callback.onSuccess();
                } else {
                    System.out.println("미션 삭제 실패: " + response.errorBody());
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

    public void getMyMissionFromServer(Long userId, GetMyMissionListCallback callback) {
        Call<List<MyMissionItem>> call = missionApiService.getMyMission(userId);
        call.enqueue(new Callback<List<MyMissionItem>>() {
            @Override
            public void onResponse(Call<List<MyMissionItem>> call, Response<List<MyMissionItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<MyMissionItem> myMissionItems = response.body();
                    System.out.println("내 미션 조회 성공: 200 OK");
                    callback.onSuccess(myMissionItems); // 성공 시 콜백 호출
                } else {
                    System.out.println("내 미션 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MyMissionItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }

    public void getRecMissionFromServer(Long userId, GetRecMissionListCallback callback) {
        Call<List<RecMissionItem>> call = missionApiService.getRecMission(userId);
        call.enqueue(new Callback<List<RecMissionItem>>() {
            @Override
            public void onResponse(Call<List<RecMissionItem>> call, Response<List<RecMissionItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<RecMissionItem> recMissionItems = response.body();
                    System.out.println("내 미션 조회 성공: 200 OK");
                    callback.onSuccess(recMissionItems); // 성공 시 콜백 호출
                } else {
                    System.out.println("내 미션 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<RecMissionItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage());
            }
        });
    }
}
