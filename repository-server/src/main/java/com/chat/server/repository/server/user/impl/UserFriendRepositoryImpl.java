package com.chat.server.repository.server.user.impl;

import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.user.UserFriendRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFriendRepositoryImpl implements UserFriendRepository {

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
            preparedStatement = connection.prepareStatement(UserFriendRepository.SELECT_ALL_FRIENDS_BY_USER_ID);
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
    public boolean addNewFriend(int user_id, int friend_id) {
        return false;
    }

    @Override
    public boolean deleteFreind(int user_id, int friend_id) {
        return false;
    }
}
