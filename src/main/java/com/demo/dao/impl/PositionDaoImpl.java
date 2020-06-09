package com.demo.dao.impl;

import com.demo.dao.interfaces.PositionDao;
import com.demo.exceptions.PositionException;
import com.demo.exceptions.PositionException;
import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.utils.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PositionDaoImpl implements PositionDao {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Position position) {

        String updateSql = "UPDATE positions SET job_name=?, salary=?, active=? WHERE id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, position.getJobName().toLowerCase());
            preparedStatement.setBigDecimal(2, position.getSalary());
            preparedStatement.setBoolean(3, position.getActive());
            preparedStatement.setInt(4, position.getId());


            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position info can't be update because doesn't exists. Id: " + position.getId());
                throw new PositionException("Position with id " + position.getId() + " doesn't exists");
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
    public boolean delete(Position position) {
        String deleteByUserNameSql = "DELETE FROM positions WHERE id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByUserNameSql)) {

            preparedStatement.setInt(1, position.getId());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position can't be deleted because doesn't exist. Position: " + position.getJobName());
                throw new PositionException("Position with job name " + position.getJobName() + " doesn't exist");
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
        String deleteByIdSql = "DELETE FROM positions WHERE positions.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSql)) {

            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position can't be deleted because doesn't exist. Id: " + id);
                throw new PositionException("Position not found");
            }

            logger.info("Data successfully deleted");
            return true;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
            ;
        }
        return false;
    }

    @Override
    public List<Position> getAll() {
        List<Position> positionList = new ArrayList<>();
        String getAllSql = "SELECT positions.id, positions.job_name, positions.salary, positions.active " +
                "FROM positions";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                Position position = new Position();
                Integer id = resultSet.getInt("id");
                String jobName = resultSet.getString("job_name");
                BigDecimal salary = resultSet.getBigDecimal("salary");
                boolean active = resultSet.getBoolean("active");
                position.setId(id);
                position.setJobName(jobName);
                position.setSalary(salary);
                position.setActive(active);

                positionList.add(position);
            }

            return positionList;
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());


        }
        return Collections.emptyList();
    }

    @Override
    public Position getById(Integer id) {
        String findByIdSql = "SELECT worker.first_name, worker.last_name, worker.hire_date, worker.working_experience," +
                " positions.id, positions.job_name, positions.salary, positions.active" +
                " FROM positions LEFT JOIN worker ON  positions.id = worker.position_id " +
                "WHERE positions.id=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                Position position = new Position();
                while (resultSet.next()) {  // because cursor is before first row

                    position.setId(Integer.parseInt(resultSet.getString("id")));
                    position.setJobName(resultSet.getString("job_name"));
                    position.setSalary(BigDecimal.valueOf(Long.parseLong(resultSet.getString("salary"))));
                    position.setActive(resultSet.getBoolean("active"));
                    if (resultSet.getString("first_name") == null) {
                        break;
                    } else {
                        Worker worker = new Worker();
                        worker.setFirstName(resultSet.getString("first_name"));
                        worker.setLastName(resultSet.getString("last_name"));
                        worker.setHireDate(Date.valueOf(resultSet.getString("hire_date")));
                        worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));
                    }
                }

                return position;
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Position();
    }

    @Override
    public Position getByName(String jobName) {

        String getByNameSql = "SELECT worker.first_name, worker.last_name, worker.hire_date, worker.working_experience, " +
                " positions.id, positions.job_name, positions.salary, positions.active " +
                " FROM positions LEFT JOIN worker ON  positions.id = worker.position_id " +
                " WHERE positions.job_name=?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByNameSql)) {

            preparedStatement.setString(1, jobName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Position position = new Position();
                while (resultSet.next()) {  // because cursor is before first row

                    position.setId(Integer.parseInt(resultSet.getString("id")));
                    position.setJobName(resultSet.getString("job_name"));
                    position.setSalary(BigDecimal.valueOf(Long.parseLong(resultSet.getString("salary"))));
                    position.setActive(resultSet.getBoolean("active"));
                    if (resultSet.getString("first_name") == null) {
                        break;
                    } else {
                        Worker worker = new Worker();
                        worker.setFirstName(resultSet.getString("first_name"));
                        worker.setLastName(resultSet.getString("last_name"));
                        worker.setHireDate(Date.valueOf(resultSet.getString("hire_date")));
                        worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));
                    }

                }
                if (position.getId() == null) {
                    logger.error("Position with job name " + jobName + " doesn't exists");
                    throw new PositionException("Position with job name " + jobName + " doesn't exists");
                }

                return position;
            }
        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return new Position();

    }

    @Override
    public Position save(Position position) {

        String addSql = "INSERT INTO positions(job_name, salary, active) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, position.getJobName().toLowerCase());
            preparedStatement.setBigDecimal(2, position.getSalary());
            preparedStatement.setBoolean(3, position.getActive());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Position didn't create");
                throw new PositionException("Error while creating position!");
            }

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    position.setId(resultSet.getInt(1));
                    logger.info("Info added to data base");

                    return position;
                }
            }

        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }
        return null;
    }

    @Override
    public List<Position> getPositionListByActive(boolean active) {
        String getPositionListByActiveSql = "SELECT positions.id, positions.job_name, positions.salary " +
                "FROM positions " +
                "WHERE positions.active=?";

        List<Position> positionList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getPositionListByActiveSql)) {

            preparedStatement.setBoolean(1, active);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {  // because cursor is before first row
                    Position position = new Position();

                    position.setId(Integer.parseInt(resultSet.getString("id")));
                    position.setJobName(resultSet.getString("job_name"));
                    position.setSalary(resultSet.getBigDecimal("salary"));
                    position.setActive(active);
                    positionList.add(position);
                }

                if (positionList.isEmpty()) {
                    throw new PositionException("No active positions");
                }

                return positionList;
            }

        } catch (SQLException e) {

            logger.error("Message: {}", e.getMessage());
            logger.error("Error code: {}", e.getErrorCode());
            logger.error("Sql state: {}", e.getSQLState());
        }

        return Collections.emptyList();
    }
}
