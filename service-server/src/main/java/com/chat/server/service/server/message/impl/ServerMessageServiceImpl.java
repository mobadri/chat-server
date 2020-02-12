package com.chat.server.service.server.message.impl;

import com.chat.server.model.chat.Message;
import com.chat.server.service.server.message.ServerMessageService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerMessageServiceImpl extends UnicastRemoteObject implements ServerMessageService {

/*
    Vector<ClientMessageService> clientMessageServices = new Vector<>();
*/

    public ServerMessageServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(Message message) {
        //@yasmine
        //todo message to all
        //-----------------
        System.out.println(message);
        //todo save message to db;
        //todo send message notification to all user on the group
        //todo send message to all user on the group s
        // message.getChatGroup().getMessages().add(message);
    }

   /* @Override
    public void register(ClientMessageService clientMessageService) {
        clientMessageServices.add(clientMessageService);
    }

    @Override
    public void unRegister(ClientMessageService clientMessageService) {
        clientMessageServices.remove(clientMessageService);
    }*/

}
