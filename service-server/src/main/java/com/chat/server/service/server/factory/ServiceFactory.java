package com.chat.server.service.server.factory;

import com.chat.server.service.server.user.UserService;
import com.chat.server.service.server.user.impl.UserServiceImpl;

import java.rmi.RemoteException;

public class ServiceFactory {
    public static UserService creatUserService() throws RemoteException {

        return new UserServiceImpl();
    }
}
