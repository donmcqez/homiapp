package com.tikay.homitest.data.source;

import com.tikay.homitest.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockUsers {
    public static List<User> getUsers() {
        User user1 = new User(
                1,
                "Kwame Twum",
                "kwame",
                "kwame@homi.com",
                "+233249713683",
                "asdf1234",
                true
        );

        User user2 = new User(
                2,
                "Alex",
                "tikay",
                "tikay@homi.com",
                "+124567887825",
                "asdf1234",
                true
        );

        User user3 = new User(
                3,
                "Sarah",
                "sarah",
                "sarah@homi.com",
                "+233201234567",
                "asdf1234",
                false
        );

        User user4 = new User(
                4, "Adolf",
                "adolf",
                "adolf@homi.com",
                "+233273452389",
                "asdf1234",
                false
        );
        final User user5 = new User(
                5, "Fred",
                "fred",
                "fred@homi.com",
                "+233244123456",
                "asdf1234",
                false
        );

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        return userList;
    }
}
