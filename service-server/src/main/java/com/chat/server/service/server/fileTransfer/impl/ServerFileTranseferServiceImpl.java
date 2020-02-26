package com.chat.server.service.server.fileTransfer.impl;

import com.chat.client.service.client.callback.FileTransferServiceCallBack;
import com.chat.server.service.server.fileTransfer.ServerFileTranseferService;
import com.healthmarketscience.rmiio.*;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ServerFileTranseferServiceImpl extends UnicastRemoteObject implements ServerFileTranseferService {

   // RemoteInputStreamServer istream = null;
    String storagePath = "C:/Users/pc/Downloads/server/";

    private static ServerFileTranseferService instance = null;
    Vector<FileTransferServiceCallBack> fileTransferServiceCallBackVector = new Vector<>();

    public ServerFileTranseferServiceImpl() throws RemoteException {
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
    public void sendFile(String nameFile, RemoteInputStream inFile) throws RemoteException {
        System.out.println("data in the server: " + nameFile + "    : " + inFile);
        InputStream istream = null;

        try {
            istream = RemoteInputStreamClient.wrap(inFile);
            System.out.println(istream);
            System.out.println("the file Path is : " + storagePath+nameFile);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(storagePath + nameFile),true);
            byte[] data = new byte[1024 * 1024];

            int len = istream.read(data);
            System.out.println(len);
            while (len != -1) {
                fileOutputStream.write(data);
                len = istream.read(data);
            }
            System.out.println("file on server");

           // notify(nameFile, outStream);
            acceptClient(nameFile);

           /* istream.close();
            fileOutputStream.flush();
            fileOutputStream.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notify(String nameFile, RemoteOutputStream remoteOutputStream) {
        System.out.println(fileTransferServiceCallBackVector.size());
        for (FileTransferServiceCallBack fileTransferServiceCallBack : fileTransferServiceCallBackVector) {

            System.out.println("this client is notified");
            new Thread(() -> {
//                try {
//                  //  fileTransferServiceCallBack.downLoad(nameFile, remoteOutputStream);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }).start();
        }
    }

    public void acceptClient(String filename ) {

        for (FileTransferServiceCallBack fileTransferServiceCallBack : fileTransferServiceCallBackVector) {
            try ( BufferedInputStream bufferedInputStream = new BufferedInputStream
                    (new FileInputStream(new File(storagePath + filename)));){
                InputStream inputStream = new FileInputStream(new File(storagePath + filename));
                RemoteInputStreamServer remoteInputStreamServer = new SimpleRemoteInputStream(inputStream);

                //System.out.println(istream);
                new Thread(() -> {
                    try {
                        fileTransferServiceCallBack.downLoad(filename, remoteInputStreamServer);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //if (istream != null) istream.close();
            }
        }
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
