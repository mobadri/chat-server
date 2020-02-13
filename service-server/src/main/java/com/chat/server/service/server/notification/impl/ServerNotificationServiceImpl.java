package com.chat.server.service.server.notification.impl;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.notification.NotificationRepository;
import com.chat.server.service.server.notification.ServerNotificationService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

public class ServerNotificationServiceImpl extends UnicastRemoteObject implements ServerNotificationService {

    //still incomplete
    // todo
    private NotificationRepository notificationRepository;

    public ServerNotificationServiceImpl() throws RemoteException {
        notificationRepository = RepositoryServerFactory.createNotificationRepository();
    }

    @Override
    public List<Notification> getUserNotification(User user, boolean seen) {
        return notificationRepository.getAllUserNotificationsEitherSeenOrNot(user, seen);
    }

    @Override
    public List<Notification> getUserNotificationByType(User user, boolean seen, NotificationType notificationType) {
        return null;
    }
}
