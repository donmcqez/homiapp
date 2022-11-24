package com.tikay.homitest.domain.repository;

import com.tikay.homitest.domain.model.ApiResponse;

import retrofit2.Call;

public interface SeriesRepository {
//    List<Series> getMedias() throws IOException;
    Call<ApiResponse> getAllSeries();
}
