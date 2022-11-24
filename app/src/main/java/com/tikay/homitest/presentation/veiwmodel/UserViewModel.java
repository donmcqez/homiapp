package com.tikay.homitest.presentation.veiwmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepositoryImpl(application);
    }

    //this is the data that we will fetch asynchronously
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<List<User>> userListLiveData;

    public LiveData<User> observeUser(){
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            loadAuthUser();
        }
        return userLiveData;
    }

    public LiveData<List<User>> getUserData() {
        //if the list is null initialize
        if (userListLiveData == null) {
            userListLiveData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadUserList();
        }
        //finally we will return the list
        return userListLiveData;
    }

    private void loadAuthUser() {
        userLiveData.setValue(userRepository.getUser());
    }

    public User login(String email, String password) {

        for (User user : userRepository.getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                userRepository.saveUser(user);
                loadAuthUser();
                return user;
            }
        }
        return null;
    }

    public void signOut(){
        userRepository.signOut();
        loadAuthUser();
    }

    private void loadUserList() {
        userListLiveData.setValue(userRepository.getUsers());
    }


    public void upgradeUser(boolean isPremium) {
        userRepository.updateUser(isPremium);
        loadAuthUser();
    }
}
