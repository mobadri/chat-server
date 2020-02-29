package com.chat.server.repository.server.user.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;

class Encryption {

    public Encryption(){
    }

    public String encrypt(String data) {
        return BCrypt.hashpw(data, BCrypt.gensalt());
    }

    public boolean checkIfExist(String password, String hashedPassword) {
         return BCrypt.checkpw(password, hashedPassword);
    }
}