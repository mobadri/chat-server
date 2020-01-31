package com.chat.server.repository.server.user;
import com.chat.server.model.user.User;

import java.util.List;

public interface UserRepository {
     String SELECT_ALL = "SELECT * FROM USER";
     String SELECT_BY_ID = "SELECT * FROM USER WHERE ID = ?";
     String SELECT_BY_PHONE_PASSWORD = "SELECT * FROM USER WHERE PHONE = ? " +
            "AND PASSWORD = ?";
     String SELECT_ALL_USER_FRIENDS = "SELECT * FROM USER INNER JOIN USER_FRIENDS " +
            "ON USER.ID = USER_FRIENDS.FRIEND_ID WHERE USER.ID = ?";
     String SELECT_BY_PHONE = "SELECT * FROM USER WHERE PHONE = ?";
     String INSERT_USER = "INSERT INTO USER (FIRST_NAME,LAST_NAME,PHONE,PASSWORD,EMAIL," +
            "COUNTRY,GENDER,DATE_OF_BIRTH,BIO,ONLINE,MODE)" +
            "  VALUES (?,?,?,?,?,?,?,?,?,?,?)";
     String UPDATE_USER = "UPDATE USER SET FIRST_NAME= ?," +
            "LAST_NAME = ?,PHONE = ?,PASSWORD = ? ,EMAIL = ?," +
            " COUNTRY =? ,GENDER = ?,DATE_OF_BIRTH =?,BIO = ?,ONLINE = ?,MODE = ?" +
            " WHERE ID = ?";
     String DELETE_USER = "DELETE FROM USER WHERE ID = ?";

    /**
     *  get all users form database
     * @return list<User>
     */
    public List<User> findAll();

    /**
     * get user form database using his id
     * @param id user id
     * @return User
     */
    public User findById(Long id);

    /**
     * sing in by phone and password
     * @param phone user phone
     * @param password user password
     * @return User if exist on database Or null if not exist
     */
    public User findByPhoneAndPassword(String phone, String password);

    /**
     * get all user friendsx
     * @param user user to get his/her friends
     * @return list of users as a friends for current user
     */
    public List<User> findAllUserFriends(User user);

    /**
     * insert user to database
     * @param user user to insert
     * @return id of inserted user or (-1) if failed to insert
     */
    public int insertUser(User user);

    /**
     * update user to database
     * @param user user to update
     * @return integer number of row updated
     */
    public int updateUser(User user);

    /**
     * delete user from database
     * @param id user id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int delete(int id);

    /**
     * search to find user by his phone
     * @param phone user phone
     * @return user if founded or null if not founded
     */
    public User findByPhone(String phone);

}
