package com.chat.server.service.server.message.impl;

import com.chat.server.service.server.message.MessageService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageServiceImpl extends UnicastRemoteObject implements MessageService {


    public MessageServiceImpl() throws RemoteException {
    }
}
