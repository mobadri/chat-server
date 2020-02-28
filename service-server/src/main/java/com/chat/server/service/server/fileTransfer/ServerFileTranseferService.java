package com.chat.server.service.server.fileTransfer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.chat.client.service.client.callback.*;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.healthmarketscience.rmiio.RemoteInputStream;

public interface ServerFileTranseferService extends Remote {

    void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException;

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException;

    void sendFile(String nameFile, RemoteInputStream inFile, ChatGroup currentChatGroup, User currentUser) throws RemoteException;

    void clientAcceptFile(RemoteInputStream remoteInputStream,String filename, ChatGroup currentChatGroup, User currentUser) throws RemoteException;

    public void send(String nameFile, ChatGroup currentChatGroup, User currentUser) throws RemoteException;
}


