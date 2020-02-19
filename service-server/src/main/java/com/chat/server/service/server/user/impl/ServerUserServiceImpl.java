package com.chat.server.service.server.user.impl;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserFriendRepository;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerUserServiceImpl extends UnicastRemoteObject implements ServerUserService {

    UserRepository userRepository = RepositoryServerFactory.creatUserRepository();
    UserFriendRepository userFriendRepository = RepositoryServerFactory.createUserFriendRepository();
    ServerNotificationService serverNotificationService = ServiceFactory.createServerNotificationService();
    private static ServerUserServiceImpl instance;


    private ServerUserServiceImpl() throws RemoteException {
        super(11223);
        System.out.println("creat service");
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
    public List<User> getByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public List<User> getUserFriends(User user, FriendStatus friendStatus) {

        return userRepository.findAllUserFriends(user.getId(), friendStatus);
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

    @Override
    public int addFriend(User currentUser, User friend) {

        int i = userFriendRepository.addNewFriend(currentUser.getId(), friend.getId(), FriendStatus.PENDING);
        if (i > 0) {
            Notification notification = new Notification();
            notification.setNotificationMessage("you have a new friend request from user " + currentUser.getPhone());
            notification.setNotificationType(NotificationType.FRIEND_REQUEST);
            notification.setUserFrom(currentUser);
            notification.setUserTo(friend);
            try {
                serverNotificationService.sendNotification(notification);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return i;

    }

    @Override
    public User getUserByPhone(String phone) throws RemoteException {
        return userRepository.findUserByPhone(phone);
    }

    public synchronized static ServerUserServiceImpl getInstance() {
        if (instance == null) {
            try {
                instance = new ServerUserServiceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
