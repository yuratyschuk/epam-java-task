package com.demo.dao.impl;

import com.demo.dao.DAO;
import com.demo.exceptions.TrainException;
import com.demo.exceptions.TripException;
import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.model.Train;
import com.demo.model.Trip;
import com.demo.utils.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TripDaoImpl implements DAO<Trip> {

    private String GET_ALL = "SELECT trip.id, trip.departure_time, trip.arrival_time, " +
            "trip.route_id, trip.ticket_price, trip.train_id, " +
            "train.train_name, train.train_number, route.arrival_place_id, " +
            "route.departure_place_id, departure_place.departure_name, " +
            "arrival_place.arrival_name, trip.number_of_carriages " +
            "FROM trip " +
            "JOIN train ON train.id = trip.train_id " +
            "JOIN route ON route.id = trip.route_id " +
            "JOIN departure_place ON route.departure_place_id = departure_place.id " +
            "JOIN arrival_place ON route.arrival_place_id = arrival_place.id";


    private static Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Trip trip) {
        String UPDATE = "UPDATE trip SET trip.departure_time=?, trip.arrival_time=?, trip.route_id=?, " +
                "trip.ticket_price=?, trip.train_id=?, trip.number_of_carriages=? WHERE trip.id=?";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setDate(1, trip.getDepartureTime());
            preparedStatement.setDate(2, trip.getArrivalTime());
            preparedStatement.setInt(3, trip.getRoute().getId());
            preparedStatement.setBigDecimal(4, trip.getTicketPrice());
            preparedStatement.setInt(5, trip.getTrain().getId());
            preparedStatement.setInt(6, trip.getNumberOfCarriages());
            preparedStatement.setInt(7, trip.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new TripException("Error while updating trip");
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean delete(Trip trip) {
        String delete = "DELETE FROM trip WHERE trip=";
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String DELETE_BY_ID = "DELETE FROM trip WHERE trip.id=?";
        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete data because it doesn't exists. Id " + id);
                throw new TripException("Can't delete data because it doesn't exists. Id " + id);
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public List<Trip> getAll() {
        List<Trip> tripList = new ArrayList<>();


        try(Connection connection = ConnectionFactory.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getInt("id"));
                trip.setDepartureTime(resultSet.getDate("departure_time"));
                trip.setArrivalTime(resultSet.getDate("arrival_time"));
                trip.setTicketPrice(resultSet.getBigDecimal("ticket_price"));
                trip.setNumberOfCarriages(resultSet.getInt("number_of_carriages"));

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

                trip.setRoute(route);
                trip.setTrain(train);

                tripList.add(trip);
            }

            if (tripList.isEmpty()) {
                logger.error("Trip table is empty");
                throw new TrainException("Trip table is empty");
            }

            return tripList;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return null;
    }

    @Override
    public Trip getById(Integer id) {
        try(Connection connection = ConnectionFactory.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL + " WHERE trip.id=?")) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Trip trip = new Trip();
                trip.setId(resultSet.getInt("id"));
                trip.setDepartureTime(resultSet.getDate("departure_time"));
                trip.setArrivalTime(resultSet.getDate("arrival_time"));
                trip.setTicketPrice(resultSet.getBigDecimal("ticket_price"));
                trip.setNumberOfCarriages(resultSet.getInt("number_of_carriages"));

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

                trip.setRoute(route);
                trip.setTrain(train);
                return trip;
            } else {
                logger.error("Trip with id " + id + " doesn't exists");
                throw new TripException("Trip with id " + id + " doesn't exists");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return null;
    }


    @Override
    public boolean save(Trip trip) {
        String SAVE = "INSERT INTO trip(departure_time, arrival_time, route_id, ticket_price, train_id, number_of_carriages) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection connection = ConnectionFactory.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setDate(1, trip.getDepartureTime());
            preparedStatement.setDate(2, trip.getArrivalTime());
            preparedStatement.setInt(3, trip.getRoute().getId());
            preparedStatement.setBigDecimal(4, trip.getTicketPrice());
            preparedStatement.setInt(5, trip.getTrain().getId());
            preparedStatement.setInt(6, trip.getNumberOfCarriages());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while creating trip");
                throw new TripException("Error while creating trip");
            }

            logger.info("data added to database");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }
}
