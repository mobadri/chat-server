package com.chat.server.service.server.chatgroup.impl;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.message.MessageRepository;
import com.chat.server.service.server.chatgroup.ServerChatGroupService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerChatGroupServiceImpl extends UnicastRemoteObject implements ServerChatGroupService {

    /*
        Vector<ClientChatGroupService> clientChatGroupServices = new Vector<>();
    */
    ChatGroupRepository chatGroupRepository = RepositoryServerFactory.creatChatRepository();
    MessageRepository messageRepository = RepositoryServerFactory.createMessageRepository();

    public ServerChatGroupServiceImpl() throws RemoteException {
    }

    @Override
    public List<ChatGroup> getAllChatGroups() throws RemoteException {
        return chatGroupRepository.getAllChatGroups();
    }

    @Override
    public ChatGroup getChatGroupByID(int id) throws RemoteException {
        return chatGroupRepository.getChatGroupByID(id);
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(User user) throws RemoteException {
        return chatGroupRepository.getAllChatGroupsForUser(user);
    }

    @Override
    public ChatGroup insertChatGroup(ChatGroup chatGroup) throws RemoteException {
        return chatGroupRepository.insertChatGroup(chatGroup);
    }

    @Override
    public ChatGroup updateChatGroup(ChatGroup chatGroup) throws RemoteException {
        return chatGroupRepository.updateChatGroup(chatGroup);
    }

    @Override
    public int deleteChatGroup(int id) throws RemoteException {
        return chatGroupRepository.deleteChatGroup(id);
    }

    @Override
    public Message sendMessage(Message message) {

        return messageRepository.insertMessage(message);
    }

    @Override
    public User addFriend(ChatGroup chatGroup, User friend) {
        return chatGroupRepository.addFriend(chatGroup, friend);
    }

    @Override
    public int removeFriend(ChatGroup chatGroup, User friend) {
        return chatGroupRepository.removeFriend(chatGroup, friend);
    }

    @Override
    public List<ChatGroup> searchByName(String groupName, User user) {
        return chatGroupRepository.searchByName(groupName, user);
    }

    /*@Override
    public void register(ClientChatGroupService clientChatGroupService) {
        clientChatGroupServices.add(clientChatGroupService);
    }

    @Override
    public void unRegister(ClientChatGroupService clientChatGroupService) {
        clientChatGroupServices.remove(clientChatGroupService);
    }*/
}
