package com.chat.server.model.chat;

import java.io.Serializable;

public enum NotificationType implements Serializable {
    MESSAGE_RECEIVED,
    MESSAGE_SENT,
    FRIEND_REQUEST,
    FRIEND_ACCEPT,
    FILE_TRANSFER_REQUEST,
    FILE_TRANSFER_ACCEPT
}
