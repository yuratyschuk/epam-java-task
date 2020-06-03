package com.demo.dao.impl;

import com.demo.dao.interfaces.UserDao;
import com.demo.model.User;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public boolean update(User client) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        String deleteSql = "DELETE FROM users WHERE users.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {

            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;
    }


    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User user) {
        String saveSql = "INSERT INTO users (firstName, lastName,  email, password, username)" +
                " VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getUsername());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("User doesn't saved");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));

                    return user;
                }
            }

        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return null;
    }

    @Override
    public User getByEmail(String email) {
        String getByEmailSql = "SELECT users.id FROM users WHERE users.email=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByEmailSql)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User client = new User();
                    client.setId(resultSet.getInt("id"));
                    client.setEmail(email);
                    return client;
                }


            }
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public User validateLogin(String username, String password) {
        String validateLoginSql = "SELECT users.id, users.firstName, users.lastName, users.email " +
                "FROM users " +
                "WHERE users.username=? AND users.password=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(validateLoginSql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(password);
                    user.setUsername(username);

                    return user;
                }
            }
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }
}
