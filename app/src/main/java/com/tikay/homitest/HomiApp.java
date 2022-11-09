package com.tikay.homitest;

import android.app.Application;


import com.tikay.homitest.features.home.utils.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class HomiApp extends Application implements Constants {
    private static HomiApp self;

    public static HomiApp self() {
        return self;
    }

//    private ApiHelper apiHelper;

    private int marginThree;
    private int padding;
    private int margin;
    private int round;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build());

//        apiHelper = new AppApiHelper();

        marginThree = (int) Utils.dp2px(4);
        padding = (int) Utils.dp2px(16);
        margin = (int) Utils.dp2px(8);
        round = margin;
    }

//    public ApiHelper getApiHelper() {
//        return apiHelper;
//    }

    public int getMarginThree() {
        return marginThree;
    }

    public int getPadding() {
        return padding;
    }

    public int getMargin() {
        return margin;
    }

    public int getRound() {
        return round;
    }
}
