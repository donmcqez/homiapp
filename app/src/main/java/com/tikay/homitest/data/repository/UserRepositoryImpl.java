package com.tikay.homitest.data.repository;

import android.content.Context;

import com.tikay.homitest.data.local.UserSharedPreference;
import com.tikay.homitest.data.source.UserDataSource;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    UserDataSource userDataSource;
    Context context;
    UserSharedPreference preference;

    public UserRepositoryImpl(Context context){
        this.context = context;
        this.userDataSource = new UserDataSource();
        preference = new UserSharedPreference(context);
    }

    @Override
    public User getUser() {
        return preference.getUser();
    }

    @Override
    public void saveUser(User user) {
        preference.saveUser(user);
    }

    @Override
    public void updateUser(boolean isPremium) {
        preference.updateUser(isPremium);
    }

    @Override
    public void signOut() {
        preference.clearUserData();
    }

    @Override
    public List<User> getUsers() {
        return userDataSource.getUsers();
    }
}
