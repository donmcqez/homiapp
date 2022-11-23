package com.tikay.homitest.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MockUsers {
    private static final User user1 = new User(1, "Kwame", "kwame", "kwame@homi.com",
            "0241222222", "asdf1234", true);
    private static final User user2 = new User(2, "Alex", "tikay", "tikay@homi.com",
            "0248079201", "asdf1234", true);
    private static final User user3 = new User(3, "Sarah", "sarah", "sarah@homi.com",
            "0201234567", "asdf1234", false);
    private static final User user4 = new User(4, "Adolf", "adolf", "adolf@homi.com",
            "0273452389", "asdf1234", false);
    private static final User user5 = new User(5, "Fred", "fred", "fred@homi.com",
            "0244123456", "asdf1234", false);

    public static List<User> getUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        return userList;
    }
}
