package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

public class SignOutUseCase {
    UserRepository userRepository;
    public SignOutUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
    }

    public void signOut(){
        userRepository.signOut();
    }
}
