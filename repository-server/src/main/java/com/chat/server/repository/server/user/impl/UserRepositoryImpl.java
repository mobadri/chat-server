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
    private final String SELECT_ALL = "SELECT * FROM USER";
    private final String SELECT_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private final String SELECT_IF_ONLINE = "SELECT * FROM USER WHERE ONLINE = ?";
    private final String SELECT_BY_PHONE_PASSWORD = "SELECT * FROM USER WHERE PHONE = ? " +
            "AND PASSWORD = ?";
    private final String SELECT_ALL_USER_FRIENDS = "SELECT * FROM USER INNER JOIN USER_FRIENDS " +
            "ON USER.ID = USER_FRIENDS.FRIEND_ID WHERE USER.ID = ?";
    private final String SELECT_BY_PHONE = "SELECT * FROM USER WHERE PHONE like '%' ? '%'";
    private final String INSERT_USER = "INSERT INTO USER (FIRST_NAME,LAST_NAME,PHONE,PASSWORD,EMAIL," +
            "COUNTRY,GENDER,DATE_OF_BIRTH,BIO,ONLINE,MODE)" +
            "  VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE_USER = "UPDATE USER SET FIRST_NAME= ?," +
            "LAST_NAME = ?,PHONE = ?,PASSWORD = ? ,EMAIL = ?," +
            " COUNTRY =? ,GENDER = ?,DATE_OF_BIRTH =?,BIO = ?,ONLINE = ?,MODE = ?" +
            " WHERE ID = ?";
    private final String DELETE_USER = "DELETE FROM USER WHERE ID = ?";
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
            preparedStatement = connection.prepareStatement(SELECT_ALL);
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
    public User findById(int id, boolean fullData) {

        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (fullData) {
                    user = ModelAdapter.mapResultSetToUser(resultSet,
                            findAllUserFriends(user),
                            chatGroupRepository.getAllChatGroupsForUser(user));
                } else {
                    user = ModelAdapter.mapResultSetToUser(resultSet);
                }
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
    public User findByPhoneAndPassword(String phone, String password) {

        User user = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_PHONE_PASSWORD);
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
            preparedStatement = connection.prepareStatement(SELECT_ALL_USER_FRIENDS);
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
    public User insertUser(User user) {
        int id = -1;
        try {
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapUsertoPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            user.setId(id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User updateUser(User user) {

        User updated = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            ModelAdapter.mapUsertoPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(12, user.getId());
            int res = preparedStatement.executeUpdate();
            if (res > 0)
                updated = user;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }

    @Override
    public int delete(int id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_USER);
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
    public List<User> findByPhone(String phone) {
        // User user = null;
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_PHONE);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                System.out.println("REsultSet.next" + user.getPhone());
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
    public List<User> findIfOnline(boolean online) {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_IF_ONLINE);
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
