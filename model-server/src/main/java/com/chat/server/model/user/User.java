package com.chat.server.model.user;

import com.chat.server.model.chat.ChatGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private String email;
    private String country;
    private Gender gender;
    private Date dateOfBirth;
    private String BIO;
    private byte[] image;
    private List<User> friends = new ArrayList<>();
    private List<ChatGroup> chatGroups = new ArrayList<>();
    private boolean online;
    private Mode mode;

    public User() {
    }

    public User(String firstName, String lastName, String phone,
                String password, String email, String country,
                Gender gender, Date dateOfBirth, String BIO, byte[] image,
                List<User> friends, List<ChatGroup> chatGroups,
                boolean online, Mode mode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.country = country;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.BIO = BIO;
        this.image = image;
        this.friends = friends;
        this.chatGroups = chatGroups;
        this.online = online;
        this.mode = mode;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<ChatGroup> getChatGroups() {
        return chatGroups;
    }

    public void setChatGroups(List<ChatGroup> chatGroups) {
        this.chatGroups = chatGroups;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBIO() {
        return BIO;
    }

    public void setBIO(String BIO) {
        this.BIO = BIO;
    }

    public byte[] getImage() {
        return image;
    }

    /**
     * user image with maximum size of 16 MB
     * it stored on database as MEDIUMBLOB
     *
     * @param image user image
     */
    public void setImage(byte[] image) {
        if (image.length < 16777215) {
            this.image = image;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", gender=" + gender +
                ", contacts=" + friends.size() +
                ", online=" + online +
                ", mode=" + mode +
                ", Date of Birth " + dateOfBirth +
                '}';
    }
}
