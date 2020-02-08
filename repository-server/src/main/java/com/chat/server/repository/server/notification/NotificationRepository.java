package com.chat.server.repository.server.notification;

import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;

import java.util.List;

public interface NotificationRepository {

    //todo complete this repo
    //@author noura
    String GET_ALL_NOTIFICATIONS_FOR_USER = "SELECT * FROM NOTIFICATIONS WHERE userTo = ?";
    String GET_NOTIFICATIONS_FOR_USER_BY_NOTIFICATION_TYPE = "SELECT * FROM NOTIFICATION WHERE userTo = ? AND"
            + " notificationType = ?";

    String GET_NOTIFICATIONS_FOR_USER_EITHER_SEEN_OR_UNSEEN = "SELECT * FROM NOTIFICATIONS WHERE " +
            "seen = ? and userTo = ?";

    public List<Notification> getAllNotificationsForUser(User user);

    public List<Notification> getAllUserNotificationsEitherSeenOrNot(User user, boolean seen);
}
