package com.demo.dao.impl;

import com.demo.dao.interfaces.StopDao;
import com.demo.exceptions.StopException;
import com.demo.model.Stop;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StopDaoImpl implements StopDao {

    private static Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Stop stop) {
        String UPDATE = "UPDATE stop SET stop.name=?, stop.duration=? WHERE stop.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, stop.getName());
            preparedStatement.setInt(2, stop.getDuration());
            preparedStatement.setInt(3, stop.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new StopException("Data in stop table can't be updated");
            }

            logger.info("Data successfully updated");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public List<Stop> getAll() {
        String GET_ALL = "SELECT stop.id, stop.name, stop.duration FROM stop";
        List<Stop> stopList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Stop stop = new Stop();
                stop.setId(resultSet.getInt("id"));
                stop.setName(resultSet.getString("name"));
                stop.setDuration(resultSet.getInt("duration"));

                stopList.add(stop);
            }

            if (stopList.isEmpty()) {
                throw new StopException("Stop table is empty");
            }

            return stopList;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }

        return null;
    }

    @Override
    public Stop getByName(String stopName) {
        String FIND_BY_STOP_NAME = "SELECT stop.id, stop.duration FROM stop WHERE stop.name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_STOP_NAME)) {

            preparedStatement.setString(1, stopName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stop stop = new Stop();
                stop.setId(resultSet.getInt("id"));
                stop.setName(resultSet.getString("name"));
                stop.setDuration(resultSet.getInt("duration"));
                return stop;
            } else {
                throw new StopException("Stop with name " + stopName + " doesn't exists");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return null;
    }

    @Override
    public Stop getById(Integer id) {
        String FIND_BY_ID = "SELECT stop.id, stop.name, stop.duration FROM stop WHERE stop.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stop stop = new Stop();
                stop.setId(resultSet.getInt("id"));
                stop.setName(resultSet.getString("name"));
                stop.setDuration(resultSet.getInt("duration"));
                return stop;
            } else {
                throw new StopException("Stop with id " + id + " doesn't exists");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return null;
    }

    @Override
    public boolean delete(Stop stop) {
        String DELETE_BY_NAME = "DELETE FROM stop WHERE stop.name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {

            preparedStatement.setString(1, stop.getName());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new StopException("Can't delete stop by name. Name " + stop.getName());
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String DELETE_BY_NAME = "DELETE FROM stop WHERE stop.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new StopException("Can't delete stop by id. Id " + id);
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;

    }

    @Override
    public boolean save(Stop stop) {
        String SAVE = "INSERT INTO stop(stop.name, stop.duration) VALUES(?,?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setString(1, stop.getName());
            preparedStatement.setInt(2, stop.getDuration());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new StopException("Error while saving stop");
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }

        return false;
    }
}
