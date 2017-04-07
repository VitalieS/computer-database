package com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerdatabase.model.entities.Company;

/**
 * @author Vitalie SOVA
 *
 */
public enum CompanyDAO {
    CompanyDAO;

    /**
     * @return companyList - The list of companies
     */
    public ArrayList<Company> getCompaniesList() {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Company> companyList = new ArrayList<Company>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM company ORDER BY id ASC");
            while (resultSet.next()) {
                Company c = new Company(resultSet.getLong("id"), resultSet.getString("name"));
                companyList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet); */
        }
        return companyList;
    }

    /**
     * @param choiceId - The id of the selected company
     * @return company - The selected company object
     */
    public Company getCompanyById(Long choiceId) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Company company = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM company WHERE id = " + choiceId);
            while (resultSet.next()) {
                company = new Company(resultSet.getLong("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet); */
        }
        return company;
    }

    /**
     * @param idBegin
     *            - The id of the first company
     * @param idEnd
     *            - The id of the last company
     * @return listCompany - The list of companies in the selected range
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Company> listCompany = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM company ORDER BY id ASC LIMIT ?,?");
            while (resultSet.next()) {
                listCompany.add(new Company(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet); */
        }
        return listCompany;
    }

    /**
     * @return count - The number of companies
     */
    public int getNumberOfCompanies() {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) AS nbOfCompanies FROM company");
            while (resultSet.next()) {
                count = resultSet.getInt("nbOfCompanies");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet); */
        }
        return count;
    }

}