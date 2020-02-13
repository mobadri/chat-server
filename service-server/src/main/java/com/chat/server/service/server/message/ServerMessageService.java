package com.chat.server.service.server.message;

import com.chat.server.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMessageService extends Remote {
    /**
     * send Message to group
     * @param message to send
     */

        void sendMessage(Message message) throws RemoteException;
   /* *//**
     * to register client
     * @param clientMessageService client service to register it
     *//*
    public void register(ClientMessageService clientMessageService);

    *//**
     * to unRegister client
     * @param  clientMessageService client service to register it
     *//*
    public void unRegister(ClientMessageService clientMessageService);*/
}
