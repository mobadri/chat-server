package com.chat.server.repository.server.user.impl;


import com.chat.server.config.database.ConnectToDBFactory;
import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.adapters.ModelAdapter;
import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.chat.impl.ChatGroupRepositoryImpl;
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
    private final String SELECT_BY_GROUP = "SELECT * FROM USER INNER JOIN GROUP_USER ON USER.ID = GROUP_USER.USER_ID " +
            " INNER JOIN CHAT_GROUP ON CHAT_GROUP.ID =GROUP_USER.GROUP_ID" +
            " AND CHAT_GROUP.ID = ?";
    private final String SELECT_ALL_USER_FRIENDS = "SELECT * FROM USER inner join USER_FRIENDS " +
            " ON (USER_FRIENDS.USER_ID = USER.ID" +
            " OR USER_FRIENDS.FRIEND_ID = USER.ID)" +
            " WHERE (USER_FRIENDS.USER_ID = ? OR USER_FRIENDS.FRIEND_ID = ?)" +
            " AND USER.ID <> ? AND USER_FRIENDS.FRIEND_STATUS = ?";
    private final String SELECT_BY_PHONE = "SELECT * FROM USER WHERE PHONE like '%' ? '%'";
    private final String INSERT_USER = "INSERT INTO USER (FIRST_NAME,LAST_NAME,PHONE,PASSWORD,EMAIL," +
            "COUNTRY,GENDER,DATE_OF_BIRTH,BIO,ONLINE,MODE,IMAGE)" +
            "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE_USER = "UPDATE USER SET FIRST_NAME= ?," +
            "LAST_NAME = ?,PHONE = ?,PASSWORD = ? ,EMAIL = ?," +
            " COUNTRY =? ,GENDER = ?,DATE_OF_BIRTH =?,BIO = ?,ONLINE = ?,MODE = ?,IMAGE=?" +
            " WHERE ID = ?";
    private final String DELETE_USER = "DELETE FROM USER WHERE ID = ?";
    private final String UPDATE_USER_MODE = "";
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ChatGroupRepository chatGroupRepository = new ChatGroupRepositoryImpl();
    private Encryption encryption;

    public UserRepositoryImpl() throws Exception {

        connection = ConnectToDBFactory.creatConnectionManualy();
        encryption = new Encryption();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                user.setPassword("");
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (fullData) {
                    int userId = resultSet.getInt("ID");
                    user = ModelAdapter.mapResultSetToUser(resultSet,
                            findAllUserFriends(userId, FriendStatus.APPROVED),
                            chatGroupRepository.getAllChatGroupsForUser(userId));
                } else {
                    user = ModelAdapter.mapResultSetToUser(resultSet);
                }
            }
            user.setPassword("");
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String hashedPassword = findUserByPhone(phone).getPassword();
        boolean exist = encryption.checkIfExist(password, hashedPassword);

        if (exist) {
            try {
                preparedStatement = connection.prepareStatement(SELECT_BY_PHONE_PASSWORD);
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, hashedPassword);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int userId = resultSet.getInt("ID");
                    user = ModelAdapter.mapResultSetToUser(resultSet, findAllUserFriends(userId, FriendStatus.APPROVED)
                            , chatGroupRepository.getAllChatGroupsForUser(userId));
                }
//                user.setPassword("");
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSetAndPreparedStatement(resultSet, preparedStatement);
            }
        }
        return user;
    }

    @Override
    public List<User> findAllUserFriends(int userId, FriendStatus friendStatus) {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_USER_FRIENDS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, friendStatus.ordinal());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                user.setPassword("");
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
    public User insertUser(User user, String password) {

        int id = -1;
        try {
            user.setPassword(encryption.encrypt(password));
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ModelAdapter.mapUsertoPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            user.setId(id);
            user.setPassword("");
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
    public User updateUser(User user, String password) {

        User updated = null;
        user.setPassword(encryption.encrypt(password));
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            ModelAdapter.mapUsertoPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(13, user.getId());
            int res = preparedStatement.executeUpdate();
            if (res > 0)
                updated = user;
            user.setPassword("");
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
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_PHONE);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                System.out.println("ResultSet.next" + user.getPhone());
                user.setPassword("");
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
    public User findUserByPhone(String phone) {
        User user = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_PHONE);
            preparedStatement.setString(1, phone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = ModelAdapter.mapResultSetToUser(resultSet);
                if (user.getPhone().equals(phone)) {
//                    user.setPassword("");
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSetAndPreparedStatement(resultSet, preparedStatement);
        }
        return user;
    }

    @Override
    public User updateUserMode(User user, Mode mode) {

        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_MODE);
            preparedStatement.setInt(1, mode.ordinal());
            int res = preparedStatement.executeUpdate();
            if (res > 0) {
                user.setMode(mode);
                user.setPassword("");
            }
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
            preparedStatement = connection.prepareStatement(SELECT_IF_ONLINE);
            preparedStatement.setBoolean(1, online);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                user.setPassword("");
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
    public List<User> findByChatGroup(int chatGroupId) {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_GROUP);
            preparedStatement.setInt(1, chatGroupId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = ModelAdapter.mapResultSetToUser(resultSet);
                user.setPassword("");
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}