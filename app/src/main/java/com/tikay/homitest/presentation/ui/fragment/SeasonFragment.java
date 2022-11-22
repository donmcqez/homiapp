package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.chip.Chip;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.domain.model.Suggestion;
import com.tikay.homitest.presentation.adapters.SuggestionAdapter;
import com.tikay.homitest.presentation.utils.Utils;
import com.tikay.homitest.presentation.utils.images.ImageUtils;
import com.tikay.homitest.presentation.veiwmodel.MediaViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class SeasonFragment extends Fragment {

    @BindView(R.id.rv_seasons)
    RecyclerView recyclerView;
    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.tvViews)
    TextView tvViews;
    @BindView(R.id.tvSeason)
    TextView tvSeason;
    @BindView(R.id.chipPlay)
    Chip chipPlay;

    private NavController navController;

    private int mediaPosition = 0;

    private SuggestionAdapter suggestionAdapter;
    private MediaViewModel mediaViewModel;

    public SeasonFragment() {
        super(R.layout.fragment_season);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        navController = Navigation.findNavController(view);

//        mediaPosition = SeasonFragmentArgs.fromBundle(getArguments());
        mediaPosition = getArguments() != null ? getArguments().getInt("position") : 0;

        initViewModel();
        setUpRecyclerView();
        observeSuggestionData();
    }

    private void initViewModel() {
        // I'm using requireActivity() in order to share the same viewModel with host activity
        mediaViewModel = new ViewModelProvider(requireActivity()).get(MediaViewModel.class);
    }

    private void setUpRecyclerView() {
        suggestionAdapter = new SuggestionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(suggestionAdapter);

        suggestionAdapter.setOnItemClickListener((v, suggestion, position) -> {
            SeasonFragmentDirections.ActionSeasonFragmentToEpisodeFragment action =
                    SeasonFragmentDirections.actionSeasonFragmentToEpisodeFragment();
            action.setEpisodeId(suggestion.getId());
            navController.navigate(action);

//            SeasonFragmentDirections.ActionSeasonFragmentToPlayerFragment action =
//                    SeasonFragmentDirections.actionSeasonFragmentToPlayerFragment();
//            action.setSeasonId(mediaPosition);
//            navController.navigate(action);

        });
    }

    private void observeSuggestionData() {
        mediaViewModel.getMediaData().observe(getViewLifecycleOwner(), mediaList -> {
            initViews(mediaList.get(mediaPosition));

            List<Suggestion> suggestionList = new ArrayList<>();
            suggestionList.addAll(mediaList.get(0).getSuggestions());
            suggestionList.addAll(mediaList.get(1).getSuggestions());

            suggestionAdapter.submitList(suggestionList);
        });
    }

    private void initViews(Media media) {
        String year = "Release year "+media.getReleased();
        String views = "Views "+media.getViews();
        String season = "Season 1";
        tvTitle.setText(media.getTitle());
        tvCategory.setText(media.getCategory());
        tvRating.setText(media.getRating());
        tvDescription.setText(media.getSynopsis());
        tvYear.setText(year);
        tvViews.setText(views);
        tvSeason.setText(season);

        int margin = (int) Utils.dp2px(requireActivity(),8);
        ImageUtils.showImage(
                requireActivity(),
                ivBanner,
                media.getBanner(),
                new CenterCrop(),
                new RoundedCorners(margin)
        );
    }

    @OnClick(value = R.id.chipPlay)
    public void onIvPlayClicked() {
        SeasonFragmentDirections.ActionSeasonFragmentToPlayerFragment action =
                SeasonFragmentDirections.actionSeasonFragmentToPlayerFragment();
        action.setSeasonId(mediaPosition);
        navController.navigate(action);
    }

}
