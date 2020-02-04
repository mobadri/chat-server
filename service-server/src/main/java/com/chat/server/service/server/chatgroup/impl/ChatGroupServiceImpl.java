package com.chat.server.service.server.chatgroup.impl;

import com.chat.server.service.server.chatgroup.ChatGroupService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatGroupServiceImpl extends UnicastRemoteObject implements ChatGroupService {

    protected ChatGroupServiceImpl() throws RemoteException {
    }
}
