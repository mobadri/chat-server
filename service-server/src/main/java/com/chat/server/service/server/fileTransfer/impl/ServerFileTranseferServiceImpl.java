package com.chat.server.service.server.fileTransfer.impl;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.User;
import com.chat.server.service.server.factory.ServiceFactory;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.chat.server.service.server.notification.ServerNotificationService;
import com.healthmarketscience.rmiio.RemoteInputStream;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerFileTranseferServiceImpl extends UnicastRemoteObject implements ServerFileTranseferService {

    private static ServerFileTranseferService instance;
    private ServerNotificationService serverNotificationService = ServiceFactory.createServerNotificationService();
    private Vector<FileTransferServiceCallBack> fileTransferServiceCallBackVector = new Vector<>();

    public ServerFileTranseferServiceImpl() throws RemoteException {

        super(11223);

//        super(11223, SslClientSocketFactory.getInstance(),
//                SslServerSocketFactory.getInstance());
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {
        fileTransferServiceCallBackVector.add(fileTransferServiceCallBack);
    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {
        fileTransferServiceCallBackVector.remove(fileTransferServiceCallBack);
    }

    @Override
    public void sendFile(String nameFile, RemoteInputStream inFile, ChatGroup currentChatGroup, User currentUser) throws RemoteException {
        System.out.println("data in the server: " + nameFile + "    : " + inFile);
        System.out.println("currentUser : " + currentUser);
        System.out.println("currentChatGroup : " + currentChatGroup.getName());
        System.out.println("size of vector" + fileTransferServiceCallBackVector.size());

//        InputStream istream = null;
//
//        try {
//            istream = RemoteInputStreamClient.wrap(inFile);
//            System.out.println(istream);
//            System.out.println("the file Path is : " + nameFile);
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(nameFile), true);
//            byte[] data = new byte[1024 * 1024];
//            int len = istream.read(data);
//            System.out.println(len);
//            while (len != -1) {
//                fileOutputStream.write(data);
//                len = istream.read(data);
//            }
//            System.out.println("file on server");
//
//            notify(nameFile, currentChatGroup, currentUser);
//           /* istream.close();
//            fileOutputStream.flush();
//            fileOutputStream.close();*/
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        clientAcceptFile(inFile, nameFile, currentChatGroup, currentUser);
    }

    public void notify(String fileName, ChatGroup currentChatGroup, User currentUser) {
        for (FileTransferServiceCallBack fileTransferServiceCallBack : fileTransferServiceCallBackVector) {
            try {
                System.out.println("getChatGroupId " + fileTransferServiceCallBack.getChatGroup().getId());
                System.out.println("currentChatGroup" + currentChatGroup);
                System.out.println("gettUser  : " + fileTransferServiceCallBack.getCurrentUser().getId());
                System.out.println("currentUser : " + currentUser.getId());

                if (fileTransferServiceCallBack.getChatGroup().getId() == currentChatGroup.getId() &&
                        fileTransferServiceCallBack.getCurrentUser().getId() != currentUser.getId()
                        && fileTransferServiceCallBack.getCurrentUser().isOnline()) {
                    Notification notification = new Notification(currentUser, fileTransferServiceCallBack.getCurrentUser(), NotificationType.FILE_TRANSFER_ACCEPT);
                    notification.setNotificationMessage(
                            currentChatGroup.getId() + " sent a file :=" + fileName);
                    String messegeContent = notification.getNotificationMessage();
                    String f = messegeContent.substring(messegeContent.indexOf(":=") + 2);
                    System.out.println("name of file ***" + f);
                    serverNotificationService.sendNotification(notification);
                    System.out.println("this client is notified " + fileTransferServiceCallBack.getCurrentUser().getFirstName());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clientAcceptFile(RemoteInputStream remoteInputStream, String filename, ChatGroup currentChatGroup, User currentUser) {
        for (FileTransferServiceCallBack fileTransferServiceCallBack : fileTransferServiceCallBackVector) {
            try /*(BufferedInputStream bufferedInputStream = new BufferedInputStream
                    (new FileInputStream(new File(filename)));
                 InputStream inputStream = new FileInputStream(new File(filename));
                 RemoteInputStreamServer remoteInputStreamServer = new SimpleRemoteInputStream(inputStream))*/ {
                if (currentUser.getId() == fileTransferServiceCallBack.getCurrentUser().getId() && currentUser.isOnline()) {

                    new Thread(() -> {
                        try {
                            fileTransferServiceCallBack.downLoad(filename, remoteInputStream);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                unregister(fileTransferServiceCallBack);
                new File(filename).delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send(String nameFile, ChatGroup currentChatGroup, User currentUser) {
        notify(nameFile, currentChatGroup, currentUser);
    }

    public synchronized static ServerFileTranseferService getInstance() {
        if (instance == null) {
            try {
                instance = new ServerFileTranseferServiceImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
