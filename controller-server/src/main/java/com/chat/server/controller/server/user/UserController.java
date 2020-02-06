package com.chat.server.controller.server.user;

import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.util.List;

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
}
