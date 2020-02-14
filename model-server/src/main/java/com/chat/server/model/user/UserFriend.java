package com.chat.server.model.user;

public class UserFriend {
    private int user;
    private int friend;
    private FriendStatus friendStatus;


    public UserFriend() {
    }


    public UserFriend(int user, int friend, FriendStatus friendStatus) {
        this.user = user;
        this.friend = friend;
        this.friendStatus = friendStatus;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getFriend() {
        return friend;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public FriendStatus getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

    @Override
    public String toString() {
        return "UserFriend{" +
                "user=" + user +
                ", friend=" + friend +
                ", friendStatus =" + friendStatus +
                '}';
    }
}
