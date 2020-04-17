package com.demo.service;

import com.demo.dao.impl.UserDaoImpl;
import com.demo.model.User;

public class UserService {

    UserDaoImpl userDao = new UserDaoImpl();

    public User save(User client) {
        return userDao.save(client);
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User checkLogin(String username, String password) {
        return userDao.validateLogin(username, password);
    }
}
