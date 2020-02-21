package com.chat.server.controller.server.user;

import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    private ServerUserService service = ServiceFactory.createServerUserService();

    public UserController() throws RemoteException {

    }

    public List<User> getAllOnlineUsers(boolean online) {
        try {
            return service.getOnlineUsers(online);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        try {
            return service.getAllUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteUser(User user) {
        try {
            return service.deleteUser(user.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public User insertUser(User user) {
        try {
            return service.insertUser(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Map<String, Boolean> validateUser(User user) {
        Map<String, Boolean> validateUserData = new HashMap<>();
        try {
            validateUserData = service.validateUsr(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return validateUserData;
    }


    public User updateUser(User user) {
        try {
            return service.updateUser(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
