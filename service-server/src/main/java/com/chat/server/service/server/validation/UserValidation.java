package com.chat.server.service.server.validation;

import com.chat.server.model.user.User;

public class UserValidation {

    User user = null;

    public UserValidation() {

        this.user = user;
    }

    public boolean validName(String name) {

        return name.matches("[a-zA-z]*");
    }

    public boolean validMail(String mail) {
        return mail.matches("[a-zA-z0-9. _]*@[A-Za-z]*.[A-Za-z]*");
    }

    public boolean validPhone(String phone) {

        return phone.matches("[0-9]*");
    }

    /*public boolean uniquePhone(String phone){

    }*/
    public boolean validCountry(String country) {

        return country.matches("[a-zA-z]*");
    }

   /* public Map<String,String> validData(){

    }
*/
}
