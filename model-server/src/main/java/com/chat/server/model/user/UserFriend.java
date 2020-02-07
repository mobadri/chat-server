package com.chat.server.model.user;

public class UserFriend {
    private int user;
    private int friend;

    public UserFriend() {
    }

    public UserFriend(int user, int friend) {
        this.user = user;
        this.friend = friend;
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

    @Override
    public String toString() {
        return "UserFriend{" +
                "user=" + user +
                ", friend=" + friend +
                '}';
    }
}
