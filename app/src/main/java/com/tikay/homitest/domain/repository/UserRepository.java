package com.tikay.homitest.domain.repository;

import com.tikay.homitest.domain.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    User getUser();
    void saveUser(User user);
    void updateUser(boolean isPremium);
    void signOut();
}
