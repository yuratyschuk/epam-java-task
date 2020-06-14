package com.demo.service;

import com.demo.dao.interfaces.UserDao;
import com.demo.model.User;
import com.demo.validation.Validation;
import com.demo.validation.ValidationEnum;

import static com.demo.validation.Validation.*;
import static com.demo.validation.Validation.userNameValidation;
import static com.demo.validation.ValidationEnum.*;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String save(User user) {
        ValidationEnum result = emailValidation()
                .and(passwordSizeValidation())
                .and(passwordValidation())
                .and(userNameValidation())
                .apply(user);
        if (result.equals(SUCCESS)) {
            userDao.save(user);
        } else if (result.equals(PASSWORD_NOT_VALID)) {
            return "Password must contain: " +
                    "1. Capital letter;" +
                    "2. 8 letters size;" +
                    "3. Digits";
        } else if (result.equals(USERNAME_NOT_VALID)) {
            return "Username must be 6 characters length";
        } else if (result.equals(EMAIL_NOT_VALID)) {
            return "Email not valid";
        }

        return "";
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User checkLogin(String username, String password) {
        return userDao.validateLogin(username, password);
    }

    public String update(User user) {
        ValidationEnum result = emailValidation()
                .and(userNameValidation())
                .apply(user);
        if (result.equals(SUCCESS)) {
            userDao.update(user);
        } else if (result.equals(USERNAME_NOT_VALID)) {
            return "Username must be 6 characters length";
        } else if (result.equals(EMAIL_NOT_VALID)) {
            return "Email not valid";
        }

        return "";
    }

    public boolean delete(User user) {
        return userDao.delete(user);
    }

    public boolean changePassword(User user, String repeatedPassword) {
        ValidationEnum result =
                passwordSizeValidation()
                        .and(passwordValidation())
                        .apply(user);

        if (result.equals(SUCCESS)) {
            return userDao.update(user);
        } else {
            return false;
        }
    }

    public  boolean deleteById(int id) {
        return userDao.deleteById(id);
    }

    public User getById(int id) {
        return userDao.getById(id);
    }
}
