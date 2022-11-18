package com.tikay.homitest.presentation.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tikay.homitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity{

//    @BindView(R.id.flFragment)
//    FrameLayout flFragment;
//    @BindView(R.id.navHostFragment)
//    NavHostFragment navHostFragment;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
//        toolbar = findViewById(R.id.toolbar);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setSupportActionBar(toolbar);

//        NavHostFragment navHostFragment = (NavHostFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navHostFragmentContainer) ;
//        NavController navController = navHostFragment.getNavController();
        NavController navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }


}
