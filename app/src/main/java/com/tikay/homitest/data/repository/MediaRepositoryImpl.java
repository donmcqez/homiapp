package com.tikay.homitest.data.repository;

import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.data.remote.service.HomiRemoteService;
import com.tikay.homitest.domain.repository.MediaRepository;


import retrofit2.Call;

public class MediaRepositoryImpl implements MediaRepository {

    HomiRemoteService homiRemoteService;

    public MediaRepositoryImpl(HomiRemoteService homiRemoteService){
        this.homiRemoteService = homiRemoteService;

    }

    @Override
    public Call<ApiResponse> getMediaList() {
        return homiRemoteService.getMediaData();
    }


}
