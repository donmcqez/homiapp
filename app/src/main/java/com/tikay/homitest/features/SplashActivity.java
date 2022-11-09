package com.tikay.homitest.features;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tikay.homitest.R;
import com.tikay.homitest.features.dashboard.DashBoardActivity;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
            startActivity(intent);
            finish();
        },3*1000);



    }
}
