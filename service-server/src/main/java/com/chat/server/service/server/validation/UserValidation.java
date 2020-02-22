package com.chat.server.service.server.validation;

import com.chat.server.model.user.Gender;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean validName(String name) {

        return name.matches("[a-zA-z]+");
    }

    public boolean validMail(String mail) {
        return mail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public boolean validPhone(String phone) {

        return phone.matches("^(?:\\+?2)?01[0-9]{9}$");
    }

    public boolean gender(Gender gender) {
        return gender != null;
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

    public boolean validCountry(String country) {
        return !country.equals("");
    }

    public boolean validPassword(String password) {
        return password.matches("^[a-zA-Z!@#$%-^&?_0-9]{8,40}$");
    }

    public Map<String, Boolean> validUser(User user) {
        Map<String, Boolean> validUser = new HashMap<>();

        validUser.put("InvalidFirstName", validName(user.getFirstName()));
        validUser.put("InvalidLastName", validName(user.getLastName()));
        validUser.put("InvalidPhone", validPhone(user.getPhone()));
        validUser.put("InvalidPassword", validPassword(user.getPassword()));
        validUser.put("InvalidEmail", validMail(user.getEmail()));
        validUser.put("InvalidCountry", validCountry(user.getCountry()));
        validUser.put("InvalidGender", gender(user.getGender()));

        return validUser;
    }

}
