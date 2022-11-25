package com.tikay.homitest.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.tikay.homitest.R;
import com.tikay.homitest.ui.veiwmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class AccountFragment extends Fragment {
    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    private static final String TAG = AccountFragment.class.getSimpleName();
    private int seasonId = -1;
    private UserViewModel userViewModel;
    private NavController navController;

    @BindView(R.id.chipUpgrade)
    Chip chipUpgrade;
    @BindView(R.id.chipLogin)
    Chip chipLogin;
    @BindView(R.id.chipDeleteAuth)
    Chip chipDeleteAuth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        navController = Navigation.findNavController(view);

//        mediaPosition = SeasonFragmentArgs.fromBundle(getArguments());
        seasonId = getArguments() != null ? getArguments().getInt("seriesId") : 0;

        initViewModel();
        observeAuth();
    }

    private void initViewModel() {
        // I'm using requireActivity() in order to share the same viewModel with host activity
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void observeAuth() {
        userViewModel.observeUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                if (user.isPremium() && seasonId >= 0)
                    chipUpgrade.setVisibility(View.INVISIBLE);

                chipLogin.setVisibility(View.INVISIBLE);
                chipDeleteAuth.setVisibility(View.INVISIBLE);
            } else {
                chipUpgrade.setVisibility(View.INVISIBLE);
                chipDeleteAuth.setVisibility(View.INVISIBLE);
                chipLogin.setVisibility(View.VISIBLE);
            }
        });

    }

    @OnClick(R.id.chipUpgrade)
    public void upgradeAccount() {
        userViewModel.upgradeUser(true);

//        AccountFragmentDirections.ActionAccountFragmentToPlayerFragment action =
//                AccountFragmentDirections.actionAccountFragmentToPlayerFragment();
//        navController.navigate(action);
        navController.popBackStack();
    }

    @OnClick(R.id.chipDeleteAuth)
    public void signOut() {
        userViewModel.signOut();

//        navController.navigate(AccountFragmentDirections.actionAccountFragmentToSeriesFragment());
//        NavOptions navOptions = new NavOptions.Builder()
//                .setPopUpTo(R.id.seriesFragment,false)
//                .build();
//        navController.navigate(R.id.authFragment,null,navOptions);

        navController.popBackStack();
    }


}
