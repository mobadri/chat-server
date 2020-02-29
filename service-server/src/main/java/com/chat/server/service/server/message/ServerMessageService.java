package com.chat.server.service.server.message;

import com.chat.client.service.client.callback.MessageServiceCallBack;
import com.chat.server.model.chat.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMessageService extends Remote {
    /**
     * send Message to group
     * @param message to send
     */
        void sendMessage(Message message) throws RemoteException;

    /**
     * to register client
<<<<<<< HEAD
     * @param clientMessageService client service to register it
     *//*

    public void register( clientMessageService);
=======
     * @param messageServiceCallBack client service to register it
     */
     void register(MessageServiceCallBack messageServiceCallBack)throws RemoteException;
>>>>>>> af7fefbb7152abd59a715fbffd63f5dd13118cad

    /**
     * to unRegister client
<<<<<<< HEAD
     * @param  clientMessageService client service to register it
    *//*

    public void unRegister(ClientMessageService clientMessageService);*/
=======
     * @param  messageServiceCallBack client service to register it
     */
    public void unRegister(MessageServiceCallBack messageServiceCallBack)throws RemoteException;
>>>>>>> af7fefbb7152abd59a715fbffd63f5dd13118cad
}
