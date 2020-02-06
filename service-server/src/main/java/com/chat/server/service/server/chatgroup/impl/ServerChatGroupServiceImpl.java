package com.chat.server.service.server.chatgroup.impl;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.service.server.chatgroup.ServerChatGroupService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;

public class ServerChatGroupServiceImpl extends UnicastRemoteObject implements ServerChatGroupService {

    public ServerChatGroupServiceImpl() throws RemoteException {
    }

    @Override
    public List<ChatGroup> getAllChatGroups() throws RemoteException {
        return null;
    }

    @Override
    public ChatGroup getChatGroupByID(int id) throws RemoteException {
        return null;
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(User user) throws RemoteException {
        return null;
    }

    @Override
    public int insertChatGroup(ChatGroup chatGroup) throws RemoteException {
        return 0;
    }

    @Override
    public int updateChatGroup(ChatGroup chatGroup) throws RemoteException {
        return 0;
    }

    @Override
    public int deleteChatGroup(int id) throws RemoteException {
        return 0;
    }
}
