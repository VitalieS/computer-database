package com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.PreparedStatement;

public class ConnectionDAO {

	private static ResultSet results;
	private static ConnectionDAO connectionDAO;
	private static Connection connection;
	private static Statement statement;

	protected ConnectionDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
			Properties props = new Properties();
			props.setProperty("user","admincdb");
			props.setProperty("password","qwerty1234");
			connection = DriverManager.getConnection(url, props);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionDAO getInstance() {
		if (connectionDAO == null) {
			connectionDAO = new ConnectionDAO();
		}
		return connectionDAO;
	}

	public void executeQuerySelect(String query) {
		try {
			statement = connection.createStatement();
			results = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeQueryDataManipulation(String query) {
		try {
			statement = connection.createStatement();
			int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			if (results == 1) {
				System.out.println("Succes");
			} else {
				System.out.println("Fail");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public ResultSet getResults() {
		return results;
	}
	
	public Statement getStatement() {
		return statement;
	}

	public void closeConnexion() throws SQLException {
		if (connectionDAO != null) {
			statement.close();
			connection.close();
		}
		/*if (connection != null)
			connection.close();
		if (statement != null)
			statement.close();
		if (results != null)*/
			// In case the statement or the connection object
			// has been closed, the ResultSet will be closed
			// automatically:
			//results.close();

		if (connection.isClosed() && results.isClosed() &&  statement.isClosed()) {
			System.out.println("Not connected");
		}
	}

}