package com.tikay.homitest.presentation.ui.fragment;

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
import com.tikay.homitest.presentation.veiwmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class AccountFragment extends Fragment {
    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    private static final String TAG = AccountFragment.class.getSimpleName();
    private int seasonId;
    private UserViewModel userViewModel;
    private NavController navController;

    @BindView(R.id.chipUpgrade)
    Chip chipUpgrade;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        navController = Navigation.findNavController(view);

//        mediaPosition = SeasonFragmentArgs.fromBundle(getArguments());
        seasonId = getArguments() != null ? getArguments().getInt("position") : 0;

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
                if (user.isPremium())
                    chipUpgrade.setVisibility(View.GONE);
            }
        });

    }

    @OnClick(R.id.chipUpgrade)
    public void upgradeAccount() {
        userViewModel.upgradeUser(true);

        AccountFragmentDirections.ActionAccountFragmentToPlayerFragment acttion =
                AccountFragmentDirections.actionAccountFragmentToPlayerFragment();

        navController.navigate(acttion);
    }

    @OnClick(R.id.chipUpgrade)
    public void signOut() {
        userViewModel.signOut();
    }


}
