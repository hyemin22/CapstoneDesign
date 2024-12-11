package com.capstoneandroid.gieokdama.api;

import com.capstoneandroid.gieokdama.item.AlbumItem;
import com.capstoneandroid.gieokdama.item.DiaryLikeItem;
import com.capstoneandroid.gieokdama.item.DiaryListItem;
import com.capstoneandroid.gieokdama.model.DiaryNum;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface DiaryApiService {

    // 앨범 저장
    @POST("/album")
    Call<Void> saveAlbum(@Body AlbumItem album);

    // 앨범 조회
    @GET("/album")
    Call<List<AlbumItem>> getAlbum(@Query("userId") Long userId);

    // 일기 저장
    @Multipart
    @POST("/diary")
    Call<Void> saveDiary(@PartMap Map<String, RequestBody> data, // 일반 데이터
                         @Part List<MultipartBody.Part> files,   // 파일 데이터
                         @Part("wishId") Long wishId
    );

    // 일기 조회(앨범별)
    @GET("/diary/album")
    Call<List<DiaryListItem>> getDiaryInAlbum(@Query("userId") Long userId, @Query("albumId") Long albumId);

    // 일기 조회(날짜별)
    @GET("/diary/date")
    Call<List<DiaryListItem>> getDiaryInDate(@Query("userId") Long userId, @Query("date") String date);

    // 일기 개수 조회(날짜별)
    @GET("/diary/num")
    Call<List<DiaryNum>> getDiaryNum(@Query("userId") Long userId);

    // 일기 전체 개수 조회
    @GET("/diary/all")
    Call<Integer> getAllDiaryNum(@Query("userId") Long userId);

    // 일기 조회(장소별)
    @GET("/diary/address")
    Call<List<DiaryListItem>> getDiaryInAddress(@Query("userId") Long userId);

    // 일기 조회(개별)
    @GET("/diary")
    Call<DiaryListItem> getDiary(@Query("id") Long id);

    // 일기 삭제
    @DELETE("/diary")
    Call<Void> deleteDiary(@Query("id") Long id);

    // 공감 추가
    @POST("/diary/like")
    Call<Void> saveLike(@Query("diaryId") Long diaryId, @Query("type") Integer type, @Query("userId") Long userId);

    // 공감 조회
    @GET("/diary/like")
    Call<List<DiaryLikeItem>> getLike(@Query("diaryId") Long diaryId);

    // 사용자가 추가한 공감 조회
    @GET("/diary/like/type")
    Call<Integer> getLikeType(@Query("diaryId") Long diaryId, @Query("userId") Long userId);

    // 공감 삭제
    @DELETE("/diary/like")
    Call<Void> deleteLike(@Query("diaryId") Long diaryId, @Query("type") Integer type, @Query("userId") Long userId);
}
