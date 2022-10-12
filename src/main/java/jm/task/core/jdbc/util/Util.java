package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final String URL_U = "jdbc:mysql://localhost:3306/GO";
    private static final String USER_U = "root";
    private static final String PASSWORD_U = "rootroot";

//---------------------------------------------------
    private  static Connection connection;

//---------------------------------------------------
    private static SessionFactory sessionFactory;
//---------------------------------------------------

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {

                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL_U);
                settings.put(Environment.USER, USER_U);
                settings.put(Environment.PASS, PASSWORD_U);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                Configuration configuration = new Configuration();
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                System.out.println("Connection OK");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

//---------------------------------------------------
    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection(URL_U, USER_U, PASSWORD_U);

            connection.setAutoCommit(false);

            System.out.println("Connection OK");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
