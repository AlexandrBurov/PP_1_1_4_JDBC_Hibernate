package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/GO";
    private static final String USER = "root";
    private static final String PASSWORD = "rootroot";

    private Connection connection;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
           // connection.setAutoCommit(false);
            System.out.println("Connection OK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
