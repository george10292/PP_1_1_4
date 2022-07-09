package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Name1", "Lastname1", (byte) 18);
        userService.saveUser("Name2", "Lastname2", (byte) 28);
        userService.saveUser("Name3", "Lastname3", (byte) 38);
        userService.saveUser("Name4", "Lastname4", (byte) 48);


        userService.getAllUsers();
        userService.removeUserById(1);

        //userService.cleanUsersTable();
        //userService.dropUsersTable();
    }
}
