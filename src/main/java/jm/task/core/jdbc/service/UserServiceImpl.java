package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao db = new UserDaoJDBCImpl();

    public void createUsersTable() {
        db.createUsersTable();
    }

    public void dropUsersTable() {
        db.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        db.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        db.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return db.getAllUsers();
    }

    public void cleanUsersTable() {
        db.cleanUsersTable();
    }
}
