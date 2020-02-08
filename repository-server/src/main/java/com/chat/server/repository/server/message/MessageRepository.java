package com.chat.server.repository.server.message;

import com.chat.server.model.chat.Message;

import java.util.List;

public interface MessageRepository {


    /**
     * get all messages
     *
     * @param group_id
     * @return list of messages
     */
    List<Message> selectAllMessageByGroup(int group_id);

    /**
     * insert new message
     *
     * @param message
     * @return the that inserted
     */
    Message insertMessage(Message message);

    /**
     * delete message
     *
     * @param messgae_id
     */
    void deleteMessage(int messgae_id);

}
