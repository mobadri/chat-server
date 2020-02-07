package com.chat.server.service.server.chatgroup;

import com.chat.client.service.client.chat.ClientChatGroupService;
import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerChatGroupService extends Remote {

    /**
     *  get all groups from the database
     * @return list of groups stored on database
     */
    public List<ChatGroup> getAllChatGroups() throws RemoteException;

    /**
     * get chat group with id
     * @param id chat group id
     * @return ChatGroup by id
     */
    public ChatGroup getChatGroupByID(int id) throws RemoteException;

    /**
     * get all chat groups for a user
     * @param user user that register on group
     * @return list of user groups
     */
    public List<ChatGroup> getAllChatGroupsForUser(User user) throws RemoteException;

    /**
     * insert chatGroup to database
     * @param chatGroup chatGroup to insert
     * @return id of inserted chatGroup or (-1) if failed to insert
     */
    public int insertChatGroup(ChatGroup chatGroup) throws RemoteException;

    /**
     * update chatGroup to database
     * @param chatGroup chatGroup to update
     * @return integer number of row updated
     */
    public int updateChatGroup(ChatGroup chatGroup) throws RemoteException;

    /**
     * delete chatGroup from database
     * @param id chatGroup id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int deleteChatGroup(int id) throws RemoteException;

    /**
     * to register client
     * @param clientChatGroupService client service to register it
     */
    public void register(ClientChatGroupService clientChatGroupService);

    /**
     * to unRegister client
     * @param  clientChatGroupService client service to register it
     */
    public void unRegister(ClientChatGroupService clientChatGroupService);
}
