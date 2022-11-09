package com.tikay.homitest.features.stream.presenter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.ApiResponse;
import com.tikay.homitest.data.remote.service.ApiService;
import com.tikay.homitest.data.remote.service.HomiRemoteService;
import com.tikay.homitest.data.repository.MediaRepositoryImpl;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.domain.repository.MediaRepository;
import com.tikay.homitest.features.stream.veiwmodel.MediaViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.fab)
    private FloatingActionButton fab;

    List<Media> mediaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaViewModel mediaViewModel = new ViewModelProvider(this).get(MediaViewModel.class);

        HomiRemoteService homiRemoteService = ApiService.getAPIService();
        MediaRepository mediaRepository = new MediaRepositoryImpl(homiRemoteService);
        mediaViewModel.setMediaRepository(mediaRepository);

        mediaViewModel.getMediaData().observe(this, new Observer<List<Media>>() {
            @Override
            public void onChanged(List<Media> medias) {
                Log.d("MainActivity", "data   : " + medias);
                mediaList = medias;
            }
        });

//        loadMediaList();

//        model.getHeroes().observe(this, new Observer<List<Hero>>() {
//            @Override
//            public void onChanged(@Nullable List<Hero> heroList) {
//                adapter = new HeroesAdapter(MainActivity.this, heroList);
//                recyclerView.setAdapter(adapter);
//            }
//        });

    }

    private void loadMediaList() {
        HomiRemoteService homiRemoteService = ApiService.getAPIService();
        MediaRepository mediaRepository = new MediaRepositoryImpl(homiRemoteService);
        Call<ApiResponse> call = mediaRepository.getMedias();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse mediaResponse = response.body();
                    if (mediaResponse != null) {
                        Log.d("MainActivity", "message: " + mediaResponse.getMessage());
                        Log.d("MainActivity", "status : " + mediaResponse.isStatus());
                        Log.d("MainActivity", "data   : " + mediaResponse.getMediaDataList());
                    }
                }else {
                    Log.d("MainActivity", " =========> RESPONSE UNSUCCESSFUL ");
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MainActivity","ERROR: ======>  "+ t.getLocalizedMessage());
            }

        });
    }




































}
