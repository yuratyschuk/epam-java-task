package com.demo.dao.impl;

import com.demo.dao.interfaces.RouteDao;
import com.demo.exceptions.RouteException;
import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private static Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Route route) {

        String UPDATE = "UPDATE route SET route.arrival_place_id=?, route.departure_place_id=? WHERE route.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new RouteException("Error while updating Route");
            }

            logger.info("Updated successfully");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }

        return false;
    }

    @Override
    public List<Route> getAll() {
        String GET_ALL = "SELECT route.id, route.arrival_place_id, route.departure_place_id, " +
                "departure_place.departure_name, arrival_place.arrival_name FROM route JOIN departure_place " +
                "ON route.departure_place_id = departure_place.id " +
                "JOIN  arrival_place ON route.arrival_place_id = arrival_place.id";
        List<Route> routeList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt("id"));

                Places arrivalPlace = new Places();
                arrivalPlace.setId(resultSet.getInt("departure_place_id"));
                arrivalPlace.setPlaceName(resultSet.getString("departure_name"));
                Places departurePlace = new Places();
                departurePlace.setId(resultSet.getInt("arrival_place_id"));
                departurePlace.setPlaceName(resultSet.getString("arrival_name"));

                route.setArrivalPlace(arrivalPlace);
                route.setDeparturePlace(departurePlace);
                routeList.add(route);
            }

            if (routeList.isEmpty()) {
                logger.error("Route table is empty");
                throw new RouteException("Route table is empty");
            }

            return routeList;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return null;
    }

    @Override
    public Route getByArrivalPlaceIdAndDeparturePlaceId(Integer departurePlaceId, Integer arrivalPlaceId) {
        String FIND_BY_ARRIVAL_PLACE_ID_AND_DEPARTURE_PLACE_ID = "SELECT route.id FROM route " +
                "WHERE route.departure_place_id=? AND route.arrival_place_id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement(FIND_BY_ARRIVAL_PLACE_ID_AND_DEPARTURE_PLACE_ID)) {

            preparedStatement.setInt(1, departurePlaceId);
            preparedStatement.setInt(2, arrivalPlaceId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt("id"));
                return route;
            } else {
                throw new RouteException("Route not found");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return null;
    }

    @Override
    public Route getById(Integer id) {
        String FIND_BY_ID = "SELECT route.id, route.departure_place_id, route.arrival_place_id, " +
                "departure_place.departure_name, arrival_place.arrival_name FROM route JOIN departure_place " +
                "ON route.departure_place_id = departure_place.id " +
                "JOIN  arrival_place ON route.arrival_place_id = arrival_place.id WHERE route.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt("id"));

                Places arrivalPlace = new Places();
                arrivalPlace.setId(resultSet.getInt("departure_place_id"));
                arrivalPlace.setPlaceName(resultSet.getString("departure_name"));
                Places departurePlace = new Places();
                departurePlace.setId(resultSet.getInt("arrival_place_id"));
                departurePlace.setPlaceName(resultSet.getString("arrival_name"));

                route.setArrivalPlace(arrivalPlace);
                route.setDeparturePlace(departurePlace);

                return route;
            } else {
                logger.error("Route with id " + id + " doesn't exists");
                throw new RouteException("Route with id " + id + " doesn't exists");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return null;
    }


    @Override
    public boolean deleteById(Integer id) {
        String DELETE_BY_ID = "DELETE FROM route WHERE route.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete route because it doesn't exists. Id " + id);
                throw new RouteException("Can't delete route because it doesn't exists. Id " + id);
            }

            logger.info("Data deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean save(Route route) {
        String SAVE = "INSERT INTO route(route.departure_place_id, route.arrival_place_id) VALUES(?,?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            if (!route.getDeparturePlace().getPlaceName().equals(route.getArrivalPlace().getPlaceName())) {
                preparedStatement.setInt(1, route.getDeparturePlace().getId());
                preparedStatement.setInt(2, route.getArrivalPlace().getId());

                int checkIfNotNull = preparedStatement.executeUpdate();
                if (checkIfNotNull == 0) {
                    logger.error("Error while creating new route");
                    throw new RouteException("Error while creating new route");
                }
            } else {
                logger.error("Departure place and arrival place is equal");
                throw new RouteException("Departure place and arrival place is equal");
            }

            logger.info("Route saved successfully");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public boolean delete(Route route) {
        return false;
    }
}
