package com.demo.dao.impl;

import com.demo.dao.interfaces.PlacesDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Places;
import com.demo.utils.ConnectionPool;
import com.sun.media.jfxmedia.events.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlacesDaoImpl implements PlacesDao {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Places places) {
        String updateSql = "UPDATE places SET places.name=? WHERE places.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, places.getPlaceName());
            preparedStatement.setInt(2, places.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();

            if(checkIfNotNull == 0) {
                logger.error("Place info can't be update because doesn't exists. Id: " + places.getId());
                throw new DataInsertException("Place with id " + places.getId() + " doesn't exists");
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
    public boolean delete(Places places) {
        String sqlDelete = "DELETE FROM places WHERE places.id=?";

        try(Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {

            preparedStatement.setInt(1, places.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();

            if(checkIfNotNull == 0) {
                logger.error("Position can't be deleted because doesn't exist. Id: " + places.getId());
                throw new DataNotFoundException("Position not found");
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
        String saveSql = "INSERT INTO places(name) VALUES(?)";

        try(Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, places.getPlaceName());

            int checkIfNotNull = preparedStatement.executeUpdate();

            if(checkIfNotNull == 0) {
                logger.error("Place didn't create");
                throw new DataInsertException("Error while creating place!");
            }

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    places.setId(resultSet.getInt(1));

                    return places;
                }
            }

        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Places();
    }

    @Override
    public Places getById(Integer id) {
        String getByIdSql = "SELECT places.name FROM places WHERE places.id=?";

        try(Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(getByIdSql)) {

            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Places places = new Places();
                    places.setId(id);
                    places.setPlaceName(resultSet.getString("name"));

                    return places;
                } else {
                    logger.error("Place with id: " + id + " not found");
                    throw new DataNotFoundException("Place with id: " + id + " not found");
                }
            }

        }  catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return new Places();
    }

    @Override
    public boolean deleteById(Integer id) {
        String deleteByIdSql = "DELETE FROM places WHERE places.id=?";

        try(Connection connection = ConnectionPool.getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();

            if(checkIfNotNull == 0) {
                logger.error("Place can't be deleted because doesn't exist. Id: " + id);
                throw new DataNotFoundException("Place not found");
            }

            return true;
        } catch (SQLException e) {
            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }


        return false;
    }
}
