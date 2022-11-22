package com.tikay.homitest.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tikay.homitest.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NonConstantResourceId")
public class PlayerActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        ButterKnife.bind(this);

        transparentStatusBar();
    }

    private void transparentStatusBar() {
        Window window = getWindow();
        new WindowInsetsControllerCompat(window,  window.getDecorView()).setAppearanceLightStatusBars(true);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.white));
    }

}
