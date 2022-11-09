package com.tikay.homitest.data.repository;

import com.tikay.homitest.data.source.UserDataSource;
import com.tikay.homitest.domain.model.User;
import com.tikay.homitest.domain.repository.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    UserDataSource userDataSource;

    public UserRepositoryImpl(
            UserDataSource userDataSource
    ){
        this.userDataSource = userDataSource;
    }

    @Override
    public List<User> getUsers() {
        return userDataSource.getUsers();
    }
}
