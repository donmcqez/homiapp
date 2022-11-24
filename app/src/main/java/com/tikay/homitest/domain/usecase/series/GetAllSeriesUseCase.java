package com.tikay.homitest.domain.usecase.series;

import com.tikay.homitest.data.repository.SeriesRepositoryImpl;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.domain.repository.SeriesRepository;

import retrofit2.Call;

public class GetAllSeriesUseCase {
    SeriesRepository seriesRepository;
    public GetAllSeriesUseCase(){
        seriesRepository = new SeriesRepositoryImpl();
    }

    public Call<ApiResponse> getAllSeries(){
        return seriesRepository.getAllSeries();
    }
}
