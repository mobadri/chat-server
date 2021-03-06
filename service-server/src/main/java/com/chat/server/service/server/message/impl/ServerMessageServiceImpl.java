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

    private ServerMessageServiceImpl() throws Exception {
        super(11223);

//        super(11223, new RMISSLClientSocketFactory(),
//                new RMISSLServerSocketFactory());
//        super(11223, SslClientSocketFactory.getInstance(),
//                SslServerSocketFactory.getInstance());
//        super(0, SslClientSocketFactory.getInstance(), SslServerSocketFactory.getInstance());
    }

    @Override
    public void sendMessage(Message message) {
        //@yasmine
        //todo message to all
        //-----------------
        notifyAll(message);
        sedNotification(message);
        //todo save message to db;
        // message.getChatGroup().getMessages().add(message);
    }

    private void sedNotification(Message message) {
        for (User user : message.getChatGroup().getUsers()) {
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

        Vector<MessageServiceCallBack> unreachableSevices = new Vector<>();
        for (MessageServiceCallBack messageServiceCallBack : messageServiceCallBackVector) {
            try {
                if (messageServiceCallBack.getChatGroupId() == message.getChatGroup().getId()
                ) {
                    messageServiceCallBack.receiveMessage(message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                unreachableSevices.add(messageServiceCallBack);
            }
        }
        // remove if user terminated
        if (unreachableSevices.size() > 0) {
            for (MessageServiceCallBack messageServiceCallBack : unreachableSevices) {
                messageServiceCallBackVector.remove(messageServiceCallBack);
                System.out.println("vector size" + messageServiceCallBackVector.size()
                );
            }
        }
    }

    private Notification creatNotification(String message, NotificationType notificationType, boolean seen) {
        Notification notification = new Notification();
        notification.setNotificationType(notificationType);
        notification.setNotificationMessage(message);
        return notification;
    }

    public synchronized static ServerMessageServiceImpl getInstance() {
        if (instance == null) {
            try {
                instance = new ServerMessageServiceImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
