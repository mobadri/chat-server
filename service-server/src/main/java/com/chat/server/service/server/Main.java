package com.chat.server.service.server;

import com.chat.server.service.server.validation.UserValidation;
public class Main {
    public static void main(String[] args) {
        UserValidation s=new UserValidation();

        System.out.println(s.validMail("mariamRagab.ieee@gmail.com"));
    }
}
