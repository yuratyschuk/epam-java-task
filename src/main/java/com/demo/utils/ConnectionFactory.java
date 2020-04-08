package com.demo.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

  private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
  private String CONNECTION_URL = "jdbc:mysql://localhost:3306/epam_task_database";
  private Properties properties;
  private File file;

  private static ConnectionFactory connectionFactory = null;

  private ConnectionFactory() {
    try {
      file = new File("src/main/resources/application.properties");
      properties = new Properties();
      properties.load(new FileReader(file));
      Class.forName(DRIVER_CLASS_NAME);
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() throws SQLException {
    Connection conn;
    conn = DriverManager.getConnection(CONNECTION_URL, properties);
    return conn;
  }

  public static ConnectionFactory getInstance() {
    if (connectionFactory == null) {
      connectionFactory = new ConnectionFactory();
    }
    return connectionFactory;
  }

  public ComboPooledDataSource getDataSource() throws PropertyVetoException
  {
    ComboPooledDataSource cpds = new ComboPooledDataSource();
    cpds.setJdbcUrl(CONNECTION_URL + "?serverTimezone=UTC");
    cpds.setUser("root");
    cpds.setPassword("ryjgrfryjgrf");


    // Optional Settings
    cpds.setInitialPoolSize(5);
    cpds.setMinPoolSize(5);
    cpds.setAcquireIncrement(5);
    cpds.setMaxPoolSize(20);
    cpds.setMaxStatements(100);

    return cpds;
  }


}
