package com.chat.server.service.server.fileTransfer.impl;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ServerFileTranseferServiceImpl extends UnicastRemoteObject implements ServerFileTranseferService {

    private static ServerFileTranseferService instance = null;
    Vector<FileTransferServiceCallBack> fileTransferServiceCallBackVector = new Vector<>();

    public ServerFileTranseferServiceImpl() throws RemoteException {
    }

    @Override
    public void register(FileTransferServiceCallBack fileTransferServiceCallBack) throws RuntimeException {
        fileTransferServiceCallBackVector.add(fileTransferServiceCallBack);
    }

    @Override
    public void unregister(FileTransferServiceCallBack fileTransferServiceCallBack) throws RemoteException {
        fileTransferServiceCallBackVector.remove(fileTransferServiceCallBack);
    }

    @Override
    public void sendFile(String nameFile, RemoteInputStream inFile) throws RemoteException {
        InputStream istream = null;
        try {
            istream = RemoteInputStreamClient.wrap(inFile);

//            int letter = istream.read();
//            while (letter != -1) {
//                System.out.print((char) letter);
//                letter = istream.read();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadFile() {

    }

//    private void notify(String fileName, byte[] data, int length) {
//        for (FileTransferServiceCallBack fileTransferServiceCallBack : vector) {
//            try {
//                fileTransferServiceCallBack.downLoad(fileName, data, length);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }



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
