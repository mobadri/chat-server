package com.chat.server.service.server.notification;

import com.chat.client.service.client.message.ClientMessageService;
import com.chat.client.service.client.notification.ClientNotificationService;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerNotificationService extends Remote {
    /**
     * get user notifications which seen or not seen
     * @param user user to get its notification
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

    /**
     * to register client
     * @param clientNotificationService client service to register it
     */
    public void register(ClientNotificationService clientNotificationService);

    /**
     * to unRegister client
     * @param  clientNotificationService client service to register it
     */
    public void unRegister(ClientNotificationService clientNotificationService);
}
