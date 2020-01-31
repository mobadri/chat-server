package com.chat.server.model.chat;

import com.chat.server.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class ChatGroup {
    private int id;
    private String name;
    private List<User> users = new ArrayList<>();

    public ChatGroup() {

    }

    public ChatGroup(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
