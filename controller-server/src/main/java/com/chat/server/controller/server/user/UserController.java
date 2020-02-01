package com.chat.server.controller.server.user;

import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.user.UserService;

import java.util.List;

public class UserController {
    private UserService service = ServiceFactory.creatUserService();

    public List<User> getAllOnlineUsers(boolean online) {
        return service.getOnlineUsers(online);
    }

    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
}
