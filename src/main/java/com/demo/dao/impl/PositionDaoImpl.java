package com.demo.dao.impl;

import com.demo.dao.PositionDao;
import com.demo.exceptions.PositionException;
import com.demo.exceptions.WorkerException;
import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.dao.DAO;
import com.demo.utils.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PositionDaoImpl implements PositionDao {

    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Position position) {

        String UPDATE = "UPDATE positions SET job_name=?, salary=? WHERE id=?";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, position.getJobName().toLowerCase());
            preparedStatement.setBigDecimal(2, position.getSalary());
            preparedStatement.setInt(3, position.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position info can't be update because doesn't exists. Id: " + position.getId());
                throw new PositionException("Position with id " + position.getId() + " doesn't exists");
            }

            logger.info("update successful");
            return true;
        } catch (SQLException e) {
            logger.error("Error in Position in update method");
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Position position) {
        String SQL_DELETE_BY_USERNAME = "DELETE FROM positions WHERE job_name=?";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_USERNAME)) {

            preparedStatement.setString(1, position.getJobName().toLowerCase());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position can't be deleted because doesn't exist. Position: " + position.getJobName());
                throw new WorkerException("Position with job name " + position.getJobName() + " doesn't exist");
            }

            logger.info("Data deleted");
            return true;
        } catch (SQLException e) {
            logger.error("Exception in Position in delete method");
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String SQL_DELETE_BY_ID = "DELETE FROM positions WHERE positions.id=?";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position can't be deleted because doesn't exist. Id: " + id);
                throw new WorkerException("Position not found");
            }

            logger.info("Data successfully deleted");
            return true;
        } catch (SQLException e) {
            logger.error("Error in PositionDAO in deleteById method");
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        }
        return false;
    }

    @Override
    public List<Position> getAll() {
        List<Position> positionList = new ArrayList<>();
        String SQL_ALL = "SELECT positions.*, worker.last_name, worker.first_name, worker.hire_date, worker.working_experience" +
                " FROM positions JOIN worker ON  worker.position_id = positions.id";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ALL)) {


            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, Position> positionById = new HashMap<>();
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id");
                String jobName = resultSet.getString("job_name");
                BigDecimal salary = resultSet.getBigDecimal("salary");

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int workingExperience = Integer.parseInt(resultSet.getString("working_experience"));
                Date hireDate = Date.valueOf(resultSet.getString("hire_date"));

                Position position = positionById.get(id);
                if (position == null) {
                    position = new Position(id, jobName, salary);
                    positionById.put(position.getId(), position);
                }

                Worker worker = new Worker(firstName, lastName, workingExperience, hireDate);
                position.setWorkerList(Collections.singletonList(worker));

            }
            positionList.addAll(positionById.values());

            return positionList;
        } catch (SQLException e) {
            logger.warn("SQLException in PositionDao in getAll method");
            logger.warn(e.getMessage() + e.getErrorCode() + e.getSQLState());

        }
        return null;
    }

    @Override
    public Position getById(Integer id) {
        String SQL_FIND_BY_ID = "SELECT worker.first_name, worker.last_name, worker.hire_date, worker.working_experience," +
                " positions.id, positions.job_name, positions.salary" +
                " FROM positions LEFT JOIN worker ON  positions.id = worker.position_id " +
                "WHERE positions.id=?";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Position position = new Position();
            while (resultSet.next()) {  // because cursor is before first row

                position.setId(Integer.parseInt(resultSet.getString("id")));
                position.setJobName(resultSet.getString("job_name"));
                position.setSalary(BigDecimal.valueOf(Long.parseLong(resultSet.getString("salary"))));

                if (resultSet.getString("first_name") == null) {
                    break;
                } else {
                    Worker worker = new Worker();
                    worker.setFirstName(resultSet.getString("first_name"));
                    worker.setLastName(resultSet.getString("last_name"));
                    worker.setHireDate(Date.valueOf(resultSet.getString("hire_date")));
                    worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));
                    position.setWorkerList(Collections.singletonList(worker));
                }
            }


            return position;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Position getByName(String jobName) {

        String SQL_GET_BY_NAME = "SELECT worker.first_name, worker.last_name, worker.hire_date, worker.working_experience, " +
                " positions.id, positions.job_name, positions.salary " +
                " FROM positions LEFT JOIN worker ON  positions.id = worker.position_id " +
                " WHERE positions.job_name=?";
        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_NAME)) {

            preparedStatement.setString(1, jobName);

            ResultSet resultSet = preparedStatement.executeQuery();
            Position position = new Position();
            while (resultSet.next()) {  // because cursor is before first row

                position.setId(Integer.parseInt(resultSet.getString("id")));
                position.setJobName(resultSet.getString("job_name"));
                position.setSalary(BigDecimal.valueOf(Long.parseLong(resultSet.getString("salary"))));
                if (resultSet.getString("first_name") == null) {
                    break;
                } else {
                    Worker worker = new Worker();
                    worker.setFirstName(resultSet.getString("first_name"));
                    worker.setLastName(resultSet.getString("last_name"));
                    worker.setHireDate(Date.valueOf(resultSet.getString("hire_date")));
                    worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));
                    position.setWorkerList(Collections.singletonList(worker));
                }
            }
            if (position.getId() == null) {
                logger.error("Position with job name " + jobName + " doesn't exists");
                throw new PositionException("Position with job name " + jobName + " doesn't exists");
            }

            return position;
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return null;

    }

    @Override
    public boolean save(Position position) {

        String SQL_ADD = "INSERT INTO positions(job_name, salary) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD)) {

            preparedStatement.setString(1, position.getJobName().toLowerCase());
            preparedStatement.setBigDecimal(2, position.getSalary());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position didn't create");
                throw new WorkerException("Error while creating position!");
            }

            logger.info("Info added to data base");
            return true;
        } catch (SQLException e) {
            logger.error("Sql Exception in PositionDao create method");
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        }
        return false;
    }
}
