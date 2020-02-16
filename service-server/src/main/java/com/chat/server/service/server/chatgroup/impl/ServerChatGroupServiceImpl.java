package com.chat.server.service.server.chatgroup.impl;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.service.server.chatgroup.ServerChatGroupService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerChatGroupServiceImpl extends UnicastRemoteObject implements ServerChatGroupService {

    private ChatGroupRepository chatGroupRepository;
    private UserRepository userRepository;

    public ServerChatGroupServiceImpl() throws RemoteException {
        super(11223);
        chatGroupRepository = RepositoryServerFactory.creatChatRepository();
        userRepository = RepositoryServerFactory.creatUserRepository();
    }

    @Override
    public List<ChatGroup> getAllChatGroups() throws RemoteException {
        return chatGroupRepository.getAllChatGroups();
    }

    @Override
    public ChatGroup getChatGroupByID(int id) throws RemoteException {
        ChatGroup chatGroup = chatGroupRepository.getChatGroupByID(id);
        chatGroup.setUsers(userRepository.findByChatGroup(id));
        return chatGroup;
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(User user) throws RemoteException {
        return chatGroupRepository.getAllChatGroupsForUser(user.getId());
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
    public ChatGroup addFriend(ChatGroup chatGroup, User friend) {
        return chatGroupRepository.addFriend(chatGroup, friend);
    }

    @Override
    public ChatGroup removeFriend(ChatGroup chatGroup, User friend) {
        return chatGroupRepository.removeFriend(chatGroup, friend);
    }

    @Override
    public List<ChatGroup> searchByName(String groupName, User user) {
        return chatGroupRepository.searchByName(groupName, user);
    }
}
