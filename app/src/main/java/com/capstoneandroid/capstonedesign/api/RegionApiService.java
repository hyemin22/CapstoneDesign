package com.capstoneandroid.capstonedesign.api;

import com.capstoneandroid.capstonedesign.model.District;
import com.capstoneandroid.capstonedesign.model.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RegionApiService {
    @GET("/api/provinces")
    Call<List<Province>> getProvinces();

    @GET("/api/districts/{provinceId}")
    Call<List<District>> getDistrictsByProvince(@Path("provinceId") int provinceId);
}
