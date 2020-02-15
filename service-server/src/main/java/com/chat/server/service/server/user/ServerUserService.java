package com.chat.server.service.server.user;

import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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
     * @param fulldata boolean to get all user data with friends and chatgroups
     * @return user if founded or null of not founded
     * @throws RemoteException
     */
    public User getUserById(int id, boolean fulldata) throws RemoteException;

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
    public List<User> getUserFriends(User user) throws RemoteException;

    /**
     * insert Or register user to the system
     *
     * @param user user to be inserted
     * @return inserted user
     * @throws RemoteException
     */
    public User insertUser(User user) throws RemoteException;

    /**
     * update user data to the system
     *
     * @param user user to be updated
     * @return updated user
     * @throws RemoteException
     */
    public User updateUser(User user) throws RemoteException;

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
     */
    public int addFriend(User currentUser, User friend) throws RemoteException;

}
