package com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Vitalie SOVA
 *
 */
public enum ConnectionDB {
    CONNECTION;

    /**
     * @return The connection
     * @throws ConfigurationException
     */
    public Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PropertiesManager.load();
            String typeconn = PropertiesManager.config.getString("typeconn");
            String typedb = PropertiesManager.config.getString("typedb");
            String host = PropertiesManager.config.getString("host");
            String port = PropertiesManager.config.getString("port");
            String database = PropertiesManager.config.getString("database");
            String param = PropertiesManager.config.getString("param");

            String username = PropertiesManager.config.getString("username");
            String password = PropertiesManager.config.getString("password");
            System.out.println("Connecting to the database : " + database
                    + " with username : " + username);
            String url = new String(typeconn + ":" + typedb + "://" + host + ":"
                    + port + "/" + database + param);
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection() {
        return CONNECTION.createConnection();
    }

    /**
     * @throws SQLException
     *             - The SQL exception
     */
    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection to database closed");
    }

    public void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeResulSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}