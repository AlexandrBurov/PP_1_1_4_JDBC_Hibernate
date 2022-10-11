package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


public class UserDaoHibernateImpl extends Util implements UserDao {

    private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USERS (" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(30) , " +
            "LASTNAME VARCHAR(30)," +
            "AGE INT )";

    private final static String DROP_TABLE = "DROP TABLE IF EXISTS USERS";

    private Transaction transaction = null;

//--------------------CONSTRUCTOR--------------------

    public UserDaoHibernateImpl() {

    }

//--------------------CREATE TABLE--------------------


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Query query = session.createSQLQuery(CREATE_TABLE).addEntity(User.class);

            query.executeUpdate();

            transaction.commit();

            System.out.println("Таблица USERS создана.");


        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }
            e.printStackTrace();
        }

    }



//--------------------DROP TABLE--------------------


    @Override
    public void dropUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Query query = session.createSQLQuery(DROP_TABLE).addEntity(User.class);

            query.executeUpdate();

            transaction.commit();

            System.out.println("Таблица USERS удалена");


        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }
            e.printStackTrace();
        }
    }



//--------------------SAVE USER--------------------

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            System.out.println("User с именем "+name+ " добавлен в базу данных");

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
            e.printStackTrace();

        }
    }




//--------------------DELETE USER BY ID--------------------
    @Override
    public void removeUserById(long id) {

        try (Session session = getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Query query = session.createQuery("delete User where id = :param");

            query.setParameter("param", id);

            int result = query.executeUpdate();

            System.out.println("User c id № " + id + " удален из базы данных");

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }

    }




//--------------------GET ALL USER--------------------

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();


        try (Session session = getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            users = session.createQuery("from User").getResultList();

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }

        return users;
    }





//--------------------CLEAN TABLE--------------------

    @Override
    public void cleanUsersTable() {

        try (Session session = getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.createSQLQuery("DELETE FROM USERS").executeUpdate();

            System.out.println("Таблица USERS очищена.");

            transaction.commit();


        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();
            }
        }



    }
}
