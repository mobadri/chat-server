package com.chat.server.model.chat;

import com.chat.server.model.user.User;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {

    private int id;
    private String message;
    private Style style;
    private User userFrom;
    private ChatGroup chatGroup;
    private LocalDate timestamp;

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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
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
