package com.chat.server.service.server.message.impl;

import com.chat.client.service.client.message.ClientMessageService;
import com.chat.server.service.server.message.ServerMessageService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerMessageServiceImpl extends UnicastRemoteObject implements ServerMessageService {

    Vector<ClientMessageService> clientMessageServices = new Vector<>();

    public ServerMessageServiceImpl() throws RemoteException {
    }

    @Override
    public void register(ClientMessageService clientMessageService) {
        clientMessageServices.add(clientMessageService);
    }

    @Override
    public void unRegister(ClientMessageService clientMessageService) {
        clientMessageServices.remove(clientMessageService);
    }
}
