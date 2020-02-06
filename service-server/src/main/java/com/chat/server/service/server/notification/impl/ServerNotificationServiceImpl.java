package com.chat.server.service.server.notification.impl;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import com.chat.server.service.server.notification.ServerNotificationService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

public class ServerNotificationServiceImpl extends UnicastRemoteObject implements ServerNotificationService {

    public ServerNotificationServiceImpl() throws RemoteException {
    }

    @Override
    public List<Notification> getUserNotification(User user, boolean seen) {
        return null;
    }

    @Override
    public List<Notification> getUserNotificationByType(User user, boolean seen, NotificationType notificationType) {
        return null;
    }

}
