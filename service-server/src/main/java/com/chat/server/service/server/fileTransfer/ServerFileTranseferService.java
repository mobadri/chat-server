package com.chat.server.service.server.fileTransfer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.chat.client.service.client.callback.*;

public interface ServerFileTranseferService extends Remote {

     Vector<FileTransferServiceCallBack> vector = new Vector<>();

     ConcurrentHashMap<Integer,String> concurrentHashMap=new ConcurrentHashMap<>();


    void register(  FileTransferServiceCallBack fileTransferServiceCallBack )throws RuntimeException;

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack)throws RemoteException;

    void send(String fileName,byte [] data,int length)throws RemoteException;
}


