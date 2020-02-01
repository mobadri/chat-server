package com.chat.server.repository.server.chat.impl;


import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.chat.ChatGroupRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupRepositoryImpl implements ChatGroupRepository {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public ChatGroupRepositoryImpl(){

        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    @Override
    public List<ChatGroup> getAllChatGroups() {
        List<ChatGroup> chatGroups = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(ChatGroupRepository.SELECT_ALL_CHAT_GROUP);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ChatGroup chatGroup = ModelAdapter.mapResultSetToChatGroup(resultSet);
                chatGroups.add(chatGroup);
            }
            return chatGroups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResultSetAndPreparedStatement(resultSet,preparedStatement);
        }
        return chatGroups;
    }

    @Override
    public ChatGroup getChatGroupByID(int id) {
        ChatGroup chatGroup= new ChatGroup();
        try {
            preparedStatement = connection.prepareStatement(ChatGroupRepository.SELECT_CHAT_GROUP_BY_ID);
            preparedStatement.setLong(1,id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                chatGroup = ModelAdapter.mapResultSetToChatGroup(resultSet);
            }
            return chatGroup;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResultSetAndPreparedStatement(resultSet,preparedStatement);
        }
        return chatGroup;
    }

    @Override
    public List<ChatGroup> getAllChatGroupsForUser(User user) {

        List<ChatGroup> chatGroups = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(ChatGroupRepository.SELECT_ALL_CHAT_GROUPS_BY_USER_ID);
            preparedStatement.setInt(1,user.getId());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ChatGroup chatGroup = ModelAdapter.mapResultSetToChatGroup(resultSet);
                chatGroups.add(chatGroup);
            }
            return chatGroups;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResultSetAndPreparedStatement(resultSet,preparedStatement);
        }
        return chatGroups;
    }

    @Override
    public int insertChatGroup(ChatGroup chatGroup) {
        int id = -1;
        try {
            preparedStatement = connection.prepareStatement(ChatGroupRepository.INSERT_CHAT_GROUP, Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapChatGrouptoPreparedStatement(preparedStatement, chatGroup);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public int updateChatGroup(ChatGroup chatGroup) {
        try {
            preparedStatement = connection.prepareStatement(ChatGroupRepository.UPDATE_CHAT_GROUP);
            ModelAdapter.mapChatGrouptoPreparedStatement(preparedStatement,chatGroup);
            preparedStatement.setLong(2,chatGroup.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int deleteChatGroup(int id) {
        try {
            preparedStatement = connection.prepareStatement(ChatGroupRepository.DELETE_CHAT_GROUP);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void closeResultSetAndPreparedStatement(ResultSet resultSet,PreparedStatement preparedStatement){
        try {
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
