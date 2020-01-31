package com.chat.server.repository.server;

import com.chat.server.model.user.User;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //-------------------------------
        // @noura
        //todo test all user repo methods
        UserRepository userRepository = RepositoryServerFactory.creatUserRepository();
        List<User> all = userRepository.findAll();
        System.out.println(all.size());

        //-------------------------------
        //@shaheen
        //todo test all chat repo methods


    }
}
