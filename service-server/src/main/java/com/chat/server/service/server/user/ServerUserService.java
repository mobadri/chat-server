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
     */
    public List<User> getAllUsers() throws RemoteException;

    /**
     * get user by his id
     *
     * @param id user id
     * @return user if founded or null of not founded
     */
    public User getUserById(int id) throws RemoteException;

    /**
     * get user by phone and password
     *
     * @param phone    user phone
     * @param password user password
     * @return user if founded Or null if not founded
     */
    public User getByPhoneAndPassword(String phone, String password) throws RemoteException;

    /**
     * search for user by phone
     *
     * @param phone user phone
     * @return user if founded Or null if not founded
     */
    public User getByPhone(String phone) throws RemoteException;

    /**
     * get list of user friends
     *
     * @param user user to get his friends
     * @return list of users as user friends
     */
    public List<User> getUserFriends(User user) throws RemoteException;

    /**
     * insert Or register user to the system
     *
     * @param user user to be inserted
     * @return int id of user inserted 0 if failed to insert
     */
    public int insertUser(User user) throws RemoteException;

    /**
     * update user data to the system
     *
     * @param user user to be updated
     * @return int number of row updated
     */
    public int updateUser(User user) throws RemoteException;

    /**
     * delete user form the system
     *
     * @param id user id to be deleted
     * @return int number of row deleted
     */
    public int deleteUser(int id) throws RemoteException;

    /**
     * get all online Users
     *
     * @param online online = true , offline = false;
     * @return list of online users
     */
    public List<User> getOnlineUsers(boolean online) throws RemoteException;
}
