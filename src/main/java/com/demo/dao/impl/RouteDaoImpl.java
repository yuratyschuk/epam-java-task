package com.demo.dao.impl;

import com.demo.dao.interfaces.RouteDao;
import com.demo.exceptions.RouteException;
import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RouteDaoImpl implements RouteDao {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Route route) {

        String updateSql = "UPDATE route SET route.arrival_place_id=?, route.departure_place_id=? WHERE route.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setInt(1, route.getArrivalPlace().getId());
            preparedStatement.setInt(2, route.getDeparturePlace().getId());
            preparedStatement.setInt(3, route.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new RouteException("Error while updating Route");
            }

            logger.info("Updated successfully");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return false;
    }

    @Override
    public List<Route> getAll() {
        String getAllSql = "SELECT route.id, route.arrival_place_id, route.departure_place_id, " +
                "departure_place.name AS departure_name, arrival_place.name AS arrival_name " +
                "FROM route " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN  places arrival_place ON route.arrival_place_id = arrival_place.id";
        List<Route> routeList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                Route route = getFromDatabase(resultSet);
                routeList.add(route);
            }

            if (routeList.isEmpty()) {
                logger.error("Route table is empty");
                throw new RouteException("Route table is empty");
            }

            return routeList;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return Collections.emptyList();
    }

    @Override
    public Route getByArrivalPlaceIdAndDeparturePlaceId(Integer departurePlaceId,
                                                                  Integer arrivalPlaceId) {

        String findByArrivalPlaceIdAndDeparturePlaceIdSql = "SELECT route.id FROM route " +
                "WHERE route.departure_place_id=? AND route.arrival_place_id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement(findByArrivalPlaceIdAndDeparturePlaceIdSql)) {

            preparedStatement.setInt(1, departurePlaceId);
            preparedStatement.setInt(2, arrivalPlaceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Route route = new Route();
                    route.setId(resultSet.getInt("id"));
                    Places departurePlace = new Places();
                    departurePlace.setId(departurePlaceId);
                    Places arrivalPlace = new Places();
                    arrivalPlace.setId(arrivalPlaceId);
                    route.setArrivalPlace(arrivalPlace);
                    route.setDeparturePlace(departurePlace);

                    return route;
                }

                return new Route();
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    @Override
    public Route getById(Integer id) {
        String findByIdSql = "SELECT route.id, route.departure_place_id, route.arrival_place_id, " +
                "departure_place.name AS departure_name, arrival_place.name AS arrival_name " +
                "FROM route " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN  places arrival_place ON route.arrival_place_id = arrival_place.id WHERE route.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return getFromDatabase(resultSet);
                } else {
                    logger.error("Route with id " + id + " doesn't exists");
                    throw new RouteException("Route with id " + id + " doesn't exists");
                }

            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Route();
    }


    @Override
    public boolean deleteById(Integer id) {
        String deleteByIdSql = "DELETE FROM route WHERE route.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete route because it doesn't exists. Id " + id);
                throw new RouteException("Can't delete route because it doesn't exists. Id " + id);
            }

            logger.info("Data deleted successfully");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;
    }

    @Override
    public Route save(Route route) {
        String saveSql = "INSERT INTO route(departure_place_id, arrival_place_id) VALUES(?,?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, route.getDeparturePlace().getId());
            preparedStatement.setInt(2, route.getArrivalPlace().getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while creating new route");
                throw new RouteException("Error while creating new route");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    route.setId(resultSet.getInt(1));

                    return route;
                }


            }
            logger.info("Route saved successfully");
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return route;
    }

    @Override
    public boolean delete(Route route) {
        return false;
    }

    private Route getFromDatabase(ResultSet resultSet) throws SQLException {
        Route route = new Route();
        route.setId(resultSet.getInt("id"));

        Places arrivalPlace = new Places();
        arrivalPlace.setId(resultSet.getInt("arrival_place_id"));
        arrivalPlace.setPlaceName(resultSet.getString("arrival_name"));
        Places departurePlace = new Places();
        departurePlace.setId(resultSet.getInt("departure_place_id"));
        departurePlace.setPlaceName(resultSet.getString("departure_name"));

        route.setArrivalPlace(arrivalPlace);
        route.setDeparturePlace(departurePlace);

        return route;
    }
}
