package com.tikay.homitest.data.repository;

import com.tikay.homitest.data.source.MediaDataSource;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.data.remote.service.HomiRemoteService;
import com.tikay.homitest.domain.repository.MediaRepository;


import retrofit2.Call;

public class MediaRepositoryImpl implements MediaRepository {
    MediaDataSource mediaDataSource;

    public MediaRepositoryImpl(){
        this.mediaDataSource = new MediaDataSource();
    }

    @Override
    public Call<ApiResponse> getMediaList() {
        return mediaDataSource.getMedias();
    }


}
