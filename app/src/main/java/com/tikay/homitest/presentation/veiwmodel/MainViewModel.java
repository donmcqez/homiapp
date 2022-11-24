package com.tikay.homitest.presentation.veiwmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.domain.model.Series;
import com.tikay.homitest.domain.usecase.series.GetAllSeriesUseCase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    GetAllSeriesUseCase getAllSeriesUseCase;

    private MutableLiveData<List<Series>> seriesLiveData;

    public MainViewModel() {
        getAllSeriesUseCase = new  GetAllSeriesUseCase();
    }

    public LiveData<List<Series>> observeSeriesData() {
        if (seriesLiveData == null) {
            seriesLiveData = new MutableLiveData<>();
            updateSeriesState();
        }

        return seriesLiveData;
    }

    public void updateSeriesState() {
        Call<ApiResponse> call = getAllSeriesUseCase.getAllSeries();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse mediaResponse = response.body();
                    if (mediaResponse != null) {
                        seriesLiveData.setValue(mediaResponse.getMediaDataList());
                        Log.d("MainViewModel", "message: " + mediaResponse.getMessage());
                        Log.d("MainViewModel", "status : " + mediaResponse.isStatus());
                        Log.d("MainViewModel", "data   : " + mediaResponse.getMediaDataList());
                    }
                } else {
                    Log.d("MainViewModel", " =========> RESPONSE UNSUCCESSFUL ");
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MainViewModel", "ERROR: ======>  " + t.getLocalizedMessage());
            }

        });
    }


}
