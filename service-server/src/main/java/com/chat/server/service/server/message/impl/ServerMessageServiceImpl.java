package com.chat.server.service.server.message.impl;

import com.chat.server.service.server.message.ServerMessageService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerMessageServiceImpl extends UnicastRemoteObject implements ServerMessageService {

    public ServerMessageServiceImpl() throws RemoteException {
    }
}
