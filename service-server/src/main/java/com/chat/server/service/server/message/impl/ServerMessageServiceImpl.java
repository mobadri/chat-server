package com.chat.server.service.server.message.impl;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.message.MessageRepository;
import com.chat.server.service.server.message.ServerMessageService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerMessageServiceImpl extends UnicastRemoteObject implements ServerMessageService {

    //todo
    private MessageRepository messageRepository;

    public ServerMessageServiceImpl() throws RemoteException {
        messageRepository = RepositoryServerFactory.createMessageRepository();
    }

    @Override
    public Message sendMessage(Message message, ChatGroup group) throws RemoteException {

        message.setChatGroup(group);
        Message insertedMessage =  messageRepository.insertMessage(message);
        group.getMessages().add(insertedMessage);
        //broadcasting
        return insertedMessage;
    }
}
