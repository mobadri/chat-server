package com.chat.server.repository.server.adapters;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.chat.Notification;
import com.chat.server.model.chat.NotificationType;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.model.user.UserFriend;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelAdapter {


    public ModelAdapter() {
    }


    /**
     * map resultset to a user object with list of chat group  And list of friends
     *
     * @param resultSet query resultset
     * @return user object
     */
    public static synchronized User mapResultSetToUser(ResultSet resultSet, List<User> friends,
                                                       List<ChatGroup> chatGroups) {
        User user = mapResultSetToUser(resultSet);
        user.setFriends(friends);
        user.setChatGroups(chatGroups);

        return user;
    }

    /**
     * map resultset to a user object without list of chat group  Or list of friends
     *
     * @param resultSet query resultset
     * @return user object
     */
    public static synchronized User mapResultSetToUser(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt("ID"));
            user.setFirstName(resultSet.getString("FIRST_NAME"));
            user.setLastName(resultSet.getString("LAST_NAME"));
            user.setPhone(resultSet.getString("PHONE"));
            user.setPassword(resultSet.getString("PASSWORD"));
            user.setEmail(resultSet.getString("EMAIL"));
            user.setCountry(resultSet.getString("COUNTRY"));
            user.setGender(Gender.values()[resultSet.getInt("GENDER")]);
            user.setDateOfBirth(mapDateFromSqlDate(resultSet.getDate("DATE_OF_BIRTH")));
            user.setOnline(resultSet.getBoolean("ONLINE"));
            user.setMode(Mode.values()[resultSet.getInt("MODE")]);
            user.setBIO(resultSet.getString("BIO"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * map user fields to preparedStatement parameters as following
     * 1- FIRST_NAME ,
     * 2- LAST_NAME,
     * 3- PHONE,
     * 4- PASSWORD,
     * 5- EMAIL,
     * 6- COUNTRY,
     * 7- GENDER,
     * 8- DATE_OF_BIRTH,
     * 9- BIO,
     * 10- ONLINE,
     * 11- MODE
     *
     * @param preparedStatement that required to be map in
     * @param user              map its fields to preparedStatement parameters
     */
    public static synchronized void mapUsertoPreparedStatement(PreparedStatement preparedStatement, User user) {
        try {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setInt(7, user.getGender().ordinal());
            preparedStatement.setDate(8, ModelAdapter.mapDateToSqlDate(user.getDateOfBirth()));
            preparedStatement.setString(9, user.getBIO());
            preparedStatement.setBoolean(10, user.isOnline());
            preparedStatement.setInt(11, user.getMode().ordinal());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * map resultset to a ChatGroup object
     *
     * @param resultSet query resultset
     * @return chatGroup object
     */
    public static synchronized ChatGroup mapResultSetToChatGroup(ResultSet resultSet) {
        ChatGroup chatGroup = new ChatGroup();
        try {
            chatGroup.setId(resultSet.getInt("ID"));
            chatGroup.setName(resultSet.getString("GROUP_NAME"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatGroup;
    }

    /**
     * map ChatGroup fields to preparedStatement parameters as following
     * 1- GROUP_NAME
     *
     * @param preparedStatement that required to be map in
     * @param chatGroup         map its fields to preparedStatement parameters
     */
    public static synchronized void mapChatGrouptoPreparedStatement(PreparedStatement preparedStatement, ChatGroup chatGroup) {
        try {
            preparedStatement.setString(1, chatGroup.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * map resultset to a UserFriend object
     *
     * @param resultSet query resultset
     * @return chatGroup object
     */
    public static UserFriend mapResultSetToUserFriend(ResultSet resultSet) {
        UserFriend userFriend = new UserFriend();
        try {
            userFriend.setUser(resultSet.getInt("USER_ID"));
            userFriend.setFriend(resultSet.getInt("FRIEND_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userFriend;
    }

    /**
     * @param
     * @return
     */
    public static synchronized Message mapResultSetToMessage(ResultSet resultSet) {
        Message message = new Message();
        try {

            message.setId(resultSet.getInt("ID"));
            message.setMessage(resultSet.getString("MESSAGE_CONTENT"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static synchronized Notification mapResultSetToNotification(ResultSet resultSet) {

        Notification notification = new Notification();
        try {
            notification.setId(resultSet.getInt("id"));
            notification.setUserFrom((User) resultSet.getObject("user_from"));
            notification.setUserTo((User) resultSet.getObject("user_to"));

            int type = resultSet.getInt("notification_type");
            NotificationType notificationType;
            switch (type) {
                case 1:
                    notificationType = NotificationType.MESSAGE_RECEIVED;
                    break;
                case 2:
                    notificationType = NotificationType.MESSAGE_SENT;
                    break;
                case 3:
                    notificationType = NotificationType.FRIEND_REQUEST;
                    break;
                case 4:
                    notificationType = NotificationType.FRIEND_ACCEPT;
                    break;
                case 5:
                    notificationType = NotificationType.FILE_TRANSFER_REQUEST;
                    break;
                case 6:
                    notificationType = NotificationType.FILE_TRANSFER_ACCEPT;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            notification.setNotificationType(notificationType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

    /**
     * @param
     * @return
     */
    public static synchronized void mapMessagetoPreparedStatement(PreparedStatement preparedStatement, Message message) {
        try {
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setInt(2, message.getUserFrom().getId());
            preparedStatement.setInt(3, message.getChatGroup().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Date mapDateToSqlDate(java.util.Date date) {

        return new Date(date.getTime());
    }

    public static synchronized java.util.Date mapDateFromSqlDate(Date date) {

        return new java.util.Date(date.getTime());
    }
}
