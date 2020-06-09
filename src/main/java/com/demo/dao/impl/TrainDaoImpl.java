package com.demo.dao.impl;

import com.demo.dao.interfaces.TrainDao;
import com.demo.exceptions.DataNotFoundException;
import com.demo.exceptions.TrainException;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrainDaoImpl implements TrainDao {

    private static final Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Train train) {
        String updateSql = "UPDATE train SET train.train_name=?, train.train_number=?, train.max_number_of_carriages=?, " +
                "train.type=? " +
                "WHERE train.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, train.getTrainName());
            preparedStatement.setString(2, train.getTrainNumber());
            preparedStatement.setInt(3, train.getMaxNumberOfCarriages());
            preparedStatement.setString(4, train.getTrainType().toString());
            preparedStatement.setInt(5, train.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new TrainException("Error while updating train with id " + train.getId());
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
    public List<Train> getAll() {
        String findAllSql = "SELECT train.id, train.train_name, train.train_number, train.max_number_of_carriages, train.type " +
                "FROM train";
        List<Train> trainList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Train train = getTrainFromDataBase(resultSet);
                trainList.add(train);
            }

            return trainList;

        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return Collections.emptyList();
    }

    @Override
    public Train getByName(String trainName) {
        String findByTrainNameSql = "SELECT train.id, train.train_name, train.train_number," +
                "train.max_number_of_carriages, train.type " +
                "FROM train WHERE train.train_name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByTrainNameSql)) {

            preparedStatement.setString(1, trainName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getTrainFromDataBase(resultSet);
                } else {
                    logger.error("Train with name " + trainName + " not found");
                    throw new DataNotFoundException("Train with name " + trainName + " not found");
                }
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Train();
    }

    @Override
    public Train getById(Integer id) {
        String findByIdSql = "SELECT train.id, train.train_name, train.train_number, train.max_number_of_carriages," +
                "train.type " +
                "FROM train WHERE train.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getTrainFromDataBase(resultSet);
                } else {
                    logger.error("Train with id " + id + " not found");
                    throw new DataNotFoundException("Train with id " + id + " not found");
                }
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Train();
    }

    @Override
    public boolean delete(Train train) {
        String deleteByNameSql = "DELETE FROM train WHERE train.train_name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByNameSql)) {

            preparedStatement.setString(1, train.getTrainName());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete because train with train name: " + train.getTrainName() + " doesn't exists");
                throw new TrainException("Can't delete because train with train name: " + train.getTrainName() + " doesn't exists");
            }

            logger.info("Successfully deleted");
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
        String deleteByIdSql = "DELETE FROM train WHERE train.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete train data because it doesn't exists. Id " + id);
                throw new TrainException("Can't delete train data because id doesn't exists. Id" + id);
            }

            logger.info("Data successfully deleted");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return false;
    }

    @Override
    public Train save(Train train) {
        String saveSql = "INSERT INTO train(train_name, train_number, max_number_of_carriages, type) VALUES(?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, train.getTrainName());
            preparedStatement.setString(2, train.getTrainNumber());
            preparedStatement.setInt(3, train.getMaxNumberOfCarriages());
            preparedStatement.setString(4, train.getTrainType().toString());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while saving new train");
                throw new TrainException("Error while saving new train");
            }

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    train.setId(resultSet.getInt(1));

                    return train;
                }
            }

            logger.info("Data saved successfully");

        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    @Override
    public List<Train> getTrainListByType(TrainType trainType) {
        String getTrainListByTypeSql = "SELECT train.id, train.train_name FROM train " +
                "WHERE train.type=?";
        List<Train> trainList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getTrainListByTypeSql)) {

            preparedStatement.setString(1, trainType.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Train train = new Train();
                    train.setId(resultSet.getInt("id"));
                    train.setTrainName("train_name");
                    train.setTrainType(trainType);
                    trainList.add(train);
                }

                if (trainList.isEmpty()) {
                    logger.error("Train with type:  " + trainType + " not found");
                    throw new DataNotFoundException("Train with type:  " + trainType + " not found");
                }

                return trainList;
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return Collections.emptyList();
    }

    private Train getTrainFromDataBase(ResultSet resultSet) throws SQLException {
        Train train = new Train();
        train.setId(resultSet.getInt("id"));
        train.setTrainName(resultSet.getString("train_name"));
        train.setTrainNumber(resultSet.getString("train_number"));
        train.setMaxNumberOfCarriages(resultSet.getInt("max_number_of_carriages"));
        TrainType trainType = TrainType.valueOf(resultSet.getString("type"));
        train.setTrainType(trainType);
        return train;
    }
}
