package com.chat.server.repository.server.user;

import com.chat.server.model.user.FriendStatus;
import com.chat.server.model.user.User;

import java.util.List;

public interface UserFriendRepository {



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
     * @param user_id who send friend req
     * @param friend_id who receive req friend
     * @param friendStatus pending
     * @return int no of rows inserted
     */
    int addNewFriend(int user_id, int friend_id, FriendStatus friendStatus);

    /**
     * update a friend status
     *
     * @param user_id      who send friend req
     * @param friend_id    who receive req friend
     * @param friendStatus approve or reject
     * @return int no of rows updated
     */
    int updateFriend(int user_id, int friend_id, FriendStatus friendStatus);

    /**
     * delete a friend from user friend list
     *
     * @param user_id , friend_id
     */
    int deleteFriend(int user_id, int friend_id);

}
