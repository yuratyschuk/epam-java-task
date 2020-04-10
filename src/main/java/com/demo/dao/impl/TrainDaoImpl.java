package com.demo.dao.impl;

import com.demo.dao.interfaces.TrainDao;
import com.demo.exceptions.TrainException;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDaoImpl implements TrainDao {

    private static Logger logger = LogManager.getLogger();


    @Override
    public boolean update(Train train) {
        String UPDATE = "UPDATE train SET train.train_name=?, train.train_number=?, train.max_number_of_carriages=?," +
                "train.type=? " +
                "WHERE train.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, train.getTrainName());
            preparedStatement.setString(2, train.getTrainNumber());
            preparedStatement.setInt(3, train.getMaxNumberOfCarriages());
            preparedStatement.setString(4, train.getTrainType().toString());
            preparedStatement.setInt(5, train.getMaxNumberOfCarriages());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                throw new TrainException("Error while updating train with id" + train.getId());
            }

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public List<Train> getAll() {
        String FIND_ALL = "SELECT train.id, train.train_name, train.train_number, train.max_number_of_carriages, train.type " +
                "FROM train";
        List<Train> trainList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Train train = new Train();
                train.setId(resultSet.getInt("id"));
                train.setTrainName(resultSet.getString("train_name"));
                train.setTrainNumber(resultSet.getString("train_number"));
                train.setMaxNumberOfCarriages(resultSet.getInt("max_number_of_carriages"));
                TrainType trainType = TrainType.valueOf(resultSet.getString("type"));
                train.setTrainType(trainType);
                trainList.add(train);
            }

            return trainList;

        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return null;
    }

    @Override
    public Train getByName(String trainName) {
        String FIND_BY_TRAIN_NAME = "SELECT train.id, train.train_name, train.train_number," +
                "train.max_number_of_carriages, train.type " +
                "FROM train WHERE train.train_name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TRAIN_NAME)) {

            preparedStatement.setString(1, trainName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Train train = new Train();
                train.setId(resultSet.getInt("id"));
                train.setTrainName(resultSet.getString("train_name"));
                train.setTrainNumber(resultSet.getString("train_number"));
                train.setMaxNumberOfCarriages(resultSet.getInt("max_number_of_carriages"));
                TrainType trainType = TrainType.valueOf("type");
                train.setTrainType(trainType);

                resultSet.close();
                return train;
            } else {
                logger.error("Train with name " + trainName + " not found");
                throw new TrainException("Train with name " + trainName + " not found");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return null;
    }

    @Override
    public Train getById(Integer id) {
        String FIND_BY_ID = "SELECT train.id, train.train_name, train.train_number, train.max_number_of_carriages," +
                "train.type " +
                "FROM train WHERE train.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Train train = new Train();
                train.setId(resultSet.getInt("id"));
                train.setTrainName(resultSet.getString("train_name"));
                train.setTrainNumber(resultSet.getString("train_number"));
                train.setMaxNumberOfCarriages(resultSet.getInt("max_number_of_carriages"));
                TrainType trainType = TrainType.valueOf(resultSet.getString("type"));
                train.setTrainType(trainType);

                resultSet.close();
                return train;
            } else {
                logger.error("Train with id " + id + " not found");
                throw new TrainException("Train with id " + id + " not found");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState()
            );
        }
        return null;
    }

    @Override
    public boolean delete(Train train) {
        String DELETE_BY_NAME = "DELETE FROM train WHERE train.train_name=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)) {

            preparedStatement.setString(1, train.getTrainName());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete because train with train name: " + train.getTrainName() + " doesn't exists");
                throw new TrainException("Can't delete because train with train name: " + train.getTrainName() + " doesn't exists");
            }

            logger.info("Successfully deleted");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String DELETE_BY_ID = "DELETE FROM train WHERE train.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Can't delete train data because it doesn't exists. Id " + id);
                throw new TrainException("Can't delete train data because id doesn't exists. Id" + id);
            }

            logger.info("Data successfully deleted");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public boolean save(Train train) {
        String SAVE = "INSERT INTO train(train_name, train_number, max_number_of_carriages, type) VALUES(?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {

            preparedStatement.setString(1, train.getTrainName());
            preparedStatement.setString(2, train.getTrainNumber());
            preparedStatement.setInt(3, train.getMaxNumberOfCarriages());
            preparedStatement.setString(4, train.getTrainType().toString());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Error while saving new train");
                throw new TrainException("Error while saving new train");
            }

            logger.info("Data saved successfully");
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }
}