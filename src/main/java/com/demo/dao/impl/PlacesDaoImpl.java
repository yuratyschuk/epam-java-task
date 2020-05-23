package com.demo.dao.impl;

import com.demo.dao.interfaces.PlacesDao;
import com.demo.exceptions.PlacesException;
import com.demo.model.Places;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlacesDaoImpl implements PlacesDao {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Places places) {
        return false;
    }

    @Override
    public boolean delete(Places places) {
        return false;
    }

    @Override
    public List<Places> getAll() {
        String getAllSql = "SELECT places.id, places.name FROM places";
        List<Places> placesList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Places places = new Places();
                places.setId(resultSet.getInt("id"));
                places.setPlaceName(resultSet.getString("name"));

                placesList.add(places);
            }

            if (placesList.isEmpty()) {
                throw new PlacesException("Places table is empty");
            }
            return placesList;
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return Collections.emptyList();
    }

    @Override
    public Places save(Places places) {
        return null;
    }

    @Override
    public Places getById(Integer id) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
