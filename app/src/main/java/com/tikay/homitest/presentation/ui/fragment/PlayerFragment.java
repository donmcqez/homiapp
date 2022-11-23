package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
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
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Episode;
import com.tikay.homitest.domain.model.Suggestion;
import com.tikay.homitest.presentation.adapters.PlayListAdapter;
import com.tikay.homitest.presentation.adapters.SuggestionAdapter;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;
import com.tikay.homitest.presentation.veiwmodel.MediaViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PlayerFragment extends Fragment {
    private static final String TAG = "PlayerFragment";

    public PlayerFragment() {
        super(R.layout.fragment_player);
    }

    @BindView(R.id.rv_episode)
    RecyclerView rvEpisodes;
    @BindView(R.id.rv_suggestion)
    RecyclerView rvSuggestions;
    @BindView(R.id.clSuggestion)
    ConstraintLayout clSuggestion;

    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.video_view)
    PlayerView playerView;
    @BindView(R.id.cipLoading)
    CircularProgressIndicator circularProgressIndicator;

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
    private int episodesSize = 0;
    private List<Suggestion> suggestionList;
    private PlayListAdapter playListAdapter;
    private SuggestionAdapter suggestionAdapter;
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
        observeData();
    }

    private void initViewModel() {
// I'm using requireActivity() in order to share the same viewModel with host activity
        mediaViewModel = new ViewModelProvider(requireActivity()).get(MediaViewModel.class);
    }

    private void setUpRecyclerView() {
        playListAdapter = new PlayListAdapter();
        rvEpisodes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvEpisodes.setAdapter(playListAdapter);

        playListAdapter.setOnItemClickListener((v, episode, position) -> {
            initViews(playListAdapter.getCurrentList().get(position));
            player.seekTo(position, C.INDEX_UNSET);
            player.play();
        });

        suggestionAdapter = new SuggestionAdapter();
        rvSuggestions.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvSuggestions.setAdapter(suggestionAdapter);
        suggestionAdapter.setOnItemClickListener((v, suggestion, position) -> {
            Utils.showShortToast(requireContext(), suggestion.getTitle() + " clicked");
        });
    }

    private void observeData() {
        mediaViewModel.getMediaData().observe(getViewLifecycleOwner(), mediaList -> {
            suggestionList = new ArrayList<>();
            suggestionList.addAll(mediaList.get(0).getSuggestions());
            List<Episode> episodeList = mediaList.get(0).getSeasons().getSeason1();
            episodesSize = episodeList.size();
            initViews(episodeList.get(0));
            playListAdapter.submitList(episodeList);
            preparePlayer(episodeList);
        });
    }

    private void initPlayer() {
        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);
        player.setAudioAttributes(AudioAttributes.DEFAULT, true);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentItem, playbackPosition);
    }

    private void initViews(Episode episode) {
        String index = episode.getId() + "/" + episodesSize;
        tvTitle.setText(episode.getTitle());
        tvEpisodeIndex.setText(index);

        int margin = (int) Utils.dp2px(requireActivity(), 8);
        ImageUtils.showImage(
                requireActivity(),
                ivBanner,
                episode.getBanner(),
                new CenterCrop(),
                new RoundedCorners(margin)
        );
    }


    @UnstableApi
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initPlayer();
        }
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @UnstableApi
    @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
        if (Util.SDK_INT <= 23 || player == null) {
            initPlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }

    }

    @UnstableApi
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @UnstableApi
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    private void preparePlayer(List<Episode> episodeList) {
        List<MediaItem> mediaItemList = new ArrayList<>();
        for (Episode episode : episodeList) {
            MediaItem mediaItem = MediaItem.fromUri(episode.getMovie());
//            MediaItem mediaItem = new MediaItem.Builder().setUri(episode.getMovie())
//                    .setMediaId(episode.getTitle())
//                    .setTag(episode)
//                    .build();
            mediaItemList.add(mediaItem);
        }
        player.setMediaItems(mediaItemList);
        player.prepare();

        player.addListener(playerListener);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentItem = player.getCurrentMediaItemIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
        }
        player = null;
    }

    Player.Listener playerListener = new Player.Listener() {
        @Override
        public void onEvents(Player player2, Player.Events events) {
            if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)
                    || events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)) {
//                uiModule.updateUi(player);
            }

        }

        @Override
        public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
            try {
                int currentIndex = player.getCurrentMediaItemIndex();
                Episode episode = playListAdapter.getCurrentList().get(currentIndex);
                initViews(episode);
                playListAdapter.setSelectedPosition(currentIndex);

                Log.e(TAG, "onMediaItemTransition: INDEX =========> " + currentIndex);
            } catch (Exception e) {
                Log.e(TAG, "onMediaItemTransition: ERROR: =========> ", e);
            }
        }

        @Override
        public void onPlaybackStateChanged(int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE: {
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_IDLE");
                }
                case Player.STATE_BUFFERING: {
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_BUFFERING");
                    circularProgressIndicator.setVisibility(View.VISIBLE);
//                    circularProgressIndicator.show();
                }
                case Player.STATE_READY: {
                    ivBanner.setVisibility(View.GONE);
                    circularProgressIndicator.setVisibility(View.GONE);
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_READY");
                }
                case Player.STATE_ENDED: {
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_ENDED");

                    int currentIndex = player.getCurrentMediaItemIndex();
                    if (currentIndex == playListAdapter.getItemCount() - 1) {
                        Log.e(TAG, "onPlaybackStateChanged: =========> PLAYLIST HAS ENDED");
                        clSuggestion.setVisibility(View.VISIBLE);
                        // suggestion adapter
                        suggestionAdapter.submitList(suggestionList);
                    } else {
                        clSuggestion.setVisibility(View.GONE);
                    }

                }
                default: {
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_ENDED");
                }
            }
        }

        @Override
        public void onIsLoadingChanged(boolean isLoading) {
            Log.e(TAG, "onIsLoadingChanged: =========>  " + isLoading);

        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Log.e(TAG, "onIsLoadingChanged: =========>  " + isPlaying);
            if (isPlaying) {
                // Active playback.
            } else {
                // Not playing because playback is paused, ended, suppressed, or the player
                // is buffering, stopped or failed. Check player.getPlayWhenReady,
                // player.getPlaybackState, player.getPlaybackSuppressionReason and
                // player.getPlaybackError for details.
            }
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {

        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

        }

        @Override
        public void onPlayerError(PlaybackException error) {

        }

        @Override
        public void onPlayerErrorChanged(@Nullable PlaybackException error) {

        }
    };
}

























