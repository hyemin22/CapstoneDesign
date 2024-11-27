package com.capstoneandroid.capstonedesign.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.activity.DiaryActivity;
import com.capstoneandroid.capstonedesign.api.DiaryApiService;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;
import com.capstoneandroid.capstonedesign.repository.DiaryRepository;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import com.capstoneandroid.capstonedesign.R;

import java.util.List;

public class FeedMapFragment extends Fragment implements OnMapReadyCallback {
    Long userId = UserInfoManager.getInstance().getUserId();
    private MapView mapView;
    private NaverMap mNaverMap;
    private FusedLocationSource locationSource;

    public FeedMapFragment() {
        // Required empty public constructor
    }
    public static FeedMapFragment newInstance() {
        return new FeedMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationSource = new FusedLocationSource(getActivity(), 1000);  // 위치 소스 초기화
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_map,
                container, false);

        mapView = rootView.findViewById(R.id.navermap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);  // MapView에서 비동기로 NaverMap을 초기화

        return rootView;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        //배경 지도 선택
        //naverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
        //naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);

        // NaverMap 초기화
        mNaverMap = naverMap;
        mNaverMap.setMapType(NaverMap.MapType.Basic); // 기본 지도 설정
        mNaverMap.setLocationSource(locationSource);  // 위치 소스 설정

        // 지도 UI 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        // 서버에서 일기 데이터 가져와 지도에 표시
        fetchAndDisplayDiaryMarkers();

        // **카메라 위치 설정**
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.5665, 126.9780), // 서울의 중심 좌표
                10,  // 서울 시내가 보이도록 적당한 줌 레벨
                0,   // 기울임 각도
                0    // 방향 각도
        );
        mNaverMap.setCameraPosition(cameraPosition);
    }

    private void fetchAndDisplayDiaryMarkers() {
        DiaryRepository diaryRepository = new DiaryRepository();
        diaryRepository.getDiaryInAddress(userId, new DiaryRepository.GetDiaryListCallback() {
            @Override
            public void onSuccess(List<DiaryListItem> diaries) {
                getActivity().runOnUiThread(() -> {
                    for (DiaryListItem diary : diaries) {
                        addMarkerForDiary(diary);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "장소별 일기 조회 실패: " + errorMessage);
            }
        });
    }

    private void addMarkerForDiary(DiaryListItem diary) {
        // 위도경도가 null이면
        if (diary.getLatitude() == null || diary.getLongitude() == null) {
            System.out.println("Null latitude or longitude for diary ID: " + diary.getId());
            return; // Null 값을 처리하지 않고 마커를 추가하지 않음
        }

        Marker marker = new Marker();
        marker.setPosition(new LatLng(diary.getLatitude(), diary.getLongitude()));
        marker.setWidth(250);
        marker.setHeight(250);

        // 앨범 첫번째 이미지를 아이콘으로 사용
        Glide.with(this)
                .asBitmap()
                .load(diary.getPhoto1()) // diary.getPhoto1()에 URL이 포함됨
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Bitmap을 OverlayImage로 변환
                        OverlayImage overlayImage = OverlayImage.fromBitmap(resource);
                        marker.setIcon(overlayImage);
                        marker.setMap(mNaverMap); // 지도에 마커 추가

                        // 마커 클릭 시 일기 페이지로 이동
                        marker.setOnClickListener(overlay -> {
                            // 일기 ID를 넘겨서 일기 페이지로 이동
                            Intent intent = new Intent(getActivity(), DiaryActivity.class);
                            intent.putExtra("id", diary.getId()); // 일기 ID 전달
                            startActivity(intent);
                            return true; // 클릭 이벤트가 처리되었음을 나타냄
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // 비워둘 필요가 있으면 추가
                    }
                });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}