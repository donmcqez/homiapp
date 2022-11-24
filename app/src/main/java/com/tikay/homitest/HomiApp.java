package com.tikay.homitest;

import android.app.Application;

import com.tikay.homitest.presentation.utils.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class HomiApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build());

    }
}

