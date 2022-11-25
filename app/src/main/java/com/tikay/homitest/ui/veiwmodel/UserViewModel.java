package com.tikay.homitest.ui.veiwmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;
import com.tikay.homitest.domain.usecase.user.UserUseCases;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    UserUseCases userUseCases;

    public UserViewModel(Application application) {
        super(application);
        userUseCases = new UserUseCases(application);
    }

    private MutableLiveData<User> userLiveData;

    public LiveData<User> observeUser(){
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            updateUserState();
        }
        return userLiveData;
    }

    public User login(String email, String password) {
        User user = userUseCases.loginUseCase.login(email,password);
        updateUserState();
        return user;
    }

    public void signOut(){
        userUseCases.signOutUseCase.signOut();
        updateUserState();
    }


    public void upgradeUser(boolean isPremium) {
        userUseCases.upgradeUserUseCase.upgradeUser(isPremium);
        updateUserState();
    }

    private void updateUserState() {
        userLiveData.setValue( userUseCases.getAuthUserUseCase.getAuthUser());
    }
}
