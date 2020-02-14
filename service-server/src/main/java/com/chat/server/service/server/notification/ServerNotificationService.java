package com.chat.server.service.server.notification;

import com.chat.client.service.client.callback.NotificationServiceCallback;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerNotificationService extends Remote {
    /**
     * get user notifications which seen or not seen
     * @param user user to get his notification
     * @param seen seen Or not seen
     * @return list of user notifications
     */
    public List<Notification> getUserNotification(User user ,boolean seen) throws RemoteException;

    /**
     * get user notification which seen or not by notification type
     * @param user user to get his notifications
     * @param seen seen Or not seen
     * @param notificationType notification type
     * @return
     */
    public List<Notification> getUserNotificationByType(User user, boolean seen , NotificationType notificationType) throws RemoteException;

    void sendNotification(Notification notification) throws RemoteException;

    void register(NotificationServiceCallback notificationServiceCallback) throws RemoteException;

    void unregister(NotificationServiceCallback notificationServiceCallback) throws RemoteException;

}
