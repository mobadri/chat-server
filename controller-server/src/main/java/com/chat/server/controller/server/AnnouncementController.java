package com.chat.server.controller.server;

import com.chat.server.controller.server.user.UserController;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.notification.ServerNotificationService;

import java.rmi.RemoteException;
import java.util.List;

public class AnnouncementController {

    ServerNotificationService serverNotificationService;

    public AnnouncementController() {
        serverNotificationService = ServiceFactory.createServerNotificationService();
    }

    public void sendAnnouncementNotification(Notification notification) {
        try {

            UserController userController = new UserController();
            List<User> allOnlineUsers = userController.getAllOnlineUsers(true);
            for (User user : allOnlineUsers) {
                notification.setUserTo(user);
                serverNotificationService.sendNotification(notification);

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
