package com.chat.server.service.server.message;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMessageService extends Remote {

    /**
     * send message to someone
     *
     * @param message message to be sent
     */
    public Message sendMessage(Message message, ChatGroup group) throws RemoteException;

}
