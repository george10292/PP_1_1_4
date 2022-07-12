package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();
    private String sql;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = SESSION_FACTORY.openSession()){
            transaction = session.beginTransaction();
            sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(ID BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(255), LastName VARCHAR(255), Age TINYINT)";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            sql = "TRUNCATE TABLE users";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
