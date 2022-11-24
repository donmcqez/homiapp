package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

public class SaveUserUseCase {
    UserRepository userRepository;
    public SaveUserUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
    }

    public void saveUser(User user){
         userRepository.saveUser(user);
    }
}
