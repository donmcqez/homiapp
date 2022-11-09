package com.tikay.homitest.features.stream;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.tikay.homitest.HomiApp;
import com.tikay.homitest.R;
import com.tikay.homitest.data.remote.service.ApiService;
import com.tikay.homitest.data.remote.service.HomiRemoteService;
import com.tikay.homitest.data.repository.MediaRepositoryImpl;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.domain.repository.MediaRepository;
import com.tikay.homitest.features.home.ui.widget.drag.DragFrame;
import com.tikay.homitest.features.home.utils.Utils;
import com.tikay.homitest.features.home.utils.images.ImageUtils;
import com.tikay.homitest.features.stream.adapters.MediaAdapter;
import com.tikay.homitest.features.stream.veiwmodel.MediaViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DragFrame.OnDragListener, SwipeRefreshLayout.OnRefreshListener/*, HomeContact.HomeView, TrendingContact.TrendingView*/ {

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


    private MediaAdapter mediaAdapter;

//    private List<Media> mediaList = new ArrayList<>();
    private MediaViewModel mediaViewModel;

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

        mediaViewModel = new ViewModelProvider(this).get(MediaViewModel.class);
        HomiRemoteService homiRemoteService = ApiService.getAPIService();
        MediaRepository mediaRepository = new MediaRepositoryImpl(homiRemoteService);
        mediaViewModel.setMediaRepository(mediaRepository);

//        feeds = new ArrayList<>();
//        trending = new ArrayList<>();
        mediaAdapter = new MediaAdapter();

        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setAdapter(mediaAdapter);

//        dragFrame.setBottomFragment(getSupportFragmentManager(), DetailFragment.newInstance());
//        dragFrame.setTopFragment(getSupportFragmentManager(), PlayFragment.newInstance());
//        dragFrame.setOnDragListener(this);
//        dragFrame.close();
        mediaViewModel.getMediaData().observe(this, new Observer<List<Media>>() {
            @Override
            public void onChanged(List<Media> medias) {
                Log.d("MainActivity", "data   : " + medias);
//                mediaList = medias;
                mediaAdapter.submitList(medias);
                srHome.setRefreshing(false);

            }
        });

        fetchData();

        srHome.setOnRefreshListener(this::onClosed);
        srHome.post(() -> {
            srHome.setRefreshing(true);
        });

        mediaAdapter.setOnItemClickListener((view, media, position) ->
                Toast.makeText(MainActivity.this, media.getTitle() + " Clicked", Toast.LENGTH_LONG).show()
        );

    }


    @Override
    public void onRefresh() {
        srHome.postDelayed(() -> srHome.setRefreshing(false), 1000);
    }

//    @Override
//    public void bindTrendingVideo(ArrayList<Video> results) {
//        trending.addAll(results);
//        feeds.add(1, trending);
//        homeAdapter.bindData(feeds);
//        srHome.setRefreshing(false);
//    }
//
//    @Override
//    public void bindHomeVideo(ArrayList<Video> results) {
//        feeds.addAll(results);
//        feeds.add(BaseAdapter.END_TYPE);
//        homeAdapter.bindData(feeds);
//        trendingPresenter.getTrendingVideo();
//    }

    @Override
    public void onDragProcess(float percent) {
//        vBackground.setAlpha(1 - percent);
    }

    @Override
    public void onMaximized() {
        play();
    }

    @Override
    public void onMinimized() {
    }

    @Override
    public void onClosed() {

    }

    @OnClick(R.id.iv_pause)
    public void onIvPauseClicked() {
    }

    @OnClick(R.id.iv_play)
    public void onIvPlayClicked() {
    }

    @OnClick(R.id.iv_close)
//    public void onIvCloseClicked() {
//        dragFrame.close();
//    }

    private void fetchData() {
//        homePresenter.getHomeVideo();
        mediaViewModel.loadMediaList();
    }

    public void play(float r) {
//        ratio = r;
//        if (dragFrame.isMaximized()) {
//            play();
//        } else {
//            dragFrame.maximize();
//        }
    }

    private void play() {
//        dragFrame.postDelayed(delayRunnable, 200L);
    }

    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
//            if (dragFrame == null) return;
//            int heightNew = (int) Math.min(Utils.getScreenWidth() / ratio, Utils.getScreenHeight() - Utils.getScreenHeight() / 3f);
//            if (dragFrame.isMaximized()) {
//                dragFrame.setHeight(heightNew);
//            } else {
//                dragFrame.setHeightWaiting(heightNew);
//            }
        }
    };
}
