package com.tikay.homitest.presentation.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Series;
import com.tikay.homitest.presentation.adapters.SeriesAdapter;
import com.tikay.homitest.presentation.veiwmodel.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.sr_home)
    SwipeRefreshLayout srHome;
    //    @BindView(R.id.v_background)
//    View vBackground;
//    @BindView(R.id.drag_frame)
//    DragFrame dragFrame;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private SeriesAdapter seriesAdapter;

    //    private List<Series> mediaList = new ArrayList<>();
    private MainViewModel mainViewModel;

    private float ratio;

//    private HomeContact.HomePresenter homePresenter;
//    private TrendingContact.TrendingPresenter trendingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

//        ImageUtils.showImage(HomiApp.self().getApplicationContext(), ivAvatar, R.drawable.test_0, new CenterCrop(), new CircleCrop());

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        HomiRemoteService homiRemoteService = ApiService.getAPIService();
//        MediaRepository mediaRepository = new MediaRepositoryImpl(homiRemoteService);
//        mainViewModel.setRepositories(mediaRepository);

//        feeds = new ArrayList<>();
//        trending = new ArrayList<>();
        seriesAdapter = new SeriesAdapter();

        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setAdapter(seriesAdapter);

//        dragFrame.setBottomFragment(getSupportFragmentManager(), DetailFragment.newInstance());
//        dragFrame.setTopFragment(getSupportFragmentManager(), PlayFragment.newInstance());
//        dragFrame.setOnDragListener(this);
//        dragFrame.close();
        mainViewModel.getMediaData().observe(this, new Observer<List<Series>>() {
            @Override
            public void onChanged(List<Series> seriesList) {
                Log.d("MainActivity", "data   : " + seriesList);
//                seriesList = medias;
                seriesAdapter.submitList(seriesList);
                srHome.setRefreshing(false);

            }
        });

        fetchData();

        srHome.post(() -> {
            srHome.setRefreshing(true);
        });

        seriesAdapter.setOnItemClickListener((view, media, position) ->
                Toast.makeText(MainActivity.this, media.getTitle() + " Clicked", Toast.LENGTH_LONG).show()
        );

    }


    @Override
    public void onRefresh() {
        srHome.postDelayed(() -> srHome.setRefreshing(false), 1000);
    }

    void fetchData() {
//        homePresenter.getHomeVideo();
        mainViewModel.loadMediaList();
    }

}
