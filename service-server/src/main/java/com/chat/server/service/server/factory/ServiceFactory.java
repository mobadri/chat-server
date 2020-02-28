package com.chat.server.service.server.factory;

import com.chat.server.service.server.chatgroup.ServerChatGroupService;
import com.chat.server.service.server.chatgroup.impl.ServerChatGroupServiceImpl;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.chat.server.service.server.fileTransfer.impl.ServerFileTranseferServiceImpl;
import com.chat.server.service.server.message.ServerMessageService;
import com.chat.server.service.server.message.impl.ServerMessageServiceImpl;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.notification.impl.ServerNotificationServiceImpl;
import com.chat.server.service.server.user.ServerUserService;
import com.chat.server.service.server.user.impl.ServerUserServiceImpl;

import java.rmi.RemoteException;

public class ServiceFactory {

    public static ServerUserService createServerUserService() throws RemoteException {
        return ServerUserServiceImpl.getInstance();
    }

    public static ServerMessageService createServerMessageService() throws RemoteException {
        return ServerMessageServiceImpl.getInstance();
    }

    public static ServerNotificationService createServerNotificationService() throws RemoteException {
        return ServerNotificationServiceImpl.getInstance();
    }

    public static ServerChatGroupService createServerChatGroupService() throws RemoteException {
        return ServerChatGroupServiceImpl.getInstance();

    }
    public static ServerFileTranseferService createServerFileTranseferService() throws RemoteException {
        return ServerFileTranseferServiceImpl.getInstance();

    }
}
