package com.chat.server.service.server.fileTransfer.impl;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ServerFileTranseferServiceImpl extends UnicastRemoteObject implements ServerFileTranseferService {
    private Vector<FileTransferServiceCallBack> vector = new Vector<>();

    public ServerFileTranseferServiceImpl() throws RemoteException {
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RuntimeException {
        vector.add(fileTransferServiceCallBack);
    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {
        vector.remove(fileTransferServiceCallBack);
    }

    @Override
    public void send(String fileName, byte[] data, int length) throws RemoteException {
        notify(fileName, data, length);
    }

    private void notify(String fileName, byte[] data, int length) {
        for (FileTransferServiceCallBack fileTransferServiceCallBack : vector) {
            try {
                fileTransferServiceCallBack.downLoad(fileName, data, length);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
