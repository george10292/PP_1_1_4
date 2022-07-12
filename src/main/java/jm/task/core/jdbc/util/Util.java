package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.Properties;

public class Util  {
    private static final String URL = "jdbc:mysql://localhost:3306/user";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static final SessionFactory SESSION_FACTORY;

    static {
        connection = null;
        try  {
            connection = DriverManager.getConnection (URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", URL);
            properties.setProperty("hibernate.connection.username", LOGIN);
            properties.setProperty("hibernate.connection.password", PASSWORD);
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            SESSION_FACTORY = new Configuration()
                    .addAnnotatedClass(User.class)
                    .setProperties(properties)
                    .buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
