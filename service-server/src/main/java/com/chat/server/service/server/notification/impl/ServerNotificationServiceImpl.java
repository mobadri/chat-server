package com.chat.server.service.server.notification.impl;

import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.Mode;
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

    private ServerNotificationServiceImpl() throws Exception {
//        super(11223, new RMISSLClientSocketFactory(),
//                new RMISSLServerSocketFactory());
//        super(11223, SslClientSocketFactory.getInstance(), SslServerSocketFactory.getInstance());
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
        for (NotificationServiceCallback notificationServiceCallback : notificationServiceCallbackVector) {
            try {
//                if (notification.getUserTo().getId() == notificationServiceCallback.getUserId()
                if (notification.getUserTo().getId() == notificationServiceCallback.getUserId()
                        && notification.getUserTo().isOnline()
                        && notification.getUserFrom().getId() != notificationServiceCallback.getUserId()
                ) {
                    notificationServiceCallback.receiveNotification(notification);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                notificationServiceCallbackVector.remove(notificationServiceCallback);
            }
        }
    }

    public Notification createChangeModeNotification(User user, Mode mode, User friend) {

        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.FRIEND_CHANGE_MODE);

        String modeMessage;
        switch (mode) {
            case AWAY:
                modeMessage = "away";
                break;
            case BUSY:
                modeMessage = "busy";
                break;
            case AVAILABLE:
                modeMessage = "available";
                break;
            default:
                modeMessage = "";
        }

        notification.setNotificationMessage(user.getFirstName() + " " + user.getLastName() + " is now " + modeMessage);
        notification.setUserFrom(user);
        notification.setUserTo(friend);
        return notification;
    }

    public synchronized static ServerNotificationServiceImpl getInstance() {
        if (instance == null) {
            try {
                instance = new ServerNotificationServiceImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
