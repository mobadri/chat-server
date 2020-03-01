package com.chat.server.network.server;

import com.chat.server.config.database.connection.NetworkDatabaseConfig;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.service.server.chatgroup.ServerChatGroupService;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.chat.server.service.server.message.ServerMessageService;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.socket_factories.SslClientSocketFactory;
import com.chat.server.service.server.socket_factories.SslServerSocketFactory;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Objects;


public class Server {

    NetworkDatabaseConfig configuration;

    private static Server instance;

    private Registry registry;

    private boolean running = false;

    private ServerUserService userService;
    private ServerNotificationService notificationService;
    private ServerMessageService messageService;
    private ServerChatGroupService chatGroupService;
    private ServerFileTranseferService fileTranseferService;

    private Server() {
        configuration = NetworkDatabaseConfig.getInstance();
        int portNumber = configuration.getServerPortNumber();
        String serverIP = configuration.getServerIp();
        try {
            /*all commented segments of code is connection security trail */
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new RMISecurityManager());
//            }
            System.setProperty("java.rmi.server.hostname", serverIP); // Uses the loopback address, 127.0.0.1, if yo

            registry = LocateRegistry.createRegistry(portNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static Server getInstance() {
        instance = Objects.requireNonNullElseGet(instance, Server::new);
        return instance;
    }

    public void startServer() {
        try {
            if (!running) {

                userService = ServiceFactory.createServerUserService();
                chatGroupService = ServiceFactory.createServerChatGroupService();
                messageService = ServiceFactory.createServerMessageService();
                notificationService = ServiceFactory.createServerNotificationService();
                fileTranseferService = ServiceFactory.createServerFileTranseferService();
//
//                ServerUserService userService2 = (ServerUserService) UnicastRemoteObject.exportObject(userService, 0);
//                chatGroupService = (ServerChatGroupService) UnicastRemoteObject.exportObject(chatGroupService, 0);
//                messageService = (ServerMessageService) UnicastRemoteObject.exportObject(messageService, 0);
//                notificationService = (ServerNotificationService) UnicastRemoteObject.exportObject(notificationService, 0);
//                fileTranseferService = (ServerFileTranseferService) UnicastRemoteObject.exportObject(fileTranseferService, 0);
                System.out.println("server is running");

                registry.rebind("userService", userService);
                registry.rebind("chatGroupService", chatGroupService);
                registry.rebind("messageService", messageService);
                registry.rebind("notificationService", notificationService);
                registry.rebind("fileTranseferService", fileTranseferService);

                running = !running;
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();

        }
    }

    public void stopServer() {
        try {
            if (running) {
                System.out.println("stop server");
                registry.unbind("userService");
                registry.unbind("chatGroupService");
                registry.unbind("messageService");
                registry.unbind("notificationService");
                registry.unbind("fileTranseferService");
                running = !running;
                sendAnnouncement();
            }
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }

    private void sendAnnouncement() {

        Notification notification = new Notification();

        User user = new User();
        user.setId(1);
        user.setOnline(true);
        user.setMode(Mode.AVAILABLE);
        user.setPhone("01150777011");
        user.setFirstName("System");
        user.setLastName("Admin");
        notification.setUserFrom(user);

        notification.setNotificationType(NotificationType.SERVER_IS_CLOSED);
        notification.setNotificationMessage("server is closed!!");

        List<User> allOnlineUsers = null;
        try {
            allOnlineUsers = userService.getOnlineUsers(true);
            for (User onlineUser : allOnlineUsers) {
                notification.setUserTo(onlineUser);
                notificationService.sendNotification(notification);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}