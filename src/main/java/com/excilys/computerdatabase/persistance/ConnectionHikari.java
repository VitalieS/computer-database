package com.excilys.computerdatabase.persistance;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Vitalie SOVA
 *
 */
public enum ConnectionHikari {
    CONNECTION;

    private final String resourceName = "hikaridatabase.properties";
    HikariConfig config;
    private HikariDataSource hikariDataSource;

    private ThreadLocal<Connection> threadLocal;

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ConnectionHikari.class);

    /**
     * Default constructor.
     * @throws ClassNotFoundException
     */
    ConnectionHikari()  {
        if (threadLocal == null) {
            //final String resourceName = "hikaridatabase.properties";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                props.load(resourceStream);
                props.list(System.out);
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
            LOG.debug("Hmm" + e.toString());
            e.printStackTrace();
        }
        return threadLocal.get();
    }

    /**
     * Start transaction.
     */
    public void startTransaction() {
        try {
            threadLocal.get().setAutoCommit(false);
        } catch (SQLException e) {
            LOG.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Commit transaction.
     */
    public void commit() {
        try {
            threadLocal.get().commit();
        } catch (SQLException e) {
            LOG.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Rollback transaction.
     */
    public void roolback() {
        try {
            threadLocal.get().rollback();
        } catch (SQLException e) {
            LOG.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Open connection.
     */
    public void open() {
        try {
            Connection connection = threadLocal.get();
            if (connection == null) {
                connection = hikariDataSource.getConnection();
                threadLocal.set(connection);
            }
        } catch (SQLException e) {
            LOG.debug(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Close connexion.
     */
    public void close() {
        Connection connection = getConnection();
        threadLocal.remove();
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.debug(e.toString());
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
                LOG.debug(e.toString());
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.debug(e.toString());
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
                LOG.debug(e.toString());
                e.printStackTrace();
            }
        }
    }

}