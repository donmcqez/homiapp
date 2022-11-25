package com.tikay.homitest.data.source;

import com.tikay.homitest.data.remote.service.ApiServiceClient;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.data.remote.service.ApiService;

import retrofit2.Call;

public class SeriesDataSource {
    ApiService apiService;
    public SeriesDataSource(){
        apiService = ApiServiceClient.getAPIService();
    }

    public Call<ApiResponse> getMedias(){
        return apiService.getMediaData();
    }



}






