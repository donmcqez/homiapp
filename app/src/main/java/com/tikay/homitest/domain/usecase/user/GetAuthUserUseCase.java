package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

public class GetAuthUserUseCase {
    UserRepository userRepository;
    public GetAuthUserUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
    }

    public User getAuthUser(){
        return userRepository.getUser();
    }
}
