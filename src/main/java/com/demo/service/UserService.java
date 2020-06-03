package com.demo.service;

import com.demo.dao.interfaces.UserDao;
import com.demo.model.User;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User save(User client) {
        return userDao.save(client);
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User checkLogin(String username, String password) {
        return userDao.validateLogin(username, password);
    }

    public boolean delete(User user) {
        return userDao.delete(user);
    }
}
