package com.chat.server.service.server.validation;

import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.util.List;

public class UserValidation {

    User user = null;
    ServerUserService serverUserService;


    public UserValidation(User user) {
        try {
            serverUserService = ServiceFactory.createServerUserService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.user = user;
    }

    public boolean reservedphone(String phone) {
        try {
            User user = serverUserService.getUserByPhone(phone);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return user != null ? true : false;
    }

    public boolean uniquePhone(String phone) {
        try {

            List<User> listOfUser = serverUserService.getAllUsers();
            for (User user : listOfUser) {
                if (user.getPhone().equals(phone)) {
                    return false;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }


}
