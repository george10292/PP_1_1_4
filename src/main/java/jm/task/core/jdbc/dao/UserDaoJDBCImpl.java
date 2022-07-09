package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE if not exists users(" +
            "id int AUTO_INCREMENT PRIMARY KEY not null ," +
            "name varchar(30)," +
            "lastname varchar(30)," +
            "age tinyint UNSIGNED)";
    private static final String DROP = "DROP TABLE users";
    private static final String SAVE = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";
    private  static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String SELECT = "SELECT * FROM users";
    private static final String CLEAR = "DELETE FROM users";
    private static final Connection connection;

    static {

        connection = Util.getConnection();

    }
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(DROP);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte( 3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("User c именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet res = statement.executeQuery(SELECT);
            while (res.next()) {
                list.add(new User(res.getString(2), res.getString(3), res.getByte(4)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(CLEAR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
