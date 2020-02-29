package com.chat.server.controller.server.user;

import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements NotificationServiceCallback, UserControllerIntf {

    private ServerUserService service = ServiceFactory.createServerUserService();
    private UserControllerIntf userControllerIntf;


    public UserController() throws RemoteException {
        service.registerServerStatistics(this);
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
            return service.insertUser(user, user.getPassword());
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


    public User updateUser(User user, String password) {
        try {
            return service.updateUser(user, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void receiveNotification(Notification notification) throws RemoteException {

    }

    @Override
    public int getUserId() throws RemoteException {
        return 1;
    }

    @Override
    public void changeFriendsStatus(User user) throws RemoteException {
        userChangedHisMode(user, user.getMode());
    }

    @Override
    public void showOfflineFriends(User user) throws RemoteException {

    }

    @Override
    public void userChangedHisMode(User user, Mode mode) {
        if (userControllerIntf != null) {
            userControllerIntf.userChangedHisMode(user, mode);
        }
    }

    public void setUserControllerIntf(UserControllerIntf userControllerIntf) {
        this.userControllerIntf = userControllerIntf;
    }
}
