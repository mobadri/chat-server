package com.chat.server.repository.server.user;

import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;

import java.util.List;

public interface UserFriendRepository {

    /**
     * get all friends by user_id
     *
     * @param userId
     * @return list<User>
     */
    List<User> getAllFriends(int userId);

    /**
     * insert a friend to user friend list
     *
     * @param userId who send friend req
     * @param friendId who receive req friend
     * @param friendStatus pending
     * @return int no of rows inserted
     */
    int addNewFriend(int userId, int friendId, FriendStatus friendStatus);

    /**
     * update a friend status
     *
     * @param userId      who send friend req
     * @param friendId    who receive req friend
     * @param friendStatus approve or reject
     * @return int no of rows updated
     */
    int updateFriend(int userId, int friendId, FriendStatus friendStatus);

    /**
     * delete a friend from user friend list
     *
     * @param userId , friendId
     */
    int deleteFriend(int userId, int friendId);

    /**
     * get friend status for current user
     * @param userId current user id
     * @param friendId friend to to search for
     * @return status
     */
    FriendStatus getUserStatus(int userId,int friendId);
}
