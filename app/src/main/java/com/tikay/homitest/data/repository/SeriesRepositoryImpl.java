package com.tikay.homitest.data.repository;

import com.tikay.homitest.data.source.MediaDataSource;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.domain.repository.SeriesRepository;


import retrofit2.Call;

public class SeriesRepositoryImpl implements SeriesRepository {
    MediaDataSource mediaDataSource;

    public SeriesRepositoryImpl(){
        this.mediaDataSource = new MediaDataSource();
    }

    @Override
    public Call<ApiResponse> getAllSeries() {
        return mediaDataSource.getMedias();
    }


}
