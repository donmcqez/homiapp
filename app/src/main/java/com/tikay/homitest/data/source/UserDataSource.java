package com.tikay.homitest.data.source;

import com.tikay.homitest.domain.model.User;

import java.util.List;

public class UserDataSource {

    public UserDataSource(){}

    public List<User> getUsers() {
        return FakeUsers.getUsers();

    }
}
