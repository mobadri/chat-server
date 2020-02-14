package com.chat.server.service.server.message.impl;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;
import com.chat.server.service.server.message.ServerMessageService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerMessageServiceImpl extends UnicastRemoteObject implements ServerMessageService {


    Vector<MessageServiceCallBack> messageServiceCallBackVector = new Vector<>();


    public ServerMessageServiceImpl() throws RemoteException {
    }

    @Override
    public void sendMessage(Message message) {
        //@yasmine
        //todo message to all
        //-----------------
        System.out.println(message);
        notifyAll(message);
        //todo save message to db;
        //todo send message notification to all user on the group
        //todo send message to all user on the group s
        // message.getChatGroup().getMessages().add(message);
    }

    @Override
    public void register(MessageServiceCallBack messageServiceCallBack) {
        messageServiceCallBackVector.add(messageServiceCallBack);
    }

    @Override
    public void unRegister(MessageServiceCallBack messageServiceCallBack) {
        messageServiceCallBackVector.remove(messageServiceCallBack);
    }
    public void notifyAll(Message message){
        for (MessageServiceCallBack messageServiceCallBack : messageServiceCallBackVector){
            messageServiceCallBack.receiveMessage(message);
        }
    }


}
