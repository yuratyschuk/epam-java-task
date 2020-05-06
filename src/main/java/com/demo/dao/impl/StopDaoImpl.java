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
                throw new StopException("Data in stop table can't be updated");
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
                throw new StopException("Stop table is empty");
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
        String findByStopNameSql = "SELECT stop.id, stop.duration FROM stop WHERE stop.name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByStopNameSql)) {

            preparedStatement.setString(1, stopName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Stop stop = new Stop();
                    stop.setId(resultSet.getInt("id"));
                    stop.setName(resultSet.getString("name"));
                    stop.setDuration(resultSet.getInt("duration"));
                    return stop;
                } else {
                    throw new StopException("Stop with name " + stopName + " doesn't exists");
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
                    throw new StopException("Stop with id " + id + " doesn't exists");
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
    public boolean delete(Stop stop) {
        String deleteByNameSql = "DELETE FROM stop WHERE stop.name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByNameSql)) {

            preparedStatement.setString(1, stop.getName());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new StopException("Can't delete stop by name. Name " + stop.getName());
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
                throw new StopException("Can't delete stop by id. Id " + id);
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
        String saveSql = "INSERT INTO stop(stop.name, stop.duration) VALUES(?,?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql)) {

            preparedStatement.setString(1, stop.getName());
            preparedStatement.setInt(2, stop.getDuration());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new StopException("Error while saving stop");
            }

            return stop;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return stop;
    }

    @Override
    public Set<Stop> getStopByRouteId(int routeId) {
        String getStopByRouteIdSql = "SELECT stop.id, stop.name, stop.duration " +
                "FROM stop_route  " +
                "INNER JOIN stop ON stop.id = stop_route.stop_id " +
                "WHERE stop_route.route_id=?";

        Set<Stop> stopSet = new HashSet<>();

        try(Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getStopByRouteIdSql)) {

            preparedStatement.setInt(1, routeId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while(resultSet.next()) {
                    Stop stop = new Stop();
                    stop.setId(resultSet.getInt("id"));
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
