package com.tikay.homitest.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tikay.homitest.R;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        },2*1000);



    }
}
