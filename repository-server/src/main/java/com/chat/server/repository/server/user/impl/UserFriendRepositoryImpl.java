package com.chat.server.repository.server.user.impl;

import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.user.UserFriendRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFriendRepositoryImpl implements UserFriendRepository {

    String SELECT_ALL_FRIENDS_BY_USER_ID = "SELECT * FROM USER_FRIENDS WHERE USER_ID = ?";
    String INSERT_FRIEND = "INSERT INTO USER_FRIENDS  (USER_ID , FRIEND_ID, FRIEND_STATUS) VALUES (?,?,?)";
    String DELETE_FRIEND = "DELETE FROM USER_FRIENDS WHERE (USER_ID = ? AND FRIEND_ID = ?) OR " +
            "(FRIEND_ID = ? AND USER_ID = ?)";
    String UPDATE_FRIEND = "UPDATE USER_FRIENDS SET FRIEND_STATUS = ? WHERE (USER_ID = ? AND FRIEND_ID = ?) OR " +
            "(FRIEND_ID = ? AND USER_ID = ?)";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public UserFriendRepositoryImpl() {
        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    @Override
    public List<User> getAllFriends(int user_id) {
        List<User> friends = new ArrayList<>();
        try {
            //"SELECT * FROM USER_FRIENDS WHERE USER_ID = ?";
            preparedStatement = connection.prepareStatement(SELECT_ALL_FRIENDS_BY_USER_ID);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //friends.add()
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public int addNewFriend(int user_id, int friend_id, FriendStatus friendStatus) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_FRIEND);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, friend_id);
            preparedStatement.setInt(3, friendStatus.ordinal());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateFriend(int user_id, int friend_id, FriendStatus friendStatus) {
        try {
            preparedStatement = connection.prepareStatement(UPDATE_FRIEND);
            preparedStatement.setInt(1, friendStatus.ordinal());
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, friend_id);
            preparedStatement.setInt(4, friend_id);
            preparedStatement.setInt(5, user_id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteFreind(int user_id, int friend_id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_FRIEND);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, friend_id);
            preparedStatement.setInt(3, friend_id);
            preparedStatement.setInt(4, user_id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return i;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
