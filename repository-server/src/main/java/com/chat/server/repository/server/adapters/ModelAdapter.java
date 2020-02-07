package com.chat.server.repository.server.adapters;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

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
    public static User mapResultSetToUser(ResultSet resultSet, List<User> friends,
                                          List<ChatGroup> chatGroups) {
        User user = new User();
        mapResultSetToUser(resultSet);
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

    public static Date mapDateToSqlDate(java.util.Date date) {

        return new Date(date.getTime());
    }

    public static java.util.Date mapDateFromSqlDate(Date date) {

        return new java.util.Date(date.getTime());
    }
}
