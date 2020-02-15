package com.chat.server.network.server;

import com.chat.server.service.server.chatgroup.ServerChatGroupService;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.message.ServerMessageService;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    private final static int PORT_NUMBER = 11223;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {

            ServerUserService userService = ServiceFactory.createServerUserService();
            ServerChatGroupService chatGroupService = ServiceFactory.createServerChatGroupService();
            ServerMessageService messageService = ServiceFactory.createServerMessageService();
            ServerNotificationService notificationService = ServiceFactory.createServerNotificationService();

            Registry registry = LocateRegistry.createRegistry(PORT_NUMBER);
            System.out.println("server is running");

            registry.rebind("userService", userService);
            registry.rebind("chatGroupService", chatGroupService);
            registry.rebind("messageService", messageService);
            registry.rebind("notificationService", notificationService);
        } catch (RemoteException ex) {
            ex.printStackTrace();

        }
    }
}