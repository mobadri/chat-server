package com.chat.server.service.server.user.impl;

import com.chat.server.model.user.User;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.service.server.user.UserService;
import com.chat.server.service.server.validation.UserValidation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    UserRepository userRepository = RepositoryServerFactory.creatUserRepository();

    public UserServiceImpl() throws RemoteException {
    }

    @Override

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {

        return userRepository.findById(id);
    }

    @Override
    public User getByPhoneAndPassword(String phone, String password) {

        return userRepository.findByPhoneAndPassword(phone, password);
    }

    @Override
    public User getByPhone(String phone) {

        return userRepository.findByPhone(phone);
    }

    @Override
    public List<User> getUserFriends(User user) {

        return userRepository.findAllUserFriends(user);
    }

    @Override
    public int insertUser(User user) {

        return userRepository.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {

        return userRepository.delete(id);
    }

    @Override
    public List<User> getOnlineUsers(boolean online) {
        return userRepository.findIfOnline(online);
    }
}