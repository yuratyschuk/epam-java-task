package com.demo.dao.impl;

import com.demo.dao.interfaces.StopDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Stop;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class StopDaoImpl implements StopDao {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Stop stop) {
        String updateSql = "UPDATE stop SET stop.name=?, stop.duration=? WHERE stop.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, stop.getName());
            preparedStatement.setInt(2, stop.getDuration());
            preparedStatement.setInt(3, stop.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new DataInsertException("Data in stop table can't be updated");
            }

            logger.info("Data successfully updated");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;
    }

    @Override
    public List<Stop> getAll() {
        String getAllSql = "SELECT stop.id, stop.name, stop.duration FROM stop";
        List<Stop> stopList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Stop stop = new Stop();
                stop.setId(resultSet.getInt("id"));
                stop.setName(resultSet.getString("name"));
                stop.setDuration(resultSet.getInt("duration"));

                stopList.add(stop);
            }

            if (stopList.isEmpty()) {
                throw new DataNotFoundException("Stop table is empty");
            }

            return stopList;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return Collections.emptyList();
    }

    @Override
    public Stop getByName(String stopName) {
        String findByStopNameSql = "SELECT stop.id, stop.duration" +
                " FROM stop WHERE stop.name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByStopNameSql)) {

            preparedStatement.setString(1, stopName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Stop stop = new Stop();
                    stop.setId(resultSet.getInt("id"));
                    stop.setName(stopName);
                    stop.setDuration(resultSet.getInt("duration"));
                    return stop;
                } else {
                    logger.error("Stop with name " + stopName + " doesn't exists");
                    throw new DataNotFoundException("Stop with name " + stopName + " doesn't exists");
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
    public Stop getById(Integer id) {
        String findByIdSql = "SELECT stop.id, stop.name, stop.duration FROM stop WHERE stop.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Stop stop = new Stop();
                    stop.setId(resultSet.getInt("id"));
                    stop.setName(resultSet.getString("name"));
                    stop.setDuration(resultSet.getInt("duration"));
                    return stop;
                } else {
                    logger.error("Stop with id " + id + " doesn't exists");
                    throw new DataNotFoundException("Stop with id " + id + " doesn't exists");
                }
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Stop();
    }

    @Override
    public boolean delete(Stop stop) {
        String deleteByNameSql = "DELETE FROM stop WHERE stop.name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByNameSql)) {

            preparedStatement.setString(1, stop.getName());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new DataNotFoundException("Can't delete stop by name. Name " + stop.getName());
            }

            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String deleteByNameSql = "DELETE FROM stop WHERE stop.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByNameSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new DataNotFoundException("Can't delete stop by id. Id " + id);
            }

            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;

    }

    @Override
    public Stop save(Stop stop) {
        String saveSql = "INSERT INTO stop(name, duration) VALUES(?,?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, stop.getName());
            preparedStatement.setInt(2, stop.getDuration());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new DataInsertException("Error while saving stop");
            }

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    stop.setId(resultSet.getInt(1));

                    return stop;
                }
            }

        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return stop;
    }

    @Override
    public Set<Stop> getStopByRouteId(int routeId) {
        String getStopByRouteIdSql = "SELECT  stop.name, stop.duration " +
                "FROM stop_trip  " +
                "INNER JOIN stop ON stop.id = stop_trip.stop_id " +
                "WHERE stop_trip.trip_id=?";

        Set<Stop> stopSet = new HashSet<>();

        try(Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getStopByRouteIdSql)) {

            preparedStatement.setInt(1, routeId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while(resultSet.next()) {
                    Stop stop = new Stop();
                    stop.setId(routeId);
                    stop.setName(resultSet.getString("name"));
                    stop.setDuration(resultSet.getInt("duration"));

                    stopSet.add(stop);
                }



                return stopSet;
            }
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return Collections.emptySet();
    }

}
