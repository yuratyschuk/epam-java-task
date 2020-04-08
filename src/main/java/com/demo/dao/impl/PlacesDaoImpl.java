//package com.demo.dao.impl;
//
//import com.demo.dao.DAO;
//import com.demo.exceptions.PlacesException;
//import com.demo.model.Places;
//import com.demo.utils.ConnectionFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PlacesDaoImpl implements DAO<Places> {
//
//    private Connection connection = null;
//
//    private PreparedStatement preparedStatement = null;
//
//
//    private static Logger logger = LogManager.getLogger();
//
//    private Connection getConnection() throws SQLException {
//        return ConnectionFactory.getInstance().getConnection();
//    }
//
//
//    @Override
//    public boolean update(Places places) {
//        String UPDATE = "UPDATE places SET places.name='"+ places.getPlaceName() + "' WHERE id=" + places.getId();
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(UPDATE);
//
//            int checkIfNotNull = preparedStatement.executeUpdate();
//            if(checkIfNotNull == 0) {
//                logger.error("Place can't be updated");
//                throw  new PlacesException("Place can't be updated");
//            }
//
//            logger.info("Place updated successfully");
//            return true;
//        }catch (SQLException e) {
//            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
//        }
//        return false;
//    }
//
//    @Override
//    public List<Places> getAll() {
//        String GET_ALL = "SELECT places.id, places.name FROM places";
//
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(GET_ALL);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            List<Places> placesList = new ArrayList<>();
//            while (resultSet.next()) {
//                Places places = new Places();
//                places.setId(Integer.parseInt(resultSet.getString("id")));
//                places.setPlaceName(resultSet.getString("name"));
//                placesList.add(places);
//            }
//
//            if (placesList.isEmpty()) {
//                logger.error("Places table is empty");
//                throw new PlacesException("Places table is empty");
//            }
//
//            return placesList;
//        } catch (SQLException e) {
//
//            logger.error("Sql exception in PlacesDAO in getAll method");
//            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
//        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Places findByName(String name) {
//        String FIND_BY_NAME = "SELECT places.id, places.name FROM places WHERE places.name='" + name + "'";
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(FIND_BY_NAME);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                Places places = new Places();
//                places.setId(Integer.parseInt(resultSet.getString("id")));
//                places.setPlaceName(resultSet.getString("name"));
//
//                return places;
//            } else {
//                logger.error("Place with name " + name + " doesn't exists");
//                throw new PlacesException("Place with name " + name + " doesn't exists");
//            }
//
//        } catch (SQLException e) {
//            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
//        }
//        return null;
//    }
//
//    @Override
//    public Places findById(Integer id) {
//        String FIND_BY_ID = "SELECT places.id, places.name FROM places WHERE places.id=" + id;
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(FIND_BY_ID);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                Places places = new Places();
//                places.setId(Integer.parseInt(resultSet.getString("id")));
//                places.setPlaceName(resultSet.getString("name"));
//
//                return places;
//            } else {
//                logger.error("Place with id " + id + " doesn't exists");
//                throw new PlacesException("Place with id " + id + " doesn't exists");
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage() + e.getSQLState() + e.getErrorCode());
//        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean deleteByName(String name) {
//        String DELETE_BY_NAME = "DELETE FROM places WHERE places.id='" + name + "'";
//
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(DELETE_BY_NAME);
//
//            int checkIfNotNull = preparedStatement.executeUpdate();
//            if (checkIfNotNull == 0) {
//                logger.error("Place can't be deleted because doesn't exists. Name " + name);
//                throw new PlacesException("Place can't be deleted because doesn't exists. Name " + name);
//            }
//
//            logger.info("Data successfully deleted");
//            return true;
//        } catch (SQLException e) {
//            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
//        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteById(Integer id) {
//        String DELETE_BY_ID = "DELETE FROM places WHERE places.id=" + id;
//
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(DELETE_BY_ID);
//
//            int checkIfNotNull = preparedStatement.executeUpdate();
//            if (checkIfNotNull == 0) {
//                logger.error("Place can't be deleted because doesn't exists. Id " + id);
//                throw new PlacesException("Place can't be deleted because doesn't exists. Id " + id);
//            }
//
//            logger.info("Data successfully deleted");
//            return true;
//        } catch (SQLException e) {
//            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
//        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean save(Places places) {
//        String SQL_SAVE = "INSERT INTO places(name) VALUES(?)";
//        try {
//            connection = getConnection();
//            preparedStatement = connection.prepareStatement(SQL_SAVE);
//
//            preparedStatement.setString(1, places.getPlaceName());
//
//            int checkIfNotNull = preparedStatement.executeUpdate();
//            if (checkIfNotNull == 0) {
//                logger.error("Error while saving place");
//                throw new PlacesException("Error while saving place");
//            }
//
//            logger.info("Place added to database");
//            return true;
//        } catch (SQLException e) {
//            logger.error(e.getMessage() + e.getErrorCode() + e.getSQLState());
//        } finally {
//            try {
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//}
//
