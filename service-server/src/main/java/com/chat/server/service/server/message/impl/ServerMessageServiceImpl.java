package com.chat.server.service.server.message.impl;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.message.ServerMessageService;
import com.chat.server.service.server.notification.ServerNotificationService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerMessageServiceImpl extends UnicastRemoteObject implements ServerMessageService {


    Vector<MessageServiceCallBack> messageServiceCallBackVector = new Vector<>();
    ServerNotificationService serverNotificationService = ServiceFactory.createServerNotificationService();


    private static ServerMessageServiceImpl instance;

    private ServerMessageServiceImpl() throws RemoteException {
        super(11223);}

    @Override
    public void sendMessage(Message message) {
        //@yasmine
        //todo message to all
        //-----------------
        System.out.println(message);
        notifyAll(message);
        for (User user : message.getChatGroup().getUsers()) {
            System.out.println(message.getChatGroup().getUsers());
            if (user != message.getUserFrom() && user.isOnline()) {
                Notification notification = creatNotification("you have a new message form " + message.getUserFrom().getPhone(),
                        NotificationType.MESSAGE_RECEIVED, false);
                notification.setUserFrom(message.getUserFrom());
                notification.setUserTo(user);
                try {
                    serverNotificationService.sendNotification(notification);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        //todo save message to db;
        //todo send message notification to all user on the group
        //todo send message to all user on the group s
        // message.getChatGroup().getMessages().add(message);
    }

    @Override
    public void register(MessageServiceCallBack messageServiceCallBack) {
        messageServiceCallBackVector.add(messageServiceCallBack);
    }

    @Override
    public void unRegister(MessageServiceCallBack messageServiceCallBack) {
        messageServiceCallBackVector.remove(messageServiceCallBack);
    }

    public void notifyAll(Message message) {
        for (MessageServiceCallBack messageServiceCallBack : messageServiceCallBackVector) {
            try {
                if (messageServiceCallBack.getChatGroupId() == message.getChatGroup().getId()) {
                    messageServiceCallBack.receiveMessage(message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private Notification creatNotification(String message, NotificationType notificationType, boolean seen) {
        Notification notification = new Notification();
        notification.setNotificationType(notificationType);
        notification.setNotificationMessage(message);
        return notification;
    }

    public void notifyAll(Message message) {
        for (MessageServiceCallBack messageServiceCallBack : messageServiceCallBackVector) {
            try {
                messageServiceCallBack.receiveMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized static ServerMessageServiceImpl getInstance() {
        if (instance == null) {
            try {
                instance = new ServerMessageServiceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
