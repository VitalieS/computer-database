package com.excilys.computerdatabase.persistance;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Vitalie SOVA
 *
 */
public enum ConnectionHikari {
    CONNECTION;

    private final String hiraki_properties = "/hikaridatabase.properties";
    private HikariDataSource hikariDataSource;
    private ThreadLocal<Connection> threadLocal;

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ConnectionHikari.class);

    /**
     * Default constructor.
     */
    ConnectionHikari() {
        if (threadLocal == null) {
            File f = new File(hiraki_properties);
            if(f.exists() && !f.isDirectory()) {
                LOG.error("Hikari properties file " + f.getAbsolutePath() + " was found");
            } else {
                LOG.error("Hikari properties file " + f.getAbsolutePath() + " was not found");
            }

            HikariConfig config = new HikariConfig(hiraki_properties);
            hikariDataSource = new HikariDataSource(config);
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
            LOG.debug(e.toString());
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

    /**
     * Get autocommit value.
     *
     * @return autocommit.
     */
    public boolean isAutoCommit() {
        boolean autoCommit = false;
        try {
            autoCommit = threadLocal.get().getAutoCommit();
        } catch (SQLException e) {
            LOG.debug(e.toString());
            e.printStackTrace();
        }
        return autoCommit;
    }
}