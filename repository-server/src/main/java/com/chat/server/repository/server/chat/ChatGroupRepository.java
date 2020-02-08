package com.chat.server.repository.server.chat;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.chat.Message;
import com.chat.server.model.user.User;

import java.util.List;

public interface ChatGroupRepository {
     String SELECT_ALL_CHAT_GROUP = "SELECT * FROM CHAT_GROUP";

     String SELECT_CHAT_GROUP_BY_ID = "SELECT * FROM CHAT_GROUP WHERE ID = ?";

     String SELECT_ALL_CHAT_GROUPS_BY_USER_ID = "SELECT * FROM CHAT_GROUP " +
             "INNER join GROUP_USER " +
             "ON CHAT_GROUP.ID =GROUP_USER.GROUP_ID " +
             "WHERE GROUP_USER.USER_ID = ?";

    String INSERT_CHAT_GROUP= "INSERT INTO CHAT_GROUP (GROUP_NAME) VALUES (?)";

    String UPDATE_CHAT_GROUP= "UPDATE CHAT_GROUP SET GROUP_NAME = ? WHERE ID = ?";

    String DELETE_CHAT_GROUP = "DELETE FROM CHAT_GROUP WHERE ID = ?";


    /**
     *  get all groups from the database
     * @return list of groups stored on database
     */
    public List<ChatGroup> getAllChatGroups();

    /**
     * get chat group with id
     * @param id chat group id
     * @return ChatGroup by id
     */
    public ChatGroup getChatGroupByID(int id);

    /**
     * get all chat groups for a user
     * @param user user that register on group
     * @return list of user groups
     */
    public List<ChatGroup> getAllChatGroupsForUser(User user);

    /**
     * insert chatGroup to database
     * @param chatGroup chatGroup to insert
     * @return id of inserted chatGroup or (-1) if failed to insert
     */
    public ChatGroup insertChatGroup(ChatGroup chatGroup);

    /**
     * update chatGroup to database
     * @param chatGroup chatGroup to update
     * @return integer number of row updated
     */
    public ChatGroup updateChatGroup(ChatGroup chatGroup);

    /**
     * delete chatGroup from database
     * @param id chatGroup id to be deleted
     * @return integer number of row deleted or 0 if not deleted
     */
    public int deleteChatGroup(int id);

    /**
     * to add friend to chat
     * @param chatGroup to add friend in this chat group
     * @param friend friend to add
     * @return added friend
     */
    public User addFriend(ChatGroup chatGroup, User friend);

    /**
     * to remove friend to chat
     * @param chatGroup to remove friend from it
     * @param friend friend to remove
     * @return 1 if removed, 0 otherwise
     */
    public int removeFriend(ChatGroup chatGroup, User friend);

    /**
     * search for my groups
     * @param groupName chat group name
     * @param user current user
     * @return list of my groups
     */
    List<ChatGroup> searchByName(String groupName, User user);
}