package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Episode;
import com.tikay.homitest.presentation.adapters.PlayListAdapter;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;
import com.tikay.homitest.presentation.veiwmodel.MediaViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PlayerFragment extends Fragment {
    public PlayerFragment(){
        super(R.layout.fragment_player);
    }

    List<Episode> episodeList = new ArrayList<>();
    
    @BindView(R.id.rv_episode)
    RecyclerView recyclerView;

    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.video_view)
    PlayerView playerView;

    @BindView(R.id.tvEpisodeIndex)
    TextView tvEpisodeIndex;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCategory)
    TextView tvCategory;

    private ExoPlayer player;
    private boolean playWhenReady = true;
    private int currentItem = 0;
    private long playbackPosition = 0L;

    private NavController navController;

    private int mediaPosition = 0;

    private PlayListAdapter playListAdapter;
    private MediaViewModel mediaViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        navController = Navigation.findNavController(view);

//        mediaPosition = SeasonFragmentArgs.fromBundle(getArguments());
        mediaPosition = getArguments() != null ? getArguments().getInt("position") : 0;

        initViewModel();
        setUpRecyclerView();
//        initPlayer();
        observeData();
    }

    private void initPlayer() {
        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentItem, playbackPosition);
        player.prepare();
        if (player.isPlaying()){
            ivBanner.setVisibility(View.GONE);
        }
    }

    private void initViewModel() {
// I'm using requireActivity() in order to share the same viewModel with host activity
        mediaViewModel = new ViewModelProvider(requireActivity()).get(MediaViewModel.class);
    }

    private void setUpRecyclerView() {
        playListAdapter = new PlayListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(playListAdapter);

        playListAdapter.setOnItemClickListener((v, suggestion, position) -> {

        });
    }

    private void observeData() {
        mediaViewModel.getMediaData().observe(getViewLifecycleOwner(), mediaList -> {
            episodeList.addAll( mediaList.get(0).getSeasons().getSeason1());
            initViews(episodeList.get(0));

            playListAdapter.submitList(episodeList);
        });
    }

    private void initViews(Episode episode) {
        String index = episode.getId() + "/" + episodeList.size();
        tvTitle.setText(episode.getTitle());
        tvEpisodeIndex.setText(index);

        int margin = (int) Utils.dp2px(requireActivity(),8);
        ImageUtils.showImage(
                requireActivity(),
                ivBanner,
                episode.getBanner(),
                new CenterCrop(),
                new RoundedCorners(margin)
        );

        preparePlayer(episode);
    }

    private void preparePlayer(Episode episode) {
        MediaItem mediaItem = MediaItem.fromUri(episode.getMovie());
        player.setMediaItem(mediaItem);
    }

    @UnstableApi
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT>23) {
            initPlayer();
        }

    }

    @UnstableApi
    @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
        if (Util.SDK_INT<=23 || player==null) {
            initPlayer();
        }
    }

    private void hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().getWindow(), false);
    }

    @UnstableApi
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT<=23 ) {
            initPlayer();
        }
    }

    @UnstableApi
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT>23) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (player!=null){
            playbackPosition = player.getCurrentPosition();
            currentItem = player.getCurrentMediaItemIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
        }
        player = null;
    }
}

























