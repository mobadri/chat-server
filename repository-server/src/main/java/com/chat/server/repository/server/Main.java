package com.chat.server.repository.server;

import com.chat.server.model.chat.ChatGroup;
import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
import com.chat.server.repository.server.chat.ChatGroupRepository;
import com.chat.server.repository.server.factory.RepositoryServerFactory;
import com.chat.server.repository.server.user.UserRepository;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //-------------------------------
        // @noura
        //todo test all user repo methods

        UserRepository userRepository = RepositoryServerFactory.creatUserRepository();
        List<User> all = userRepository.findAll();
        System.out.println("test find all");
        System.out.println(all.size());
        all.forEach(System.out::println);

        User user = userRepository.findById(2);
        System.out.println("phone number of user's id  = 2");
        if (user != null)
            System.out.println(user);
        else
            System.out.println("not found");

        user = userRepository.findById(91);
        System.out.print("\nphone number of user's id  = 91 : ");
        if (user != null)
            System.out.println(user.getPhone() + user.getFriends().size());
        else
            System.out.println("not found");

        all = userRepository.findAllUserFriends(user);
        System.out.println("friends for user's id = 91 : " + all.size());
        all.forEach(System.out::println);

        user = userRepository.findByPhone("011111111");
        System.out.println("\nname of user who has phone = 011111111 : " + user);

        user = userRepository.findByPhoneAndPassword("011111111", "aaaaa");
        System.out.println("\nname of user who has phone = 011111111, password = aaaaa : " + user);

        user = userRepository.findByPhoneAndPassword("2198", "1234");
        System.out.println("name of user who has phone = 2198, password = 1234 : " + user);

        User newUser = new User();
        newUser.setFirstName("ahmed");
        newUser.setLastName("adel");
        newUser.setPassword("2345");
        newUser.setPhone("01111");
        newUser.setEmail("a");
        newUser.setCountry("egypt");
        newUser.setGender(Gender.MALE);
        newUser.setDateOfBirth(new Date());
        newUser.setOnline(true);
        newUser.setMode(Mode.BUSY);

        System.out.println("\ntest insert");
        int id = userRepository.insertUser(newUser);
        if (id == -1) {
            System.out.println("error");
        } else {
            System.out.println("done");
            newUser.setId(id);
        }

        System.out.println("\ntest update");
        newUser.setFirstName("7mada");
        int updated = userRepository.updateUser(newUser);
        if (updated != 0) {
            System.out.println("done");
        } else {
            System.out.println("error");
        }

        System.out.println("\ntest delete");
        int deleted = userRepository.delete(97);
        if (deleted != 0) {
            System.out.println("done");
        } else {
            System.out.println("not found");
        }

        //-------------------------------
        //@shaheen
        //todo test all chat repo methods
        ChatGroupRepository chatGroupRepository = RepositoryServerFactory.creatChatRepository();
        /*ChatGroup chatGroup = new ChatGroup();
        chatGroup.setName("First Group");
        chatGroupRepository.insertChatGroup(chatGroup);*/
        List<ChatGroup> allChatGroups = chatGroupRepository.getAllChatGroups();
        for (ChatGroup group : allChatGroups) {
            System.out.println(group.getId() + " " + group.getName());
        }
        System.out.println(chatGroupRepository.getChatGroupByID(1).getName());

        //-------------------------------
        // @badri
        //todo test all user repo methods
    }
}
