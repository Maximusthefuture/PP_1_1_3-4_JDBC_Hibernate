package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Petya", "Vasya", (byte) 8);
        userService.saveUser("Punya", "Vasya", (byte) 16);
        userService.saveUser("Dasha", "Vasya", (byte) 24);
        userService.saveUser("Masha", "Vasya", (byte) 32);
        List<User> list = userService.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
