package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

public class UpgradeUserUseCase {
    UserRepository userRepository;
    public UpgradeUserUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
    }

    public void getAuthUser(boolean isPremium){
         userRepository.updateUser(isPremium);
    }
}
