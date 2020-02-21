package com.chat.server.repository.server.user;

import com.chat.server.model.user.FriendStatus;
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
     * @param userID user to get his/her friends
     * @return list of users as a friends for current user
     */
    public List<User> findAllUserFriends(int userID, FriendStatus friendStatus);

    /**
     * insert user to database
     *
     * @param user user to insert
     * @return id of inserted user or (-1) if failed to insert
     */
    public User insertUser(User user, String password);

    /**
     * update user to database
     *
     * @param user user to update
     * @return integer number of row updated
     */
    public User updateUser(User user, String password);

    /**
     * delete user from database
     *
     * @param id user id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int delete(int id);

    /**
     * search to find List of users by there phone
     *
     * @param phone user phone
     * @return list of users if founded or empty list if not founded
     */
    public List<User> findByPhone(String phone);

    /**
     * find all online users
     *
     * @param online online = true , offline = false
     * @return list of online Or offline users
     */
    public List<User> findIfOnline(boolean online);

    /**
     * find all users on same chat group
     *
     * @param chatGroupId the id of chat group
     * @return list of users on the same group
     */
    public List<User> findByChatGroup(int chatGroupId);

    /**
     * find user
     * @param pbone user phone
     * @return user or null if not found
     */
    public  User findUserByPhone(String pbone);

}
