package com.chat.server.repository.server.user;

import com.chat.server.model.user.User;

import java.util.List;

public interface UserFriendRepository {
    String SELECT_ALL_FRIENDS_BY_USER_ID = "SELECT * FROM USER_FRIENDS WHERE USER_ID = ?";
    String INSERT_FRIEND = "INSERT INTO USER_FRIENDS VALUES (USER_ID = ? , FRIEND_ID = ? )";
    String DELETE_FRIEND = "DELETE FROM USER_FRIENDS WHERE (USER_ID = ? AND FRIEND_ID = ?) OR " +
            "(FRIEND_ID = ? AND USER_ID = ?)";

    /**
     * get all friends by user_id
     *
     * @param user_id
     * @return list<User>
     */
    List<User> getAllFriends(int user_id);

    /**
     * insert a friend to user friend list
     *
     * @param user_id , friend_id
     */
    boolean addNewFriend(int user_id, int friend_id);

    /**
     * delete a friend from user friend list
     *
     * @param user_id , friend_id
     */
    boolean deleteFreind(int user_id, int friend_id);

}
