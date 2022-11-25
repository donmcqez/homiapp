package com.tikay.homitest.data.repository;

import com.tikay.homitest.data.source.SeriesDataSource;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.domain.repository.SeriesRepository;


import retrofit2.Call;

public class SeriesRepositoryImpl implements SeriesRepository {
    SeriesDataSource seriesDataSource;

    public SeriesRepositoryImpl(){
        this.seriesDataSource = new SeriesDataSource();
    }

    @Override
    public Call<ApiResponse> getAllSeries() {
        return seriesDataSource.getMedias();
    }


}
