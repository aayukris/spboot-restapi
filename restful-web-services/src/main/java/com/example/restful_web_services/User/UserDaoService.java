package com.example.restful_web_services.User;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int count = 0;
    static {
        users.add(new User(++count, LocalDate.now().minusDays(90).minusYears(25), "Adam"));
        users.add(new User(++count, LocalDate.now().minusDays(120).minusYears(16), "Adama"));
        users.add(new User(++count, LocalDate.now().minusDays(209).minusYears(5), "Adamasa"));
    }

    public List<User> findAll() {
        return users;
    }
    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteOne(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
        users.removeIf(predicate);
    }

    public User save(User user){
        user.setId(++count);
        users.add(user);
        return user;
    }
    }
   