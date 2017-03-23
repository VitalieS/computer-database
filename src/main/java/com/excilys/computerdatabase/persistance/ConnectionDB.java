package main.java.com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.configuration.ConfigurationException;

/**
 * @author Vitalie SOVA
 *
 */
public enum ConnectionDB {
    CONNECTION;

    /*public Connection connection;
    public Statement statement;
    public ResultSet results;
     */
    /**
     * @return The instance
     *//*
     * protected ConnectionDB getInstance() { return CONNECTION; }
     */
    /*
     * ConnectionDB() { try { Class.forName("com.mysql.jdbc.Driver");
     * getConnection(); } catch (ClassNotFoundException e) {
     * e.printStackTrace(); }
     *
     * }
     */

    /**
     * @return The connection
     * @throws ConfigurationException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public Connection getConnection() throws ConfigurationException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //config = new PropertiesConfiguration("database.properties");
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

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ComputerDBException("ConnectionDB can not be instantiated", e);
        }
        return connection;
    }


    /**
     * @throws SQLException
     *             - The SQL exception
     */
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        System.out.println("Connection to database closed");
    }

    public void closeStatement(Statement st) throws SQLException {
        if (st != null) {
            st.close();
        }
    }

    public void closeResulSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }
}