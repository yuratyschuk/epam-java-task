package com.demo.service.impl;

import com.demo.exceptions.WorkerException;
import com.demo.model.Position;
import com.demo.model.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.demo.service.DAO;
import com.demo.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDaoImpl implements DAO<Worker> {

    private final String UPDATE = "UPDATE worker SET first_name='" + worker.getFirstName() + "', last_name='" + worker.getLastName() +
            "', position_id='" + worker.getPosition().getId() + "', working_experience='" + worker.getWorkingExperience() +
            "', hire_date='" + worker.getHireDate() + "' WHERE id=" + worker.getId();

    private final String SQL_DELETE_BY_USERNAME = "DELETE FROM worker WHERE worker.last_name=?";

    private final String SQL_DELETE_BY_ID = "DELETE FROM worker WHERE worker.id=?";

    private final String SQL_ALL = "SELECT worker.*, position.job_name, position.salary " +
            "FROM worker JOIN position ON position.id = worker.position_id";

    private final String SQL_FIND_BY_ID = "SELECT worker.*, position.job_name, position.salary FROM worker " +
            "JOIN position ON position.id = worker.position_id WHERE worker.id=" + id;

    private final String SQL_ADD = "INSERT INTO worker(first_name, last_name, working_experience, position_id, hire_date)" +
            " VALUES(?, ?, ?, ?, ?)";


    private Connection connection = null;

    private PreparedStatement preparedStatement = null;


    private static Logger logger = LogManager.getLogger();

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public boolean update(Worker worker) {
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker can't be update because doesn't exists. Id: " + worker.getId());
                throw new WorkerException("Worker with id " + worker.getId() + " doesn't exists");
            }

            logger.info("update successful");
            return true;
        } catch (SQLException e) {
            logger.error("Error in Worker in update method");
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    @Override
    public boolean delete(Worker worker) {
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(SQL_DELETE_BY_USERNAME);
            preparedStatement.setString(1, worker.getLastName());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker can't be deleted because doesn't exist. Last_name: " + worker.getLastName());
                throw new WorkerException("Worker with last_name " + worker.getLastName() + " doesn't exist");
            }

            logger.info("Data deleted");
            return true;
        } catch (SQLException e) {
            logger.error("Exception in worker in delete method");
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setInt(1, id);

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker can't be deleted because doesn't exist. Id: " + id);
                throw new WorkerException("Worker not found");
            }

            logger.info("Data successfully deleted");
            return true;
        } catch (SQLException e) {
            logger.error("Error in WorkerDao in deleteById method");
            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Worker> getAll() {
        List<Worker> workerList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Worker worker = new Worker();

                worker.setId(Integer.parseInt(resultSet.getString(
                        "id")));
                worker.setFirstName(resultSet.getString("first_name"));
                worker.setLastName(resultSet.getString("last_name"));
                worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));

                Position position = new Position();
                position.setId(Integer.parseInt(resultSet.getString("position_id")));
                position.setJobName(resultSet.getString("job_name"));
                position.setSalary(resultSet.getBigDecimal("salary"));

                worker.setPosition(position);
                workerList.add(worker);
            }

            if (workerList.isEmpty()) {
                logger.error("Worker data table is empty");
                throw new WorkerException("Worker data table is empty");
            }

            return workerList;
        } catch (SQLException e) {
            logger.warn("SQLException in WorkerDao in getAll method");
            logger.warn(e.getMessage() + e.getErrorCode() + e.getSQLState());

        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return workerList;
    }

    @Override
    public Worker getById(Integer id) {
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {  // because cursor is before first row
                Worker worker = new Worker();

                worker.setId(Integer.parseInt(resultSet.getString("id")));
                worker.setFirstName(resultSet.getString("first_name"));
                worker.setLastName(resultSet.getString("last_name"));
                worker.setHireDate(Date.valueOf(resultSet.getString("hire_date")));

                Position position = new Position();
                position.setJobName(resultSet.getString("job_name"));
                position.setJobName(resultSet.getString("salary"));
                worker.setWorkingExperience(Integer.parseInt(resultSet.getString("working_experience")));
                worker.setPosition(position);

                return worker;
            } else {
                logger.error("Worker with id " + id + " not found");
                throw new WorkerException("Worker with id " + id + " not found");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public boolean create(Worker worker) {
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD);

            preparedStatement.setString(1, worker.getFirstName());
            preparedStatement.setString(2, worker.getLastName());
            preparedStatement.setInt(3, worker.getWorkingExperience());
            preparedStatement.setInt(4, worker.getPosition().getId());
            preparedStatement.setDate(5, worker.getHireDate());

            int checkIfNotNull = preparedStatement.executeUpdate();
            if (checkIfNotNull == 0) {
                logger.error("Worker didn't create");
                throw new WorkerException("Error while creating worker!");
            }

            logger.info("Info added to data base");
            return true;
        } catch (SQLException e) {
            logger.error("Sql Exception in WorkerDao create method");
            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
