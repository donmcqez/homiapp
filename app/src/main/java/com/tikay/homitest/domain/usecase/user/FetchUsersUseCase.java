package com.tikay.homitest.domain.usecase.user;

import android.content.Context;

import com.tikay.homitest.data.repository.UserRepositoryImpl;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

import java.util.List;

public class FetchUsersUseCase {
    UserRepository userRepository;
    public FetchUsersUseCase(Context context){
        userRepository = new UserRepositoryImpl(context);
    }

    public List<User> fetchUsers(){
        return userRepository.getUsers();
    }
}
