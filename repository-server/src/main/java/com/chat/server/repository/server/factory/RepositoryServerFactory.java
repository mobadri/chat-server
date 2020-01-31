package com.chat.server.repository.server.factory;

import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.chat.impl.ChatGroupRepositoryImpl;
import com.chat.server.repository.server.user.UserRepository;
import com.chat.server.repository.server.user.impl.UserRepositoryImpl;

public class RepositoryServerFactory {

    public static final UserRepository creatUserRepository()
    {
        return new UserRepositoryImpl();
    }
    public static final ChatGroupRepository creatChatRepository(){

        return new ChatGroupRepositoryImpl();
    }
}
