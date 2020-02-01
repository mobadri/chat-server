package com.chat.server.repository.server.user.impl;


import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ChatGroupRepository chatGroupRepository = RepositoryServerFactory.creatChatRepository();

    public UserRepositoryImpl() {

        connection = ConnectToDBFactory.creatConnectionManualy();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(UserRepository.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(UserRepository.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = ModelAdapter.mapResultSetToUser(resultSet,
                        findAllUserFriends(user),
                        chatGroupRepository.getAllChatGroupsForUser(user));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;

    }

    @Override
    public User findByPhoneAndPassword(String phone, String password) {
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(UserRepository.SELECT_BY_PHONE_PASSWORD);
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = ModelAdapter.mapResultSetToUser(resultSet);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return user;
    }

    @Override
    public List<User> findAllUserFriends(User user) {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(UserRepository.SELECT_ALL_USER_FRIENDS);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(ModelAdapter.mapResultSetToUser(resultSet, null, null));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return users;
    }

    @Override
    public int insertUser(User user) {
        int id = -1;
        try {
            preparedStatement = connection.prepareStatement(UserRepository.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapUsertoPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public int updateUser(User user) {
        try {
            preparedStatement = connection.prepareStatement(UserRepository.UPDATE_USER);
            ModelAdapter.mapUsertoPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(12, user.getId());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int delete(int id) {
        try {
            preparedStatement = connection.prepareStatement(UserRepository.DELETE_USER);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public User findByPhone(String phone) {
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(UserRepository.SELECT_BY_PHONE);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = ModelAdapter.mapResultSetToUser(resultSet);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return user;

    }

    @Override
    public List<User> findIfOnline(boolean online) {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(UserRepository.SELECT_IF_ONLINE);
            preparedStatement.setBoolean(1, online);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return users;

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
