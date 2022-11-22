package com.tikay.homitest.domain.repository;

import com.tikay.homitest.domain.model.ApiResponse;

import retrofit2.Call;

public interface MediaRepository {
//    List<Media> getMedias() throws IOException;
    Call<ApiResponse> getMediaList();
}
