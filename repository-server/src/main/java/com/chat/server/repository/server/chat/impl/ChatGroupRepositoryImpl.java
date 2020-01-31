package com.chat.server.repository.server.chat.impl;


import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.chat.ChatGroupRepository;


import java.util.List;

public class ChatGroupRepositoryImpl implements ChatGroupRepository {

    @Override
    public List<ChatGroup> getAllChatGroups() {

        return null;
    }

    @Override
    public ChatGroup getChatGroupByID(int id) {
        return null;
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(User user) {
        return null;
    }

    @Override
    public int insertChatGroup(ChatGroup chatGroup) {
        return 0;
    }

    @Override
    public int updateChatGroup(ChatGroup chatGroup) {
        return 0;
    }

    @Override
    public int deleteChatGroup(int id) {
        return 0;
    }
}
