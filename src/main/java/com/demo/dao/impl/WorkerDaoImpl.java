package com.demo.dao.impl;

import com.demo.dao.interfaces.WorkerDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerDaoImpl implements WorkerDao {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Worker worker) {
        String updateSql = "UPDATE worker SET worker.first_name=?, worker.last_name=?," +
                "worker.position_id=?, worker.working_experience=?," +
                "worker.hire_date=? WHERE worker.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, worker.getFirstName());
            preparedStatement.setString(2, worker.getLastName());
            preparedStatement.setInt(3, worker.getPosition().getId());
            preparedStatement.setInt(4, worker.getWorkingExperience());
            preparedStatement.setObject(5, worker.getHireDate());
            preparedStatement.setInt(6, worker.getId());


            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker can't be update because doesn't exists. Id: " + worker.getId());
                throw new DataInsertException("Worker with id " + worker.getId() + " doesn't exists");
            }

            logger.info("update successful");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return false;
    }

    @Override
    public boolean delete(Worker worker) {
        String deleteByUsernameSql = "DELETE FROM worker WHERE worker.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByUsernameSql)) {

            preparedStatement.setInt(1, worker.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker can't be deleted because doesn't exist. Last_name: " + worker.getLastName());
                throw new DataNotFoundException("Worker with last_name " + worker.getLastName() + " doesn't exist");
            }

            logger.info("Data deleted");
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
        String deleteByIdSql = "DELETE FROM worker WHERE worker.id=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker can't be deleted because doesn't exist. Id: " + id);
                throw new DataNotFoundException("Worker not found");
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
    public List<Worker> getAll() {
        List<Worker> workerList = new ArrayList<>();
        String getAllSql = "SELECT worker.*, positions.id AS positionId, " +
                "positions.job_name, positions.salary, positions.active FROM worker " +
                "JOIN positions ON positions.id = worker.position_id";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Worker worker = getWorkerFromDataBase(resultSet);
                workerList.add(worker);
            }


            return workerList;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());


        }
        return Collections.emptyList();
    }

    @Override
    public Worker getById(Integer id) {
        String findByIdSql = "SELECT worker.*, positions.id AS positionId, " +
                "positions.job_name, positions.salary, positions.active FROM worker " +
                "JOIN positions ON positions.id = worker.position_id WHERE worker.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getWorkerFromDataBase(resultSet);
                } else {
                    logger.error("Worker with id " + id + " not found");
                    throw new DataNotFoundException("Worker with id " + id + " not found");
                }
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Worker();
    }



    @Override
    public Worker getByLastName(String lastName) {
        String getByNameSql = "SELECT worker.*, positions.id AS positionId, " +
                "positions.job_name, positions.salary, positions.active FROM worker " +
                "JOIN positions ON positions.id = worker.position_id WHERE worker.last_name=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByNameSql)) {

            preparedStatement.setString(1, lastName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getWorkerFromDataBase(resultSet);
                } else {
                    logger.error("Worker with last name: " + lastName + " not found");
                    throw new DataNotFoundException("Worker with last name: " + lastName + " not found");
                }
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Worker();

    }

    @Override
    public Worker save(Worker worker) {
        String saveSql = "INSERT INTO worker(first_name, last_name, working_experience, position_id, hire_date)" +
                " VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveSql,
                     Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, worker.getFirstName());
            preparedStatement.setString(2, worker.getLastName());
            preparedStatement.setInt(3, worker.getWorkingExperience());
            preparedStatement.setInt(4, worker.getPosition().getId());
            preparedStatement.setObject(5, worker.getHireDate());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker didn't create");
                throw new DataInsertException( "Error while creating worker!");
            }

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    worker.setId(resultSet.getInt(1));

                    return worker;
                }
            }

            logger.info("Info added to data base");
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    private Worker getWorkerFromDataBase(ResultSet resultSet) throws SQLException {
        Worker worker = new Worker();

        worker.setId(Integer.parseInt(resultSet.getString("id")));
        worker.setFirstName(resultSet.getString("first_name"));
        worker.setLastName(resultSet.getString("last_name"));
        worker.setHireDate(Date.valueOf(resultSet.getString("hire_date")));

        Position position = new Position();
        position.setId(resultSet.getInt("positionId"));
        position.setJobName(resultSet.getString("job_name"));
        position.setSalary(resultSet.getBigDecimal("salary"));
        position.setActive(resultSet.getBoolean("active"));
        worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));
        worker.setPosition(position);

        return worker;
    }

}