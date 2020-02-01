package com.chat.server.repository.server;

import com.chat.server.model.user.Gender;
import com.chat.server.model.user.Mode;
import com.chat.server.model.user.User;
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

        User user ;/*= userRepository.findById(2L);
        System.out.println("phone number of user's id  = 2");
        System.out.println(user.getPhone());*/

        user = userRepository.findById(91L);
        System.out.println("\nphone number of user's id  = 91");
        System.out.println(user.getPhone());

        all = userRepository.findAllUserFriends(user);
        System.out.println("friends for user's id = 91");
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
        newUser.setEmail("a");
        newUser.setCountry("egypt");
        newUser.setGender(Gender.MALE);
        newUser.setDateOfBirth(new Date());
        newUser.setOnline(true);
        newUser.setMode(Mode.BUSY);

        System.out.println("\ntest insert");
        int id = userRepository.insertUser(newUser);
        if (id == -1) {
            System.out.println("done");
        } else {
            System.out.println("error");
        }

        System.out.println("\ntest update");
        user.setFirstName("7mada");
        int updated = userRepository.updateUser(user);
        if (updated == 0) {
            System.out.println("done");
        } else {
            System.out.println("error");
        }

        // wait to min to check the database
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\ntest update");
        int deleted = userRepository.delete(92);
        if (deleted == 0) {
            System.out.println("done");
        } else {
            System.out.println("error");
        }

        //-------------------------------
        //@shaheen
        //todo test all chat repo methods


    }
}
