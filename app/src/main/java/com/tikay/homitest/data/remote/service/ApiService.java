package com.tikay.homitest.data.remote.service;

import com.tikay.homitest.domain.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("test/response.json")
    Call<ApiResponse> getMediaData();

}
