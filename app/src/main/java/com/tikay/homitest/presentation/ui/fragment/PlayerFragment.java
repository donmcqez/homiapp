package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.ui.PlayerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Episode;
import com.tikay.homitest.domain.model.Suggestion;
import com.tikay.homitest.presentation.adapters.PlayListAdapter;
import com.tikay.homitest.presentation.adapters.SuggestionAdapter;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;
import com.tikay.homitest.presentation.veiwmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
@OptIn(markerClass = UnstableApi.class)
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
    @BindView(R.id.pbLoading)
    ProgressBar progressBar;

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
    private MainViewModel mainViewModel;


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
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
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
        mainViewModel.observeSeriesData().observe(getViewLifecycleOwner(), mediaList -> {
            suggestionList = new ArrayList<>();
            suggestionList.addAll(mediaList.get(0).getSuggestions());
            List<Episode> episodeList = mediaList.get(0).getSeasons().getSeason1();
            episodesSize = episodeList.size();
            initViews(episodeList.get(0));
            playListAdapter.submitList(episodeList);
            preparePlayer(episodeList);
        });
    }

    private void initViews(Episode episode) {
        String index = episode.getId() + "/" + episodesSize;
        tvTitle.setText(episode.getTitle());
        tvEpisodeIndex.setText(index);
        int margin = (int) Utils.dp2px(requireActivity(), 8);
        ImageUtils.loadImage(
                requireActivity(),
                ivBanner,
                episode.getBanner()
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

    @OptIn(markerClass = UnstableApi.class)
    private void initPlayer() {
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(requireContext());
        trackSelector.buildUponParameters().setMaxVideoSizeSd();

        player = new ExoPlayer.Builder(requireContext())
                .setTrackSelector(trackSelector)
                .build();
        player.setAudioAttributes(AudioAttributes.DEFAULT, true);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentItem, playbackPosition);
        playerView.setPlayer(player);
        player.addListener(playerListener);
//        player.addAnalyticsListener(new EventLogger());
    }

    private void preparePlayer(List<Episode> episodeList) {
        List<MediaItem> mediaItemList = new ArrayList<>();
        for (Episode episode : episodeList) {
//            MediaItem mediaItem = MediaItem.fromUri(episode.getMovie());
            MediaItem mediaItem = new MediaItem.Builder()
                    .setUri(episode.getMovie())
                    .setMimeType(MimeTypes.APPLICATION_MP4)
                    .setMediaId(episode.getTitle())
                    .setTag(episode.getId())
                    .build();

            mediaItemList.add(mediaItem);
        }
        player.setMediaItems(mediaItemList);
        player.prepare();
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
//                    progressBar.setVisibility(View.VISIBLE);
                }
                case Player.STATE_READY: {
                    ivBanner.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_READY");
                }
                case Player.STATE_ENDED: {
                    Log.e(TAG, "onPlaybackStateChanged: =========> STATE_ENDED");
                    int currentIndex = player.getCurrentMediaItemIndex();

                    if (currentIndex == player.getMediaItemCount() - 1) {
                        Log.e(TAG, "onPlaybackStateChanged: =========> LAST ITEM PLAYING");
                        long duration = player.getDuration();
                        long currentPosition = player.getCurrentPosition();
//                        player.getCurrentMediaItem()
//                        Log.e(TAG, "onPlaybackStateChanged: =========> DURATION: "+duration/1000+"s");
                        Log.e(TAG, "onPlaybackStateChanged: =========> DURATION: " + duration);
                        Log.e(TAG, "onPlaybackStateChanged: =========> CURRENT POSITION: " + currentPosition);

                        if (duration > 0 && currentPosition >= duration) {
//                            clSuggestion.setVisibility(View.GONE);
                            Log.e(TAG, "onPlaybackStateChanged: =========> SHOW SUGGESTIONS");
                            playerView.hideController();
                            progressBar.setVisibility(View.GONE);
                            suggestionAdapter.submitList(suggestionList);
                            clSuggestion.setVisibility(View.VISIBLE);
                        }

                    } else {
                        // reset to gone if previous media item is selected
                        clSuggestion.setVisibility(View.GONE);
                    }

                }
                default: {
                    Log.e(TAG, "onPlaybackStateChanged: =========> UNKNOWN STATE");
                }
            }
        }

        @Override
        public void onIsLoadingChanged(boolean isLoading) {
            Log.e(TAG, "onIsLoadingChanged: =========>  IS_LOADING: " + isLoading);
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Log.e(TAG, "onIsPlayingChanged: =========>  IS_PLAYING: " + isPlaying);
            if (isPlaying) {
                progressBar.setVisibility(View.GONE);
                // Active playback.
            } else {
                progressBar.setVisibility(View.VISIBLE);
                // Not playing because playback is paused, ended, suppressed, or the player
                // is buffering, stopped or failed. Check player.getPlayWhenReady,
                // player.getPlaybackState, player.getPlaybackSuppressionReason and
                // player.getPlaybackError for details.
            }
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            Log.e(TAG, "onPlayWhenReadyChanged: =========> " + reason);
        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
            Log.e(TAG, "onPlaybackSuppressionReasonChanged: =========> " + playbackSuppressionReason);
        }

        @Override
        public void onPlayerError(PlaybackException error) {
            Log.e(TAG, "onPlayerError: =========> ", error);
            // prepare the player and try to play again
            player.prepare();
            player.play();
        }

        @Override
        public void onPlayerErrorChanged(@Nullable PlaybackException error) {
            Log.e(TAG, "onPlayerErrorChanged: =========> ", error);
        }
    };
}

























