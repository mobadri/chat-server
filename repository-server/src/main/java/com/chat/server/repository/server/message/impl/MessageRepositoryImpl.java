package com.chat.server.repository.server.message.impl;

import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.chat.Message;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.message.MessageRepository;
import com.chat.server.repository.server.user.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {

    private final String SELECT_ALL_MESSAGE = "SELECT * FROM MESSAGE WHERE CHATGROUP_ID = ?";
    private final String INSERT_MESSGAE = "INSERT INTO MESSAGE(MESSAGE_CONTENT,USER_ID,CHATGROUP_ID) VALUES (?,?,?)";
    private final String DELETE_MESSAGE = "DELETE FROM MESSAGE WHERE ID = ? ";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public MessageRepositoryImpl() {
        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    @Override
    public List<Message> selectAllMessageByGroup(int group_id) {
        List<Message> messages = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_MESSAGE);
            preparedStatement.setInt(1, group_id);
            UserRepository userRepository = RepositoryServerFactory.creatUserRepository();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = ModelAdapter.mapResultSetToMessage(resultSet);
                message.setUserFrom(userRepository.findById(
                        resultSet.getInt("USER_ID"), false));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return messages;
    }

    @Override
    public Message insertMessage(Message message) {
        int id = -1;
        try {
            preparedStatement = connection.prepareStatement(INSERT_MESSGAE, Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapMessagetoPreparedStatement(preparedStatement, message);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            message.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return message;
    }

    @Override
    public void deleteMessage(int messgae_id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_MESSAGE);
            preparedStatement.setInt(1, messgae_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
    }

    private void closeResultSetAndPreparedStatement(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
