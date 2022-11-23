package com.tikay.homitest.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tikay.homitest.R;
import com.tikay.homitest.data.remote.service.ApiService;
import com.tikay.homitest.data.remote.service.HomiRemoteService;
import com.tikay.homitest.data.repository.MediaRepositoryImpl;
import com.tikay.homitest.domain.repository.MediaRepository;
import com.tikay.homitest.presentation.veiwmodel.MediaViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NonConstantResourceId")
public class HomeActivity extends AppCompatActivity{

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        transparentStatusBar();

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.navHostFragmentContainer) ;
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
//        NavController navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        MediaViewModel mediaViewModel = new ViewModelProvider(this).get(MediaViewModel.class);
        HomiRemoteService homiRemoteService = ApiService.getAPIService();
        MediaRepository mediaRepository = new MediaRepositoryImpl(homiRemoteService);
        mediaViewModel.setMediaRepository(mediaRepository);

        navController.addOnDestinationChangedListener((navController1, destination, bundle) -> {
            if (destination.getId() == R.id.homeFragment||
                    destination.getId() == R.id.categoriesFragment||
                    destination.getId() == R.id.menuFragment) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });
    }

    private void transparentStatusBar() {
        Window window = getWindow();
        new WindowInsetsControllerCompat(window,  window.getDecorView()).setAppearanceLightStatusBars(true);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.white));
    }


}
