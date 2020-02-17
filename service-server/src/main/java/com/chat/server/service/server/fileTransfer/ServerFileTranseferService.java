package com.chat.server.service.server.fileTransfer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.chat.client.service.client.callback.*;

public interface ServerFileTranseferService extends Remote {


    void register(  FileTransferServiceCallBack fileTransferServiceCallBack )throws RuntimeException;

    void unregister(FileTransferServiceCallBack fileTransferServiceCallBack)throws RemoteException;

    void send(String fileName,byte [] data,int length)throws RemoteException;
}


