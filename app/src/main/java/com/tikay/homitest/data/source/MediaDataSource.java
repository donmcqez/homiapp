package com.tikay.homitest.data.source;

import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.data.remote.service.HomiRemoteService;

import retrofit2.Call;

public class MediaDataSource {
    HomiRemoteService homiRemoteService;

    public MediaDataSource(HomiRemoteService homiRemoteService){
        this.homiRemoteService = homiRemoteService;
    }

    public Call<ApiResponse> getMedias(){
        return homiRemoteService.getMediaData();
    }



}






