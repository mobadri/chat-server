package com.chat.server.repository.server;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //-------------------------------
        // @noura
        //todo test all user repo methods
        UserRepository userRepository = RepositoryServerFactory.creatUserRepository();
        List<User> all = userRepository.findAll();
        System.out.println(all.size());

        //-------------------------------
        //@shaheen
        //todo test all chat repo methods
        ChatGroupRepository chatGroupRepository = RepositoryServerFactory.creatChatRepository();
        /*ChatGroup chatGroup = new ChatGroup();
        chatGroup.setName("First Group");
        chatGroupRepository.insertChatGroup(chatGroup);*/
        List<ChatGroup> allChatGroups = chatGroupRepository.getAllChatGroups();
        for (ChatGroup group: allChatGroups){
            System.out.println(group.getId()+" "+group.getName());
        }
        System.out.println(chatGroupRepository.getChatGroupByID(1).getName());

        //-------------------------------
        // @badri
        //todo test all user repo methods
    }
}
