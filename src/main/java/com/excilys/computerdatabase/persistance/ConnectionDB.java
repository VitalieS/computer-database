package main.java.com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @author Vitalie SOVA
 *
 */
public enum ConnectionDB {
    CONNECTION;

    // TODO Delete this and implement without
    public ConnectionDB connectionDAO;
    public Connection connection;
    public Statement statement;
    public ResultSet results;

    /**
     * @return The instance
     */
    protected ConnectionDB getInstance() {
        connectionDAO = CONNECTION;
        return connectionDAO = CONNECTION;
        // return CONNECTION;
    }

    /**
     *
     */
    ConnectionDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return The connection
     */
    protected Connection getConnection() {
        Configuration config;
        try {
            config = new PropertiesConfiguration("src/database.properties");
            String typeconn = config.getString("typeconn");
            String typedb = config.getString("typedb");
            String host = config.getString("host");
            String port = config.getString("port");
            String database = config.getString("database");
            String param = config.getString("param");

            String username = config.getString("username");
            String password = config.getString("password");

            System.out.println("Test to connect to the database : " + database
                    + " with username : " + username);
            String url = new String(typeconn + ":" + typedb + "://" + host + ":"
                    + port + "/" + database + param);
            connection = DriverManager.getConnection(url, username, password);

        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * @param query - The SQL query
     */
    public void executeQuerySelect(String query) {
        try {
            statement = connection.createStatement();
            results = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param query - The SQL query
     */
    public void executeQueryDataManipulation(String query) {
        try {
            statement = connection.createStatement();
            int results = statement.executeUpdate(query,
                    Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                System.out.println("Succes");
            } else {
                System.out.println("Fail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The results
     */
    public ResultSet getResults() {
        return results;
    }

    /**
     * @return The statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @throws SQLException - The SQL exception
     */
    public void closeConnexion() throws SQLException {
        /*
         * if (connection != null) { //statement.close(); connection.close(); //
         * results.close(); }
         */

        if (connectionDAO != null) {
            statement.close();
            connection.close();
            // results.close();
            // CONNECTION = null;
        }
        System.out.println("Connection to database closed");
        if (connection.isClosed() && results.isClosed()
                && statement.isClosed()) {
            System.out.println("Not connected");
        }
    }
}