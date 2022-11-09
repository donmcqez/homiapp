package com.tikay.homitest.features.stream.veiwmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.domain.repository.MediaRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaViewModel extends ViewModel {
    MediaRepository mediaRepository;

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Media>> mediaListLiveData;

    public void setMediaRepository(MediaRepository mediaRepository){
        this.mediaRepository = mediaRepository;
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



    //This method is using Retrofit to get the JSON data from URL
    public void loadMediaList() {
//
//
//        HomiRemoteService api = ApiService.getAPIService();
//        Call<ApiMediaResponse> call = api.getMediaData();

        Call<ApiResponse> call = mediaRepository.getMedias();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse mediaResponse = response.body();
                    if (mediaResponse != null) {
                        mediaListLiveData.setValue(mediaResponse.getMediaDataList());
                        Log.d("MediaViewModel", "message: " + mediaResponse.getMessage());
                        Log.d("MediaViewModel", "status : " + mediaResponse.isStatus());
                        Log.d("MediaViewModel", "data   : " + mediaResponse.getMediaDataList());
                    }
                }else {
                    Log.d("MediaViewModel", " =========> RESPONSE UNSUCCESSFUL ");
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MediaViewModel","ERROR: ======>  "+ t.getLocalizedMessage());
            }

        });
    }

































}
