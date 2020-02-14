package com.chat.server.service.server.user.impl;

import com.chat.server.model.user.User;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerUserServiceImpl extends UnicastRemoteObject implements ServerUserService {
    UserRepository userRepository = RepositoryServerFactory.creatUserRepository();

    public ServerUserServiceImpl() throws RemoteException {
    }

    @Override

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id, boolean fullData) {
        return userRepository.findById(id, fullData);
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
    public User insertUser(User user) {
        return userRepository.insertUser(user);
    }

    @Override
    public User updateUser(User user) {
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
