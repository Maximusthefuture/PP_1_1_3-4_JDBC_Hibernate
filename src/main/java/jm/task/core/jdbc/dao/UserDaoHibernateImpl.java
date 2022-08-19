package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("create table if not exists users " +
                    "(id int not null auto_increment, userName varchar(58)," +
                    "lastName varchar(100), age smallint, primary key (id))").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.getTransaction();
            transaction.begin();
            User user = session.load(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                session.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            Query query = session.createQuery(criteriaQuery);
            List<User> list = query.getResultList();
            transaction.commit();
            return list;
        } catch (Exception ex) {
            transaction.rollback();
            return Arrays.asList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("truncate users").executeUpdate();
            transaction.commit();
            session.clear();
        } catch (Exception ex) {
            transaction.rollback();
        }
    }
}
