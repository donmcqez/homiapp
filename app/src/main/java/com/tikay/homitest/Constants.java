package com.tikay.homitest;

import android.content.Context;
import android.widget.Toast;

public interface Constants extends Config {

    default void showToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    default void showToast(Context context,int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
