package com.chat.server.service.server.user.impl;

import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.model.user.UserFriend;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserFriendRepository;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.user.ServerUserService;
import com.chat.server.service.server.validation.UserValidation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ServerUserServiceImpl extends UnicastRemoteObject implements ServerUserService {

    UserRepository userRepository = RepositoryServerFactory.creatUserRepository();
    UserFriendRepository userFriendRepository = RepositoryServerFactory.createUserFriendRepository();
    ServerNotificationService serverNotificationService = ServiceFactory.createServerNotificationService();
    private static ServerUserServiceImpl instance;


    private ServerUserServiceImpl() throws RemoteException {
        super(11223);

//        super(11223, new RMISSLClientSocketFactory(), new SslRMIServerSocketFactory());

//        super(0, SslClientSocketFactory.getInstance(), SslServerSocketFactory.getInstance());
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
    public User insertUser(User user, String password) {
        return userRepository.insertUser(user, password);
    }

    @Override
    public User updateUser(User user, String password) {
        return userRepository.updateUser(user, password);
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
            notification.setNotificationMessage("friend request from " + currentUser.getPhone());
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
    public int removeFriend(int currentUser, int friend) {
        int i = userFriendRepository.deleteFriend(currentUser, friend);


        return i;
    }

    @Override
    public User getUserByPhone(String phone) throws RemoteException {
        return userRepository.findUserByPhone(phone);
    }

    @Override
    public Map<String, Boolean> validateUsr(User user) throws RemoteException {
        UserValidation validator = new UserValidation(user);
        Map<String, Boolean> validate = validator.validUser(user);
        return validate;
    }

    @Override
    public int updateFriend(int userId, int friendId, FriendStatus friendStatus) throws RemoteException {
        return userFriendRepository.updateFriend(userId, friendId, friendStatus);
    }

    @Override
    public User uniquePhone(String phone) throws RemoteException {
        UserValidation userValidation = new UserValidation();
        return userValidation.uniquePhone(phone);
    }

    @Override
    public UserFriend getStatus(int currentUser, int friend) throws RemoteException {
        return userFriendRepository.getUserStatus(currentUser, friend);
    }

    @Override
    public User updateUserMode(User user, Mode mode) {

        User updatedUser = userRepository.updateUserMode(user, mode);
        new Thread(() -> {
            updatedUser.getFriends().parallelStream()
                    .forEach((friend) -> {
                        try {
                            Notification notification = serverNotificationService.createChangeModeNotification(user, mode, friend);
                            serverNotificationService.sendNotification(notification);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    });
        }).start();
        return updatedUser;
    }

    @Override
    public void registerServerStatistics(NotificationServiceCallback notificationServiceCallback) throws RemoteException {
        serverNotificationService.register(notificationServiceCallback);
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