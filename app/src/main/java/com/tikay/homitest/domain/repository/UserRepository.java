package com.tikay.homitest.domain.repository;

import com.tikay.homitest.domain.model.User;

import java.util.List;

public interface UserRepository {

    public List<User> getUsers();
}
