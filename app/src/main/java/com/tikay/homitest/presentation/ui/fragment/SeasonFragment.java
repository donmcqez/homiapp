package com.tikay.homitest.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.tikay.homitest.R;
import com.tikay.homitest.domain.model.Media;
import com.tikay.homitest.domain.model.Suggestion;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.presentation.adapters.SuggestionAdapter;
import com.tikay.homitest.presentation.utils.images.ImageUtils;
import com.tikay.homitest.presentation.veiwmodel.MainViewModel;
import com.tikay.homitest.presentation.veiwmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class SeasonFragment extends Fragment {

    private static final String TAG = "SeasonFragment";
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
//    @BindView(R.id.chipType)
//    Chip chipType;

    private NavController navController;

    private int mediaPosition = 0;
    private boolean isContentPremium = false;
    private boolean isUserPremium = false;
    private boolean isUserAuthenticated = false;

    private SuggestionAdapter suggestionAdapter;
    private MainViewModel mainViewModel;
    private UserViewModel userViewModel;

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
        checkAuth();
        setUpRecyclerView();
        observeSuggestionData();
    }

    private void initViewModel() {
        // I'm using requireActivity() in order to share the same viewModel with host activity
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }
    private void checkAuth() {
        userViewModel.observeUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                isUserAuthenticated = true;
                isUserPremium = user.isPremium();
            }
            Log.e(TAG, "checkAuth: =======> IS_USER_PREMIUM      : "+isUserPremium );
            Log.e(TAG, "checkAuth: =======> IS_USER_AUTHENTICATED: "+isUserAuthenticated );
        });

    }

    private void setUpRecyclerView() {
        suggestionAdapter = new SuggestionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(suggestionAdapter);

        suggestionAdapter.setOnItemClickListener((v, suggestion, position) -> {


        });
    }

    private void observeSuggestionData() {
        mainViewModel.getMediaData().observe(getViewLifecycleOwner(), mediaList -> {
            initViews(mediaList.get(mediaPosition));

            List<Suggestion> suggestionList = new ArrayList<>();
            suggestionList.addAll(mediaList.get(0).getSuggestions());
            suggestionList.addAll(mediaList.get(1).getSuggestions());

            suggestionAdapter.submitList(suggestionList);
        });
    }

    private void initViews(Media media) {
        isContentPremium = media.isPremium();

        String year = "Release year " + media.getReleased();
        String views = "Views " + media.getViews();
        String season = "Season 1";
        tvTitle.setText(media.getTitle());
        tvCategory.setText(media.getCategory());
        tvRating.setText(media.getRating());
        tvDescription.setText(media.getSynopsis());
        tvYear.setText(year);
        tvViews.setText(views);
        tvSeason.setText(season);

        if (isContentPremium) {
            chipPlay.setChipIconResource(R.drawable.ic_baseline_workspace_premium_24);
        }
        String premiumType =  "";
        if (isUserAuthenticated) {
            if (isUserPremium) {
                premiumType = "play";
            } else {
                premiumType = isContentPremium ? "rent $1" : "play";
            }

        } else {
             premiumType = isContentPremium ? "rent $1" : "play";
        }
        chipPlay.setText(premiumType);

        ImageUtils.loadImage(
                requireActivity(),
                ivBanner,
                media.getBanner()
        );
    }

    @OnClick(value = R.id.chipPlay)
    public void onIvPlayClicked() {
        if (isUserAuthenticated) {
            // show signup/login page
            if (isContentPremium) {
//                Toast.makeText(requireContext(), "premium content", Toast.LENGTH_LONG).show();
                if (isUserPremium) {
                    navigateToPlayer();
                    return;
                }
//                Utils.showSnackBar(requireView(), R.id.coordinatorLayout,"premium content");
                Snackbar snackbar = Snackbar.make(requireView(), "",Snackbar.LENGTH_LONG);
                snackbar.setText("Upgrade Account");
                snackbar.setActionTextColor(requireContext().getResources().getColor(R.color.purple));
                snackbar.setAction("OK", v -> {
                    navigateToAccountScreen();
                });
                snackbar.show();
            } else {
                Toast.makeText(requireContext(), "free content", Toast.LENGTH_LONG).show();
                navigateToPlayer();
            }
        } else {

            // navigate to player screen
            navigateToAuthScreen();
        }

    }



    private void navigateToAccountScreen() {

        SeasonFragmentDirections.ActionSeasonFragmentToAccountFragment action =
                SeasonFragmentDirections.actionSeasonFragmentToAccountFragment();
        action.setSeasonId(mediaPosition);
        navController.navigate(action);
    }

    private void navigateToAuthScreen() {
        SeasonFragmentDirections.ActionSeasonFragmentToAuthFragment action =
                SeasonFragmentDirections.actionSeasonFragmentToAuthFragment();
        action.setSeasonId(mediaPosition);
        navController.navigate(action);
    }

    private void navigateToPlayer(){
        SeasonFragmentDirections.ActionSeasonFragmentToPlayerFragment action =
                SeasonFragmentDirections.actionSeasonFragmentToPlayerFragment();
        action.setSeasonId(mediaPosition);
        navController.navigate(action);
    }


}
