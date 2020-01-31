package com.chat.server.model.chat;

import com.chat.server.model.user.User;

public class Notification {
    private int id;
    private User userFrom;
    private User userTo;
    private NotificationType notificationType;

    public Notification() {
    }

    public Notification(User userFrom, User userTo, NotificationType notificationType) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.notificationType = notificationType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
