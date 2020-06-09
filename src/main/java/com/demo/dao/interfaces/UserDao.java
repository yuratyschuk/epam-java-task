package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.User;

import java.util.Optional;

public interface UserDao extends DAO<User> {

    User getByEmail(String email);

    User validateLogin(String username, String password);
}
