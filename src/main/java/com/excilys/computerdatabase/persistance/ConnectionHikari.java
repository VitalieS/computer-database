package com.excilys.computerdatabase.persistance;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Vitalie SOVA
 *
 */
public enum ConnectionHikari {
    CONNECTION;

    private final String hikariProperties = "hikaridatabase.properties";
    private HikariConfig config;
    private HikariDataSource hikariDataSource;
    private ThreadLocal<Connection> threadLocal;

    /**
     * Default constructor.
     */
    private ConnectionHikari()  {
        if (threadLocal == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            try (InputStream resourceStream = loader.getResourceAsStream(hikariProperties)) {
                props.load(resourceStream);
                // props.list(System.out);
                config = new HikariConfig(props);
                hikariDataSource = new HikariDataSource(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
            threadLocal = new ThreadLocal<>();
        }
    }

    /**
     * Get HikariDataSource connection.
     *
     * @return HikariDataSource connection.
     */
    public Connection getConnection() {
        try {
            Connection connection = threadLocal.get();
            if (connection == null || connection.isClosed()) {
                threadLocal.set(connection = hikariDataSource.getConnection());
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return threadLocal.get();
    }

    /**
     * Close    .
     */
    public void close() {
        Connection connection = getConnection();
        threadLocal.remove();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close statement and resultSet.
     *
     * @param resultSet - The resultSet.
     * @param statement - The statement.
     */
    public void close(ResultSet resultSet, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close statement.
     *
     * @param statement - The statement.
     */
    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}