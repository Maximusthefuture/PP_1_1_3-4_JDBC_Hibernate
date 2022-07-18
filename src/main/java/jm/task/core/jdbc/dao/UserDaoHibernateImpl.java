package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private final Session session;
    private final Transaction transaction;

    public UserDaoHibernateImpl() {
        session = Util.getSessionFactory().openSession();
        transaction = session.getTransaction();
    }

    @Override
    public void createUsersTable() {
        transaction.begin();
        session.createSQLQuery("create table if not exists users " +
                "(id int not null auto_increment, userName varchar(58)," +
                "lastName varchar(100), age smallint, primary key (id))").executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        try {
            transaction.begin();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
//            Util.getSessionFactory().close();
            session.clear();
            transaction.rollback();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
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
        try {
            transaction.begin();
            User user = session.load(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                session.close();
            }

        } catch (Exception ex) {
            session.clear();
            ex.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        transaction.begin();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Query query = session.createQuery(criteriaQuery);
        List<User> list = query.getResultList();
        transaction.commit();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        transaction.begin();
        session.createSQLQuery("truncate users").executeUpdate();
        transaction.commit();
        session.clear();
    }
}
