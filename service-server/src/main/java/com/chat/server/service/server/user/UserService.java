package com.chat.server.service.server.user;

import com.chat.server.model.user.User;

import java.util.List;

public interface UserService {
    /**
     * get all users register on the system
     * @return list of register users
     */
    public List<User> getAllUsers();

    /**
     * get user by his id
     * @param id user id
     * @return user if founded or null of not founded
     */
    public User getUserById(int id);

    /**
     * get user by phone and password
     * @param phone user phone
     * @param password user password
     * @return user if founded Or null if not founded
     */
    public User getByPhoneAndPassword(String phone , String password);

    /**
     * search for user by phone
     * @param phone user phone
     * @return user if founded Or null if not founded
     */
    public User getByPhone(String phone);

    /**
     * get list of user friends
     * @param user user to get his friends
     * @return list of users as user friends
     */
    public List<User> getUserFriends(User user);

    /**
     * insert Or register user to the system
     * @param user user to be inserted
     * @return int id of user inserted 0 if failed to insert
     */
    public int insertUser(User user);

    /**
     * update user data to the system
     * @param user user to be updated
     * @return int number of row updated
     */
    public int updateUser(User user);

    /**
     * delete user form the system
     * @param id user id to be deleted
     * @return int number of row deleted
     */
    public int deleteUser(int id);
}
