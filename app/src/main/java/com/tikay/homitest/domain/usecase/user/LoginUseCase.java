package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

import java.util.List;

public class LoginUseCase {
    UserRepository userRepository;
    SaveUserUseCase saveUserUseCase;
    public LoginUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
        saveUserUseCase = new SaveUserUseCase(context);
    }

    public User login(String email, String password){
        for (User user : userRepository.getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                saveUserUseCase.saveUser(user);
                return user;
            }
        }
        return null;
    }
}
