package com.demo.dao.impl;

import com.demo.dao.interfaces.TripDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.model.Train;
import com.demo.model.Trip;
import com.demo.model.utils.TrainType;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TripDaoImpl implements TripDao {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Trip trip) {
        String updateSql = "UPDATE trip SET trip.departure_time=?, trip.arrival_time=?, trip.route_id=?, " +
                "trip.ticket_price=?, trip.train_id=?, trip.number_of_carriages=?, trip.number_of_places=? WHERE trip.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            setPreparedStatement(trip, preparedStatement);
            preparedStatement.setInt(8, trip.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new DataInsertException("Error while updating trip");
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
    public boolean delete(Trip trip) {
        String deleteSql = "DELETE FROM trip WHERE trip.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, trip.getId());
            int checkIfNotNull = preparedStatement.executeUpdate();

            if (checkIfNotNull == 0) {
                logger.error("Can't delete trip with id: " + trip.getId());
                throw new DataNotFoundException("Error while deleting data");
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
        String deleteById = "DELETE FROM trip WHERE trip.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteById)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete data because it doesn't exists. Id " + id);
                throw new DataNotFoundException("Can't delete data because it doesn't exists. Id " + id);
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
    public List<Trip> getAll() {
        List<Trip> tripList = new ArrayList<>();
        String getAllSql = "SELECT trip.id, trip.departure_time, trip.arrival_time, " +
                "trip.route_id, trip.ticket_price, trip.train_id, trip.number_of_places, " +
                "train.train_name, train.train_number, train.type, route.arrival_place_id, " +
                "route.departure_place_id, departure_place.name AS departure_name, " +
                "arrival_place.name AS arrival_name, trip.number_of_carriages " +
                "FROM trip " +
                "JOIN train ON train.id = trip.train_id " +
                "JOIN route ON route.id = trip.route_id " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN places arrival_place ON route.arrival_place_id = arrival_place.id";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Trip trip = getTripFromDataBase(resultSet);

                tripList.add(trip);
            }


            return tripList;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Trip> getByRouteId(int routeId) {
        List<Trip> tripList = new ArrayList<>();
        String getByRouteIdSql = "SELECT trip.id, trip.departure_time, trip.arrival_time, " +
                "trip.route_id, trip.ticket_price, trip.train_id, trip.number_of_places, " +
                "train.train_name, train.train_number, train.type, route.arrival_place_id, " +
                "route.departure_place_id, departure_place.name AS departure_name, " +
                "arrival_place.name AS arrival_name, trip.number_of_carriages " +
                "FROM trip " +
                "JOIN train ON train.id = trip.train_id " +
                "JOIN route ON route.id = trip.route_id " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN places arrival_place ON route.arrival_place_id = arrival_place.id " +
                "WHERE trip.route_id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByRouteIdSql)) {
            preparedStatement.setInt(1, routeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Trip trip = getTripFromDataBase(resultSet);
                    tripList.add(trip);
                }

                if (tripList.isEmpty()) {
                    logger.error("Trips with route " + routeId + " not found");
                    throw new DataNotFoundException("Trips with route " + routeId + " not found");
                }

                return tripList;
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return null;
    }

    @Override
    public Trip getById(Integer id) {
        String getByIdSql = "SELECT trip.id, trip.departure_time, trip.arrival_time, " +
                "trip.route_id, trip.ticket_price, trip.train_id, trip.number_of_places, " +
                "train.train_name, train.train_number, train.type, route.arrival_place_id, " +
                "route.departure_place_id, departure_place.name AS departure_name, " +
                "arrival_place.name AS arrival_name, trip.number_of_carriages " +
                "FROM trip " +
                "JOIN train ON train.id = trip.train_id " +
                "JOIN route ON route.id = trip.route_id " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN places arrival_place ON route.arrival_place_id = arrival_place.id " +
                "WHERE trip.id=? ";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getTripFromDataBase(resultSet);
                } else {
                    logger.error("Trip with id " + id + " doesn't exists");
                    throw new DataNotFoundException("Trip with id " + id + " doesn't exists");
                }
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Trip();
    }


    @Override
    public Trip save(Trip trip) {
        String saveSql = "INSERT INTO trip(departure_time, arrival_time, route_id, ticket_price, train_id, " +
                "number_of_carriages, number_of_places) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            setPreparedStatement(trip, preparedStatement);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while creating trip");
                throw new DataInsertException("Error while creating trip");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    trip.setId(resultSet.getInt(1));

                    return trip;
                }
            }


            logger.info("data added to database");
            return trip;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    @Override
    public boolean saveToStopTripTable(int tripId, int stopId) {
        String saveSql = "INSERT INTO stop_trip(stop_trip.trip_id, stop_trip.stop_id) VALUES(?, ?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql)) {

            preparedStatement.setInt(1, tripId);
            preparedStatement.setInt(2, stopId);

            preparedStatement.executeUpdate();

            logger.info("Data saved");

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    private void setPreparedStatement(Trip trip, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, trip.getDepartureTime());
        preparedStatement.setObject(2, trip.getArrivalTime());
        preparedStatement.setInt(3, trip.getRoute().getId());
        preparedStatement.setBigDecimal(4, trip.getTicketPrice());
        preparedStatement.setInt(5, trip.getTrain().getId());
        preparedStatement.setInt(6, trip.getNumberOfCarriages());
        preparedStatement.setInt(7, trip.getNumberOfPlaces());
    }

    private Trip getTripFromDataBase(ResultSet resultSet) throws SQLException {
        Trip trip = new Trip();
        trip.setId(resultSet.getInt("id"));
        trip.setDepartureTime(resultSet.getDate("departure_time"));
        trip.setArrivalTime(resultSet.getDate("arrival_time"));
        trip.setTicketPrice(resultSet.getBigDecimal("ticket_price"));
        trip.setNumberOfCarriages(resultSet.getInt("number_of_carriages"));
        trip.setNumberOfPlaces(resultSet.getInt("number_of_places"));

        Route route = new Route();
        route.setId(resultSet.getInt("route_id"));
        Places departurePlace = new Places();
        departurePlace.setId(resultSet.getInt("departure_place_id"));
        departurePlace.setPlaceName(resultSet.getString("departure_name"));
        Places arrivalPlace = new Places();
        arrivalPlace.setId(resultSet.getInt("arrival_place_id"));
        arrivalPlace.setPlaceName(resultSet.getString("arrival_name"));
        route.setDeparturePlace(departurePlace);
        route.setArrivalPlace(arrivalPlace);

        Train train = new Train();
        train.setId(resultSet.getInt("train_id"));
        train.setTrainName(resultSet.getString("train_name"));
        train.setTrainNumber(resultSet.getString("train_number"));
        TrainType trainType = TrainType.valueOf(resultSet.getString("type"));
        train.setTrainType(trainType);


        trip.setRoute(route);
        trip.setTrain(train);
        return trip;
    }
}
