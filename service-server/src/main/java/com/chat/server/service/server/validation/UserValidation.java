package com.chat.server.service.server.validation;

import com.chat.server.model.user.User;
import com.chat.server.model.user.Gender;
import com.chat.server.repository.server.user.impl.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        List<User> listOfUser = userRepository.findAll();
        for (User u : listOfUser) {
            if (u.getPhone().equals(phone)) {
                return false;
            }
        }
        return true;
    }



}
