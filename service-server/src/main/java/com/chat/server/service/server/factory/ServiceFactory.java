package com.chat.server.service.server.factory;

import com.chat.server.service.server.user.UserService;
import com.chat.server.service.server.user.impl.UserServiceImpl;

public class ServiceFactory {
    public static UserService creatUserService() {
        return new UserServiceImpl();
    }
}
