package com.chat.server.network.server;

import com.chat.server.service.server.chatgroup.ServerChatGroupService;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.message.ServerMessageService;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.*;
import java.rmi.registry.*;

public class Server {

    private final static int PORT_NUMBER = 11223;
    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            // server main
            ServerUserService serverUserService = ServiceFactory.createServerUserService();
            ServerChatGroupService chatGroupService = ServiceFactory.createServerChatGroupService();
            ServerMessageService messageService = ServiceFactory.createServerMessageService();
            ServerNotificationService notificationService = ServiceFactory.createServerNotificationService();

            Registry reg = LocateRegistry.createRegistry(PORT_NUMBER);
            System.out.println("server is sunning");

            reg.rebind("userService", serverUserService);
            reg.rebind("chatGroupService", chatGroupService);
            reg.rebind("messageService", messageService);
            reg.rebind("notificationService", notificationService);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}