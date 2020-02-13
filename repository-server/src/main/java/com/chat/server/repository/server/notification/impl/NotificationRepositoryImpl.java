package com.chat.server.repository.server.notification.impl;

import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.notification.NotificationRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepository {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public NotificationRepositoryImpl() {
        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    @Override
    public List<Notification> getAllNotificationsForUser(User user) {

        List<Notification> notifications = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(NotificationRepository.GET_ALL_NOTIFICATIONS_FOR_USER);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                notifications.add(ModelAdapter.mapResultSetToNotification(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    @Override
    public List<Notification> getAllUserNotificationsEitherSeenOrNot(User user, boolean seen) {

        List<Notification> notifications = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(NotificationRepository.GET_NOTIFICATIONS_FOR_USER_EITHER_SEEN_OR_UNSEEN);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setBoolean(2, seen);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                notifications.add(ModelAdapter.mapResultSetToNotification(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
