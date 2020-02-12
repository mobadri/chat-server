package com.chat.server.repository.server.user;

import com.chat.server.model.user.User;

import java.util.List;

public interface UserRepository {


    /**
     * get all users form database
     *
     * @return list<User>
     */
    public List<User> findAll();

    /**
     * get user form database using his id
     *
     * @param id       user id
     * @param fullData full user data such as all friends and all groups
     * @return User
     */
    public User findById(int id, boolean fullData);

    /**
     * sing in by phone and password
     *
     * @param phone    user phone
     * @param password user password
     * @return User if exist on database Or null if not exist
     */
    public User findByPhoneAndPassword(String phone, String password);

    /**
     * get all user friendsx
     *
     * @param user user to get his/her friends
     * @return list of users as a friends for current user
     */
    public List<User> findAllUserFriends(User user);

    /**
     * insert user to database
     *
     * @param user user to insert
     * @return id of inserted user or (-1) if failed to insert
     */
    public int insertUser(User user);

    /**
     * update user to database
     *
     * @param user user to update
     * @return integer number of row updated
     */
    public int updateUser(User user);

    /**
     * delete user from database
     *
     * @param id user id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int delete(int id);

    /**
     * search to find user by his phone
     *
     * @param phone user phone
     * @return user if founded or null if not founded
     */
    public User findByPhone(String phone);

    /**
     * find all online users
     *
     * @param online online = true , offline = false
     * @return list of online Or offline users
     */
    public List<User> findIfOnline(boolean online);
}
