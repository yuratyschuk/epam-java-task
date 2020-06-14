package com.demo;

import com.demo.dao.impl.TicketDaoImpl;
import com.demo.dao.impl.UserDaoImpl;
import com.demo.dao.interfaces.UserDao;
import com.demo.model.User;
import com.demo.validation.Validation;
import com.demo.validation.ValidationEnum;

import java.util.regex.Pattern;

import static com.demo.validation.Validation.*;

public class Application {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        User user = new User();

        user.setLastName("test user");
        user.setFirstName("test user");
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setUsername("username");


        user = userDao.save(user);

        System.out.println(userDao.getById(user.getId()));
    }
}
