package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USERS (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(30) , " +
            "LASTNAME VARCHAR(30)," +
            "AGE INT )";

    private final static String DROP_TABLE = "DROP TABLE IF EXISTS USERS";

    private final static String SAVE_USER = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?,?,?)";

    private final static String DELETE_ID = "DELETE FROM USERS WHERE ID = ?";

    private final static String GET_ALL_USER = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";

    private final static String CLEAN_TABLE = "DELETE FROM USERS";

    private Connection connection  = getConnection();


    public UserDaoJDBCImpl() {

    }
//--------------------CREATE TABLE--------------------
    public void createUsersTable() {

  try (Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(CREATE_TABLE);

            connection.commit();

            System.out.println("Таблица USERS создана.");

        } catch (SQLException e) {

      try {
          connection.rollback();
      } catch (SQLException ex) {
          throw new RuntimeException(ex);
      }


  }

}
//--------------------DROP TABLE--------------------

    public void dropUsersTable() {

        try (Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(DROP_TABLE);

            connection.commit();

            System.out.println("Таблица USERS удалена");


        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }


//--------------------SAVE USER--------------------
    public void saveUser(String name, String lastName, byte age) {

         try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)){


            preparedStatement.setString(1, name );
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
             System.out.println("User с именем "+name+ " добавлен в базу данных");

            preparedStatement.executeUpdate();

             connection.commit();


        } catch (SQLException e){

             try {
                 connection.rollback();
             } catch (SQLException ex) {
                 throw new RuntimeException(ex);
             }
         }

    }

//--------------------DELETE USER--------------------
    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ID)){

            preparedStatement.setLong(1, id);
            System.out.println("id № "+id+" удален из базы данных");
            preparedStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

//--------------------GET ALL USER--------------------

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(GET_ALL_USER);

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);

                connection.commit();

            }

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

         return userList;
    }


//--------------------CLEAN TABLE--------------------
    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE)){

            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Таблица USERS очищена.");

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
