package com.chat.server.service.server.fileTransfer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.chat.client.service.client.callback.*;
import com.healthmarketscience.rmiio.RemoteInputStream;

public interface ServerFileTranseferService extends Remote {



     ConcurrentHashMap<Integer,String> concurrentHashMap=new ConcurrentHashMap<>();


    void register(  FileTransferServiceCallBack fileTransferServiceCallBack )throws RemoteException;

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack)throws RemoteException;

    void sendFile(String nameFile, RemoteInputStream inFile)throws RemoteException;
}


