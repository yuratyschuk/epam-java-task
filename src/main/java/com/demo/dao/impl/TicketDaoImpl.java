package com.demo.dao.impl;

import com.demo.dao.TicketDao;
import com.demo.exceptions.TicketException;
import com.demo.model.*;
import com.demo.utils.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    private static Logger logger = LogManager.getLogger();

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public boolean update(Ticket ticket) {
        String UPDATE = "UPDATE ticket SET ticket.trip_id=?, ticket.time_when_bought=? WHERE ticket.id=?";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setInt(1, ticket.getTrip().getId());
            preparedStatement.setDate(2, ticket.getTimeWhenTicketWasBought());
            preparedStatement.setInt(3, ticket.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new TicketException("Exception while updating ticket");
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }

        return false;
    }

    @Override
    public List<Ticket> getAll() {
        String GET_ALL = "SELECT ticket.id, ticket.trip_id, ticket.time_when_bought, trip.departure_time, " +
                "trip.arrival_time, trip.ticket_price, trip.train_id, trip.route_id, " +
                "route.departure_place_id, route.arrival_place_id, arrival_place.arrival_name, " +
                "departure_place.departure_name, train.train_name, train.train_number, " +
                "train.max_number_of_carriages FROM ticket " +
                "JOIN trip ON ticket.trip_id = trip.id " +
                "JOIN route ON trip.route_id = route.id " +
                "JOIN departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN arrival_place ON route.arrival_place_id = arrival_place.id " +
                "JOIN train ON trip.train_id = train.id";

        List<Ticket> ticketList = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setTimeWhenTicketWasBought(resultSet.getDate("time_when_bought"));

                Trip trip = new Trip();
                trip.setId(resultSet.getInt("trip_id"));
                trip.setDepartureTime(resultSet.getDate("departure_time"));
                trip.setArrivalTime(resultSet.getDate("arrival_time"));
                trip.setTicketPrice(resultSet.getBigDecimal("ticket_price"));

                Route route = new Route();
                route.setId(resultSet.getInt("route_id"));


                Places arrivalPlace = new Places();
                arrivalPlace.setId(resultSet.getInt("arrival_place_id"));
                arrivalPlace.setPlaceName(resultSet.getString("arrival_name"));

                Places departurePlace = new Places();
                departurePlace.setId(resultSet.getInt("departure_place_id"));
                departurePlace.setPlaceName(resultSet.getString("departure_name"));
                route.setArrivalPlace(arrivalPlace);
                route.setDeparturePlace(departurePlace);

                Train train = new Train();
                train.setId(resultSet.getInt("train_id"));
                train.setTrainName(resultSet.getString("train_name"));
                train.setTrainNumber(resultSet.getString("train_number"));
                train.setMaxNumberOfCarriages(resultSet.getInt("max_number_of_carriages"));

                trip.setRoute(route);
                trip.setTrain(train);
                ticket.setTrip(trip);

                ticketList.add(ticket);
            }

            if (ticketList.isEmpty()) {
                throw new TicketException("Ticket table is empty");
            }

            return ticketList;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }

        return null;
    }


    @Override
    public Ticket getById(Integer id) {
        String GET_ALL = "SELECT ticket.id, ticket.trip_id, ticket.time_when_bought, trip.departure_time, " +
                "trip.arrival_time, trip.ticket_price, trip.train_id, trip.route_id, " +
                "route.departure_place_id, route.arrival_place_id, arrival_place.arrival_name, " +
                "departure_place.departure_name, train.train_name, train.train_number, " +
                "train.max_number_of_carriages FROM ticket " +
                "JOIN trip ON ticket.trip_id = trip.id " +
                "JOIN route ON trip.route_id = route.id " +
                "JOIN departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN arrival_place ON route.arrival_place_id = arrival_place.id " +
                "JOIN train ON trip.train_id = train.id WHERE ticket.id=?";


        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setTimeWhenTicketWasBought(resultSet.getDate("time_when_bought"));

                Trip trip = new Trip();
                trip.setId(resultSet.getInt("trip_id"));
                trip.setDepartureTime(resultSet.getDate("departure_time"));
                trip.setArrivalTime(resultSet.getDate("arrival_time"));
                trip.setTicketPrice(resultSet.getBigDecimal("ticket_price"));

                Route route = new Route();
                route.setId(resultSet.getInt("route_id"));


                Places arrivalPlace = new Places();
                arrivalPlace.setId(resultSet.getInt("arrival_place_id"));
                arrivalPlace.setPlaceName(resultSet.getString("arrival_name"));

                Places departurePlace = new Places();
                departurePlace.setId(resultSet.getInt("departure_place_id"));
                departurePlace.setPlaceName(resultSet.getString("departure_name"));
                route.setArrivalPlace(arrivalPlace);
                route.setDeparturePlace(departurePlace);

                Train train = new Train();
                train.setId(resultSet.getInt("train_id"));
                train.setTrainName(resultSet.getString("train_name"));
                train.setTrainNumber(resultSet.getString("train_number"));
                train.setMaxNumberOfCarriages(resultSet.getInt("max_number_of_carriages"));

                trip.setRoute(route);
                trip.setTrain(train);
                ticket.setTrip(trip);

                return ticket;
            } else {
                throw new TicketException("Ticket with id " + id + " doesn't exists");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return null;
    }

    @Override
    public boolean delete(Ticket ticket) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String DELETE_BY_ID = "DELETE FROM ticket WHERE ticket.id=?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Ticket with id " + id + " doesn't exists");
                throw new TicketException("Ticket with id " + id + " doesn't exists");
            }

            logger.info("data deleted");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean save(Ticket ticket) {
        String SAVE = "INSERT INTO ticket(trip_id, time_when_bought) VALUES(?,?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SAVE);

            preparedStatement.setInt(1, ticket.getTrip().getId());
            preparedStatement.setDate(2, ticket.getTimeWhenTicketWasBought());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while creating ticket");
                throw new TicketException("Error while creating ticket");
            }

            logger.info("Data successfully saved");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }
}
