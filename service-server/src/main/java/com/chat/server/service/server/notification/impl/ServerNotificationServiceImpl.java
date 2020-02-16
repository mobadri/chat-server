package com.chat.server.service.server.notification.impl;

import com.chat.client.service.client.callback.NotificationServiceCallback;
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

    static Vector<NotificationServiceCallback> notificationServiceCallbackVector = new Vector<>();
    // todo
    private NotificationRepository notificationRepository;

    private static ServerNotificationServiceImpl instance;

    private ServerNotificationServiceImpl() throws RemoteException {
        super(11223);
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

    @Override
    public void sendNotification(Notification notification) {
        System.out.println(notification);
        try {
            notifyAll(notification);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(NotificationServiceCallback notificationServiceCallback) {
        System.out.println("try reg" + notificationServiceCallback);
        notificationServiceCallbackVector.add(notificationServiceCallback);
    }

    @Override
    public void unregister(NotificationServiceCallback notificationServiceCallback) {
        notificationServiceCallbackVector.remove(notificationServiceCallback);
    }

    private void notifyAll(Notification notification) throws RemoteException {
        notificationServiceCallbackVector.size();
        for (NotificationServiceCallback notificationServiceCallback : notificationServiceCallbackVector) {
            System.out.println(notification);
            System.out.println(notification.getUserTo().getId());
            System.out.println(notificationServiceCallback.getUserId());
            System.out.println(notification.getUserTo().isOnline());
            try {
                if (notification.getUserTo().getId() == notificationServiceCallback.getUserId()
                        && notification.getUserTo().isOnline()) {
                    notificationServiceCallback.receiveNotification(notification);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized static ServerNotificationServiceImpl getInstance() {
        if (instance == null) {
            try {
                instance = new ServerNotificationServiceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
