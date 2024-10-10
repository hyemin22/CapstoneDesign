package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstoneandroid.capstonedesign.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import com.capstoneandroid.capstonedesign.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedMapFragment extends Fragment implements OnMapReadyCallback {
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

        // 지도에 마커 표시
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.628337504787886, 127.09131805304797));
        marker.setWidth(250);
        marker.setHeight(250);
        marker.setIcon(OverlayImage.fromResource(R.drawable.mapphoto));
        marker.setMap(naverMap);

        // 지도 UI 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        // 현재 위치 띄우기
        //위치 및 각도 조정
        /*CameraPosition cameraPosition = new CameraPosition(
                new LatLng(33.38, 126.55),   // 위치 지정
                9,                                     // 줌 레벨
                45,                                       // 기울임 각도
                45                                     // 방향
        );
        naverMap.setCameraPosition(cameraPosition);*/
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