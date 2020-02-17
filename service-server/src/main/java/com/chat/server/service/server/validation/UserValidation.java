package com.chat.server.service.server.validation;

import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.util.List;

public class UserValidation {

    User user = null;

    public UserValidation(User user) {

        this.user = user;
    }

    public boolean validName(String name) {

        return name.matches("[a-zA-z]+");
    }

    public boolean validMail(String mail) {
        return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public boolean validPhone(String phone) {

        return phone.matches("^(?:\\+?2)?01[0-9]\\d{9}$");
    }

    public boolean gender(Gender gender) {
        return gender != null;
    }

    public boolean uniquePhone(String phone) {
        try {
            ServerUserService serverUserService = ServiceFactory.createServerUserService();
            List<User> listOfUser = serverUserService.getAllUsers();
            for (User u : listOfUser) {
                if (u.getPhone().equals(phone)) {
                    return false;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }


}
