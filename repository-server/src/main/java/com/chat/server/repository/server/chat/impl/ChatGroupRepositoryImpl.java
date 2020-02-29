package com.chat.server.repository.server.chat.impl;


import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.chat.ChatGroupRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupRepositoryImpl implements ChatGroupRepository {
    private final String SELECT_ALL_CHAT_GROUP = "SELECT * FROM CHAT_GROUP";
  //  private final String SELEC_DIFFERENT_USER_TO_INVITE="SELECT * FROM   user_friends   WHERE  FRIEND_ID  NOT IN (SELECT user_id from group_user) And( user_id = ? OR friend_id =?)";
    private final String SELECT_CHAT_GROUP_BY_ID = "SELECT * FROM CHAT_GROUP WHERE ID = ?";

    private final String SELECT_ALL_CHAT_GROUPS_BY_USER_ID = "SELECT * FROM CHAT_GROUP " +
            "INNER join GROUP_USER " +
            "ON CHAT_GROUP.ID =GROUP_USER.GROUP_ID " +
            "WHERE GROUP_USER.USER_ID = ?";

    private final String INSERT_CHAT_GROUP = "INSERT INTO CHAT_GROUP (GROUP_NAME) VALUES (?)";

    private final String UPDATE_CHAT_GROUP = "UPDATE CHAT_GROUP SET GROUP_NAME = ? WHERE ID = ?";

    private final String DELETE_CHAT_GROUP = "DELETE FROM CHAT_GROUP WHERE ID = ?";
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public ChatGroupRepositoryImpl() {
        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    @Override
    public List<ChatGroup> getAllChatGroups() {

        List<ChatGroup> chatGroups = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_CHAT_GROUP);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ChatGroup chatGroup = ModelAdapter.mapResultSetToChatGroup(resultSet);
                chatGroups.add(chatGroup);
            }
            return chatGroups;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return chatGroups;
    }

    @Override
    public ChatGroup getChatGroupByID(int id) {

        ChatGroup chatGroup = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_CHAT_GROUP_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chatGroup = ModelAdapter.mapResultSetToChatGroup(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return chatGroup;
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(int userId) {

        List<ChatGroup> chatGroups = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_CHAT_GROUPS_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ChatGroup chatGroup = ModelAdapter.mapResultSetToChatGroup(resultSet);
                chatGroups.add(chatGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return chatGroups;
    }

    @Override
    public ChatGroup insertChatGroup(ChatGroup chatGroup) {

        ChatGroup insertedChatGroup = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_CHAT_GROUP, Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapChatGrouptoPreparedStatement(preparedStatement, chatGroup);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedChatGroup = getChatGroupByID(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return insertedChatGroup;
    }

    @Override
    public ChatGroup updateChatGroup(ChatGroup chatGroup) {

        try {
            preparedStatement = connection.prepareStatement(UPDATE_CHAT_GROUP);
            ModelAdapter.mapChatGrouptoPreparedStatement(preparedStatement, chatGroup);
            preparedStatement.setLong(2, chatGroup.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return chatGroup;
    }

    @Override
    public int deleteChatGroup(int id) {

        try {
            preparedStatement = connection.prepareStatement(DELETE_CHAT_GROUP);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return 0;
    }

    @Override
    public boolean addFriend(int chatGroup, int friend) {

        try {
           // chatGroup.getUsers().add(friend);
            preparedStatement = connection.prepareStatement(INSERT_USER_IN_CHAT_GROUP);
            preparedStatement.setInt(1, chatGroup);
            preparedStatement.setInt(2, friend);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
    return false;
    }

    @Override
    public ChatGroup removeFriend(ChatGroup chatGroup, User friend) {

        try {
            chatGroup.getUsers().remove(friend);
            preparedStatement = connection.prepareStatement(DELETE_USER_FROM_CHAT_GROUP);
            preparedStatement.setInt(1, chatGroup.getId());
            preparedStatement.setInt(2, friend.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return chatGroup;
    }

    @Override
    public List<ChatGroup> searchByName(String groupName, User user) {

        List<ChatGroup> chatGroups = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_CHAT_GROUP_BY_NAME);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, groupName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chatGroups.add(ModelAdapter.mapResultSetToChatGroup(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return chatGroups;
    }


    private void closeResultSetAndPreparedStatement(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}