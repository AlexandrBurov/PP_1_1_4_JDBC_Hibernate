package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        // UserService userService = new UserServiceImpl();
        // userService.createUsersTable();

        // userService.saveUser("Bob","Flow",(byte)100);
        // userService.saveUser("Nick","Chick",(byte)16);
        //userService.saveUser("Hell","Boy",(byte)62);
        // userService.removeUserById(2);

        // List<User> user = userService.getAllUsers();
        // user.forEach(s -> System.out.println(s.getName()));
        //userService.cleanUsersTable();
        // userService.dropUsersTable();


    }
}


