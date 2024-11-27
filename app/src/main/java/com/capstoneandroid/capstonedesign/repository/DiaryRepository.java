package com.capstoneandroid.capstonedesign.repository;

import com.capstoneandroid.capstonedesign.api.DiaryApiService;
import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.item.AlbumItem;
import com.capstoneandroid.capstonedesign.item.DiaryLikeItem;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryRepository {
    private final DiaryApiService diaryApiService;

    public DiaryRepository() {
        this.diaryApiService = RetrofitClient.getClient()
                .create(DiaryApiService.class);
    }

    public interface DiaryCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface GetAlbumCallback {
        void onSuccess(List<AlbumItem> albums);
        void onFailure(String errorMessage);
    }

    public interface GetDiaryListCallback {
        void onSuccess(List<DiaryListItem> diaries);
        void onFailure(String errorMessage);
    }


    public interface GetDiaryCallback {
        void onSuccess(DiaryListItem diary);
        void onFailure(String errorMessage);
    }
    public interface GetLikeCallback {
        void onSuccess(List<DiaryLikeItem> likes);
        void onFailure(String errorMessage);
    }

    public interface GetLikeTypeCallback {
        void onSuccess(Integer Type);
        void onFailure(String errorMessage);
    }


    //앨범 저장
    public void saveAlbumDataToServer(AlbumItem album, DiaryRepository.DiaryCallback callback) {
        Call<Void> call = diaryApiService.saveAlbum(album);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("앨범 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("앨범 추가 실패: " + response.errorBody());
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

    // 앨범 조회
    public void getAlbumList(Long userId, DiaryRepository.GetAlbumCallback callback) {
        Call<List<AlbumItem>> call = diaryApiService.getAlbum(userId);
        call.enqueue(new Callback<List<AlbumItem>>() {
            @Override
            public void onResponse(Call<List<AlbumItem>> call, Response<List<AlbumItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<AlbumItem> albums = response.body();
                    System.out.println("예정된 위시 조회 성공: 200 OK");
                    callback.onSuccess(albums); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("예정된 위시 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<AlbumItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 일기 저장
    public void saveDiary(Map<String, RequestBody> dataMap, List<MultipartBody.Part> fileParts, Long wishId, DiaryRepository.DiaryCallback callback) {
        Call<Void> call = diaryApiService.saveDiary(dataMap, fileParts, wishId); // 매개변수로 이미지 외 데이터, 이미지 데이터
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("일기 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("일기 추가 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 앨범별 일기 조회
    public void getDiaryInAlbum(Long userId, Long albumId, GetDiaryListCallback callback) {
        Call<List<DiaryListItem>> call = diaryApiService.getDiaryInAlbum(userId, albumId);
        call.enqueue(new Callback<List<DiaryListItem>>() {
            @Override
            public void onResponse(Call<List<DiaryListItem>> call, Response<List<DiaryListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<DiaryListItem> diaries = response.body();
                    System.out.println("앨범별 일기 조회 성공: 200 OK");
                    callback.onSuccess(diaries); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("앨범별 일기 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<DiaryListItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 날짜별 일기 조회
    public void getDiaryInDate(Long userId, String date, GetDiaryListCallback callback) {
        Call<List<DiaryListItem>> call = diaryApiService.getDiaryInDate(userId, date);
        call.enqueue(new Callback<List<DiaryListItem>>() {
            @Override
            public void onResponse(Call<List<DiaryListItem>> call, Response<List<DiaryListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<DiaryListItem> diaries = response.body();
                    System.out.println("일자별 일기 조회 성공: 200 OK");
                    callback.onSuccess(diaries); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("일자별 일기 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<DiaryListItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 장소별 일기 조회
    public void getDiaryInAddress(Long userId, GetDiaryListCallback callback) {
        Call<List<DiaryListItem>> call = diaryApiService.getDiaryInAddress(userId);
        call.enqueue(new Callback<List<DiaryListItem>>() {
            @Override
            public void onResponse(Call<List<DiaryListItem>> call, Response<List<DiaryListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<DiaryListItem> diaries = response.body();
                    System.out.println("장소별 일기 조회 성공: 200 OK");
                    callback.onSuccess(diaries); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("장소별 일기 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<DiaryListItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 일기 개별 조회
    public void getDiary(Long id, DiaryRepository.GetDiaryCallback callback) {
        Call<DiaryListItem> call = diaryApiService.getDiary(id);
        call.enqueue(new Callback<DiaryListItem>() {
            @Override
            public void onResponse(Call<DiaryListItem> call, Response<DiaryListItem> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    DiaryListItem diary = response.body();
                    System.out.println("일기 조회 성공: 200 OK");
                    callback.onSuccess(diary); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("일기 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<DiaryListItem> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 일기 삭제
    public void deleteDiary(Long id, DiaryRepository.DiaryCallback callback) {
        Call<Void> call = diaryApiService.deleteDiary(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("일기 삭제 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("일기 삭제 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 공감 추가
    public void saveLike(Long diaryId, Integer type, Long userId, DiaryRepository.DiaryCallback callback) {
        Call<Void> call = diaryApiService.saveLike(diaryId, type, userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("일기 공감 추가 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("일기 공감 추가 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 공감 조회
    public void getLike(Long diaryId, DiaryRepository.GetLikeCallback callback) {
        Call<List<DiaryLikeItem>> call = diaryApiService.getLike(diaryId);
        call.enqueue(new Callback<List<DiaryLikeItem>>() {
            @Override
            public void onResponse(Call<List<DiaryLikeItem>> call, Response<List<DiaryLikeItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    List<DiaryLikeItem> likes = response.body();
                    System.out.println("앨범별 공감 조회 성공: 200 OK");
                    callback.onSuccess(likes); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("앨범별 공감 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<List<DiaryLikeItem>> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 사용자가 추가한 공감 조회
    public void getLikeType(Long diaryId, Long userId, DiaryRepository.GetLikeTypeCallback callback) {
        Call<Integer> call = diaryApiService.getLikeType(diaryId, userId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    Integer type = response.body();
                    System.out.println("사용자 추가 공감 조회 성공: 200 OK");
                    callback.onSuccess(type); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("사용자 추가 공감 조회 실패: " + response.errorBody().toString());
                    callback.onFailure("서버 오류: " + response.message()); // 실패 시 콜백 호출
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }

    // 공감 삭제
    public void deleteLike(Long diaryId, Integer type, Long userId, DiaryRepository.DiaryCallback callback) {
        Call<Void> call = diaryApiService.deleteLike(diaryId, type, userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버로부터 성공 응답을 받았을 때 처리
                    System.out.println("일기 공감 삭제 성공");
                    callback.onSuccess(); // 성공 시 콜백 호출
                } else {
                    // 서버 응답이 있지만 오류가 있을 때 처리
                    System.out.println("일기 공감 삭제 실패: " + response.errorBody());
                    callback.onFailure("서버 오류: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("네트워크 오류: " + t.getMessage());
                callback.onFailure("네트워크 오류: " + t.getMessage()); // 실패 시 콜백 호출
            }
        });
    }
}
