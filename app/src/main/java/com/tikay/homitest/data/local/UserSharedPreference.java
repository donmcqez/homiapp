package com.tikay.homitest.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.tikay.homitest.domain.model.User;

public class UserSharedPreference {
    private static SharedPreferences sharedPreferences;
//    private Context context;

    public UserSharedPreference(Context context) {
//        this.context = context;
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getUserName());
        editor.putString(KEY_USER_FULL_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putString(KEY_USER_PASSWORD, user.getPassword());
        editor.putBoolean(KEY_USER_IS_PREMIUM, user.isPremium());
        editor.apply();
    }

    public void updateUser(boolean isPremium) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_USER_IS_PREMIUM, isPremium).apply();
    }

    public void clearUserData() {
        sharedPreferences.edit().clear().apply();
    }

    public User getUser() {
        User user = new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_FULL_NAME, ""),
                sharedPreferences.getString(KEY_USER_NAME, ""),
                sharedPreferences.getString(KEY_USER_EMAIL, ""),
                sharedPreferences.getString(KEY_USER_PHONE, ""),
                sharedPreferences.getString(KEY_USER_PASSWORD, ""),
                sharedPreferences.getBoolean(KEY_USER_IS_PREMIUM, false)

        );
        if (user.getId() > 0)
            return user;
        else
            return null;
    }

    private final String SHARED_PREF_NAME = "user_pref";

    private final String KEY_USER_NAME = "key_user_name";
    private final String KEY_USER_EMAIL = "key_email";
    private final String KEY_USER_FULL_NAME = "key_name";
    private final String KEY_USER_PHONE = "key_phone";
    private final String KEY_USER_PASSWORD = "key_password";
    private final String KEY_USER_ID = "key_id";
    private final String KEY_USER_IS_PREMIUM = "key_premium";


}
