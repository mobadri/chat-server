package com.chat.server.repository.server.adapters;

import com.chat.server.model.chat.*;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.model.user.UserFriend;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ModelAdapter {


    private ModelAdapter() {
    }


    /**
     * map resultset to a user object with list of chat group  And list of friends
     *
     * @param resultSet query resultset
     * @return user object
     */
    public static User mapResultSetToUser(ResultSet resultSet, List<User> friends,
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
    public static User mapResultSetToUser(ResultSet resultSet) {
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
            user.setImage(resultSet.getBytes("image") != null ? resultSet.getBytes("image") : new byte[0]);
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
    public static void mapUsertoPreparedStatement(PreparedStatement preparedStatement, User user) {
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
            preparedStatement.setBytes(12, user.getImage() != null ? user.getImage() : new byte[0]);
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
    public static ChatGroup mapResultSetToChatGroup(ResultSet resultSet) {
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
    public static void mapChatGrouptoPreparedStatement(PreparedStatement preparedStatement, ChatGroup chatGroup) {
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
    public static Message mapResultSetToMessage(ResultSet resultSet) {
        Message message = new Message();
        try {

            message.setId(resultSet.getInt("ID"));
            message.setMessage(resultSet.getString("MESSAGE_CONTENT"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static Notification mapResultSetToNotification(ResultSet resultSet) {

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
    public static void mapMessagetoPreparedStatement(PreparedStatement preparedStatement, Message message) {
        try {
            preparedStatement.setString(1, message.getMessage());
            preparedStatement.setInt(2, message.getUserFrom().getId());
            preparedStatement.setInt(3, message.getChatGroup().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Style mapResulsetTosTyle(ResultSet resultSet) {
        Style style = new Style();
        try {
            resultSet.next();
            style.setId(resultSet.getInt(1));
            style.setFontName(resultSet.getString(2));
            style.setFontFamily(resultSet.getString(3));
            style.setFontColor(resultSet.getString(4));
            style.setBackground(resultSet.getString(5));
            style.setFontSize(resultSet.getFloat(6));
            style.setBold(resultSet.getBoolean(7));
            style.setItalic(resultSet.getBoolean(8));
            style.setUnderline(resultSet.getBoolean(9));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return style;
    }

    public static void mapStyleTopreparedStatement(PreparedStatement preparedStatement, Style style) {
        try {
            System.out.println("font name" + style.getFontName());
            preparedStatement.setString(1, style.getFontName());
            preparedStatement.setString(2, style.getFontFamily());
            preparedStatement.setString(3, style.getFontColor());
            preparedStatement.setString(4, style.getBackground());
            preparedStatement.setFloat(5, style.getFontSize());
            preparedStatement.setBoolean(6, style.isBold());
            preparedStatement.setBoolean(7, style.isItalic());
            preparedStatement.setBoolean(8, style.isUnderline());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Date mapDateToSqlDate(LocalDate date) {
        return Date.valueOf(date);
    }

    public static LocalDate mapDateFromSqlDate(Date date) {
        return date.toLocalDate();
    }
}
