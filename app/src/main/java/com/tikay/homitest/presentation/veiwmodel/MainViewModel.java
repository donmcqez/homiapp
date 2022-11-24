package com.tikay.homitest.presentation.veiwmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tikay.homitest.data.repository.MediaRepositoryImpl;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.domain.repository.MediaRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    MediaRepository mediaRepository;;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Media>> mediaListLiveData;

    public MainViewModel() {
        mediaRepository = new MediaRepositoryImpl();
    }

    //we will call this method to get the data
    public LiveData<List<Media>> getMediaData() {
        //if the list is null
        if (mediaListLiveData == null) {
            mediaListLiveData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadMediaList();
        }

        //finally we will return the list
        return mediaListLiveData;
    }

    public void loadMediaList() {
        Call<ApiResponse> call = mediaRepository.getMediaList();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse mediaResponse = response.body();
                    if (mediaResponse != null) {
                        mediaListLiveData.setValue(mediaResponse.getMediaDataList());
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
