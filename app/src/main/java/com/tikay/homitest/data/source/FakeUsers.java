package com.tikay.homitest.data.source;

import com.tikay.homitest.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUsers {
    public static List<User> getUsers(){
        User premiumUser = new User(
                1,
                "Kwame Twum",
                "kwame",
                "kwame@homi.com",
                "+233249713683",
                "12345678",
                true
        );

        User normalUser = new User(
                1,
                "John Doe",
                "john",
                "john@homi.com",
                "+124567887825",
                "12345678",
                false
        );

        List<User> userList = new ArrayList<>();
        userList.add(premiumUser);
        userList.add(normalUser);

        return userList;
    }
}
