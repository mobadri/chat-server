package com.chat.server.network.server;

import com.chat.server.config.database.NetworkDatabaseConfig;
import com.chat.server.service.server.chatgroup.ServerChatGroupService;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.chat.server.service.server.message.ServerMessageService;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.chat.server.service.server.user.ServerUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;


public class Server {

    NetworkDatabaseConfig configuration;

    private static Server instance;

    private Registry registry;

    private boolean running = false;

    private Server() {
        configuration = NetworkDatabaseConfig.getInstance();
        String portNumber = configuration.getServerPortNumber();
        String serverIP = configuration.getServerIp();
        try {
            /*all commented segments of code is connection security trail */
          /*  if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }*/
            registry = LocateRegistry.createRegistry(Integer.valueOf(portNumber));/*, new RMISSLClientSocketFactory(), new RMISSLServerSocketFactory());*/
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
//            System.setProperty("java.rmi.server.hostname", "10.145.7.174"); // Uses the loopback address, 127.0.0.1, if yo
            if (!running) {
                ServerUserService userService = ServiceFactory.createServerUserService();
                ServerChatGroupService chatGroupService = ServiceFactory.createServerChatGroupService();
                ServerMessageService messageService = ServiceFactory.createServerMessageService();
                ServerNotificationService notificationService = ServiceFactory.createServerNotificationService();
                ServerFileTranseferService fileTranseferService = ServiceFactory.createServerFileTranseferService();

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
            }
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }
}