package com.chat.server.service.server.message;


import com.chat.client.service.client.message.ClientMessageService;

import java.rmi.Remote;

public interface ServerMessageService extends Remote {

    /**
     * to register client
     * @param clientMessageService client service to register it
     */
    public void register(ClientMessageService clientMessageService);

    /**
     * to unRegister client
     * @param  clientMessageService client service to register it
     */
    public void unRegister(ClientMessageService clientMessageService);
}
