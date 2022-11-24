package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.repository.UserRepository;

public class UpgradeUserUseCase {
    UserRepository userRepository;
    public UpgradeUserUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
    }

    public void upgradeUser(boolean isPremium){
         userRepository.updateUser(isPremium);
    }
}
