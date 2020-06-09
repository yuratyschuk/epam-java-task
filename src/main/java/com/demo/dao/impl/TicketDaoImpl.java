package com.demo.dao.impl;

import com.demo.dao.interfaces.TicketDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.*;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketDaoImpl implements TicketDao {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Ticket ticket) {
        String updateSql = "UPDATE ticket SET ticket.trip_id=?, ticket.time_when_bought=? WHERE ticket.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setInt(1, ticket.getTrip().getId());
            preparedStatement.setObject(2, ticket.getTimeWhenTicketWasBought());
            preparedStatement.setInt(3, ticket.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new DataInsertException("Exception while updating ticket");
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
    public List<Ticket> getAll() {
        String getAllSql = "SELECT ticket.id, ticket.trip_id, ticket.time_when_bought, trip.departure_time, " +
                "trip.arrival_time, trip.ticket_price, trip.train_id, trip.route_id, " +
                "route.departure_place_id, route.arrival_place_id, arrival_place.name AS arrival_name, " +
                "departure_place.name AS departure_name, train.train_name, train.train_number, " +
                "train.max_number_of_carriages FROM ticket " +
                "JOIN trip ON ticket.trip_id = trip.id " +
                "JOIN route ON trip.route_id = route.id " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN places arrival_place ON route.arrival_place_id = arrival_place.id " +
                "JOIN train ON trip.train_id = train.id";

        List<Ticket> ticketList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Ticket ticket = getTicketFromDataBase(resultSet);
                ticketList.add(ticket);
            }


            return ticketList;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return Collections.emptyList();
    }


    @Override
    public Ticket getById(Integer id) {
        String getByIdSql = "SELECT ticket.id, ticket.trip_id, ticket.time_when_bought, trip.departure_time, " +
                "trip.arrival_time, trip.ticket_price, trip.train_id, trip.route_id, " +
                "route.departure_place_id, route.arrival_place_id, arrival_place.name AS arrival_name, " +
                "departure_place.name AS departure_name, train.train_name, train.train_number, " +
                "train.max_number_of_carriages FROM ticket " +
                "JOIN trip ON ticket.trip_id = trip.id " +
                "JOIN route ON trip.route_id = route.id " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN places arrival_place ON route.arrival_place_id = arrival_place.id " +
                "JOIN train ON trip.train_id = train.id WHERE ticket.id=?";


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getTicketFromDataBase(resultSet);
                } else {
                    logger.error("Ticket with id: " + id + " doesn't exists");
                    throw new DataNotFoundException("Ticket with id " + id + " doesn't exists");
                }


            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Ticket();
    }

    @Override
    public boolean delete(Ticket ticket) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String deleteByIdSql = "DELETE FROM ticket WHERE ticket.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Ticket with id " + id + " doesn't exists");
                throw new DataNotFoundException("Ticket with id " + id + " doesn't exists");
            }

            logger.info("data deleted");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;
    }

    @Override
    public Ticket save(Ticket ticket) {
        String saveSql = "INSERT INTO ticket(trip_id, time_when_bought, user_id) VALUES(?,?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, ticket.getTrip().getId());
            preparedStatement.setObject(2, ticket.getTimeWhenTicketWasBought());
            preparedStatement.setInt(3, ticket.getUser().getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while creating ticket");
                throw new DataInsertException("Error while creating ticket");
            }

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    ticket.setId(resultSet.getInt(1));

                    return ticket;
                }
            }

            logger.info("Data successfully saved");

        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    @Override
    public List<Ticket> getTicketListByUserId(Integer userId) {
        String getTicketListByUserIdSql = "SELECT ticket.id, ticket.trip_id, ticket.time_when_bought, trip.departure_time, " +
                "trip.arrival_time, trip.ticket_price, trip.train_id, trip.route_id, " +
                "route.departure_place_id, route.arrival_place_id, arrival_place.name AS arrival_name, " +
                "departure_place.name AS departure_name, train.train_name, train.train_number, " +
                "train.max_number_of_carriages FROM ticket " +
                "JOIN trip ON ticket.trip_id = trip.id " +
                "JOIN route ON trip.route_id = route.id " +
                "JOIN places departure_place ON route.departure_place_id = departure_place.id " +
                "JOIN places arrival_place ON route.arrival_place_id = arrival_place.id " +
                "JOIN train ON trip.train_id = train.id WHERE ticket.user_id    =?";
        List<Ticket> ticketList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getTicketListByUserIdSql)) {
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Ticket ticket = getTicketFromDataBase(resultSet);
                    ticketList.add(ticket);
                }

                if(ticketList.isEmpty()) {
                    logger.error("Tickets for user not found");
                    throw new DataNotFoundException("Tickets for user not found");
                }

                return ticketList;
            }
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return Collections.emptyList();
    }

    private Ticket getTicketFromDataBase(ResultSet resultSet) throws SQLException {
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
    }
}
