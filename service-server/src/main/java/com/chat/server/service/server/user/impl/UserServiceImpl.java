package com.chat.server.service.server.user.impl;

import com.chat.server.model.user.User;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.service.server.user.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserRepository userRepository = RepositoryServerFactory.creatUserRepository();

    @Override

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public User getByPhoneAndPassword(String phone, String password) {
        return null;
    }

    @Override
    public User getByPhone(String phone) {
        return null;
    }

    @Override
    public List<User> getUserFriends(User user) {
        return null;
    }

    @Override
    public int insertUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(int id) {
        return 0;
    }

    @Override
    public List<User> getOnlineUsers(boolean online) {
        return userRepository.findIfOnline(online);
    }
}
