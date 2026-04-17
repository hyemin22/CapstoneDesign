package com.capstoneandroid.gieokdama.api;

import com.capstoneandroid.gieokdama.model.District;
import com.capstoneandroid.gieokdama.model.Province;

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
