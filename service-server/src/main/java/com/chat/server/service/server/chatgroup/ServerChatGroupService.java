package com.chat.server.service.server.chatgroup;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerChatGroupService extends Remote {

    /**
     * get all groups from the database
     *
     * @return list of groups stored on database
     */
    public List<ChatGroup> getAllChatGroups() throws RemoteException;

    /**
     * get chat group with id
     *
     * @param id chat group id
     * @return ChatGroup by id
     */
    public ChatGroup getChatGroupByID(int id) throws RemoteException;

    /**
     * get all chat groups for a user
     *
     * @param user user that register on group
     * @return list of user groups
     */
    public List<ChatGroup> getAllChatGroupsForUser(User user) throws RemoteException;

    /**
     * insert chatGroup to database
     *
     * @param chatGroup chatGroup to insert
     * @return id of inserted chatGroup or (-1) if failed to insert
     */
    public ChatGroup insertChatGroup(ChatGroup chatGroup) throws RemoteException;

    /**
     * update chatGroup to database
     *
     * @param chatGroup chatGroup to update
     * @return integer number of row updated
     */
    public ChatGroup updateChatGroup(ChatGroup chatGroup) throws RemoteException;

    /**
     * delete chatGroup from database
     *
     * @param id chatGroup id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int deleteChatGroup(int id) throws RemoteException;

    /**
     * send message to chat group
     *
     * @param message message to be appended
     */
    Message sendMessage(Message message) throws RemoteException;

    /**
     * to add friend to chat
     *
     * @param chatGroup to add friend in this chat group
     * @param friend    friend to add
     * @return added friend
     */
    public User addFriend(ChatGroup chatGroup, User friend) throws RemoteException;

    /**
     * to remove friend to chat
     *
     * @param chatGroup to remove friend from it
     * @param friend    friend to remove
     * @return 1 if removed, 0 otherwise
     */
    public int removeFriend(ChatGroup chatGroup, User friend) throws RemoteException;

    /**
     * search for my groups
     *
     * @param groupName chat group name
     * @param user      current user
     * @return list of my groups
     */
    List<ChatGroup> searchByName(String groupName, User user) throws RemoteException;

    /**
     * to register client
     * @param clientMessageService client service to register it
     */

    public void register( clientMessageService);

    /**
     * to unRegister client
     * @param  clientMessageService client service to register it
     */

    public void unRegister(ClientMessageService clientMessageService);
}