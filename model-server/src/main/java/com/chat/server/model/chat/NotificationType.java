package com.chat.server.model.chat;

import java.io.Serializable;

public enum NotificationType implements Serializable {
    MESSAGE_RECEIVED,
    MESSAGE_SENT,
    FRIEND_REQUEST,
    FRIEND_ACCEPT,
    FILE_TRANSFERE_REQUEST,
    FILE_TRANSFERE_ACCEPT

}
