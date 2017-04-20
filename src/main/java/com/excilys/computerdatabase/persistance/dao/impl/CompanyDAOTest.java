package com.excilys.computerdatabase.persistance.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistance.ConnectionHikari;
import com.excilys.computerdatabase.persistance.mappers.ResultSetMapper;

/**
 * @author Vitalie SOVA
 *
 */
public enum CompanyDAOTest {
    CompanyDAO;

    /**
     * @return companyList - The list of companies
     */
    public ArrayList<Company> getCompaniesList() {
        ArrayList<Company> companyList = new ArrayList<Company>();
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM company ORDER BY id ASC");
            while (resultSet.next()) {
                companyList.add(ResultSetMapper.INSTANCE.mapperCompany(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return companyList;
    }

    /**
     * @param choiceId - The id of the selected company
     * @return company - The selected company object
     */
    public Company getCompanyById(Long choiceId) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Company company = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM company WHERE id = " + choiceId);
            while (resultSet.next()) {
                company = ResultSetMapper.INSTANCE.mapperCompany(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return company;
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - The list of companies in the selected range
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Company> listCompany = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM company ORDER BY id ASC LIMIT ?,?");
            while (resultSet.next()) {
                listCompany.add(ResultSetMapper.INSTANCE.mapperCompany(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return listCompany;
    }

    /**
     * @return count - The number of companies
     */
    public int getNumberOfCompanies() {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) AS nbCompanies FROM company");
            while (resultSet.next()) {
                count = resultSet.getInt("nbCompanies");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return count;
    }

}