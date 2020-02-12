package com.chat.server.model.chat;

import com.chat.server.model.user.User;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Message implements Serializable {

    private int id;
    private String message;
    private User userFrom;
    private ChatGroup chatGroup;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public ChatGroup getChatGroup() {
        return chatGroup;
    }


    public void setChatGroup(ChatGroup chatGroup) {
        this.chatGroup = chatGroup;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", userFrom=" + userFrom.getPhone() +
               // ", chatGroup=" + chatGroup.getName() +
                '}';
    }
}
