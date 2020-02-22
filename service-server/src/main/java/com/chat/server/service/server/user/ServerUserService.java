package com.chat.server.service.server.user;

import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ServerUserService extends Remote {

    /**
     * get all users register on the system
     *
     * @return list of register users
     * @throws RemoteException
     */
    public List<User> getAllUsers() throws RemoteException;

    /**
     * get user by his id
     *
     * @param id       user id
     * @param fullData boolean to get all user data with friends and chatgroups
     * @return user if founded or null of not founded
     * @throws RemoteException
     */
    public User getUserById(int id, boolean fullData) throws RemoteException;

    /**
     * get user by phone and password
     *
     * @param phone    user phone
     * @param password user password
     * @return user if founded Or null if not founded
     * @throws RemoteException
     */
    public User getByPhoneAndPassword(String phone, String password) throws RemoteException;

    /**
     * search for user by phone
     *
     * @param phone user phone
     * @return user if founded Or null if not founded
     * @throws RemoteException
     */
    public List<User> getByPhone(String phone) throws RemoteException;

    /**
     * get list of user friends
     *
     * @param user user to get his friends
     * @return list of users as user friends
     * @throws RemoteException
     */
    public List<User> getUserFriends(User user, FriendStatus friendStatus) throws RemoteException;

    /**
     * insert Or register user to the system
     *
     * @param user user to be inserted
     * @return inserted user
     * @throws RemoteException
     */
    public User insertUser(User user, String password) throws RemoteException;

    /**
     * update user data to the system
     *
     * @param user user to be updated
     * @return updated user
     * @throws RemoteException
     */
    public User updateUser(User user, String password) throws RemoteException;

    /**
     * delete user form the system
     *
     * @param id user id to be deleted
     * @return int number of row deleted
     * @throws RemoteException
     */
    public int deleteUser(int id) throws RemoteException;

    /**
     * get all online Users
     *
     * @param online online = true , offline = false;
     * @return list of online users
     * @throws RemoteException
     */
    public List<User> getOnlineUsers(boolean online) throws RemoteException;

    /**
     * add user to my friend list
     *
     * @param currentUser login user
     * @param friend      a friend to add
     * @return number of row inserted
     * @throws RemoteException
     */
    public int addFriend(User currentUser, User friend) throws RemoteException;
    public int removeFriend(int currentUser, int friend) throws RemoteException;

    /**
     *  get specific user by phone
     * @param phone user phone
     * @return user if it found or nnll if not found
     * @throws RemoteException
     */
    public User getUserByPhone(String phone) throws RemoteException;

    /**
     * validate user data aginest ruels
     *
     * @param user user to be validate his data
     * @return map of validate keys and values <String ,Boolean>
     * @throws RemoteException
     */
    Map<String, Boolean> validateUsr(User user) throws RemoteException;

    /**
     * get Status for the the 2 users
     *
     * @param currentUser id of the user
     * @param friend id for the friend
     * @return number of status it depends on his status
     * @throws RemoteException
     */
    public int getStatus(int currentUser, int friend) throws RemoteException;


    /**
     * User to be updated
     * @param user to update his mode
     * @param mode new mode
     * @return updated user
     */
    User updateUserMode(User user, Mode mode);
}
