package com.tikay.homitest;

import android.widget.Toast;

public interface Constants extends Config {


    default void showToast(String message) {
        Toast.makeText(HomiApp.self(), message, Toast.LENGTH_LONG).show();
    }

    default void showToast(int message) {
        Toast.makeText(HomiApp.self(), message, Toast.LENGTH_LONG).show();
    }

}
