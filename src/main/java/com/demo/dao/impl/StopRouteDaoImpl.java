package com.demo.dao.impl;

import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StopRouteDaoImpl {

    private final static Logger logger = LogManager.getLogger();

    public void save(int tripId, int stopId) {
        String saveSql = "INSERT INTO stop_trip(stop_trip.trip_id, stop_trip.stop_id) VALUES(?, ?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql)) {

            preparedStatement.setInt(1, tripId);
            preparedStatement.setInt(2, stopId);

            preparedStatement.executeUpdate();

            logger.info("Data saved");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
