package com.excilys.computerdatabase.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.mappers.ResultSetMapper;

/**
 * @author Vitalie SOVA
 *
 */
@Repository
public class CompanyDAO {

	@Autowired
    private DataSource dataSource;
	
    private final static CompanyDAO COMPANY_DAO_INSTANCE;
    private final static Logger LOG;

    static {
        COMPANY_DAO_INSTANCE = new CompanyDAO();
        LOG = LoggerFactory.getLogger(CompanyDAO.class);
    }

    public static CompanyDAO getInstance() {
        return COMPANY_DAO_INSTANCE;
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }
    /**
     * @return companyList - The list of companies
     */
    public ArrayList<Company> getCompaniesList() {
        ArrayList<Company> companyList = new ArrayList<Company>();
        ResultSet resultSet=null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            resultSet = statement.executeQuery("SELECT * FROM company ORDER BY id ASC");
            while (resultSet.next()) {
                companyList.add(ResultSetMapper.mapperCompany(resultSet));
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    /**
     * @param choiceId - The id of the selected company
     * @return company - The selected company object
     */
    public Company getCompanyById(Long choiceId) {
        Company company = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company WHERE id = " + choiceId);
            while (resultSet.next()) {
                company = ResultSetMapper.mapperCompany(resultSet);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - The list of companies in the selected range
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        ArrayList<Company> listCompany = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company ORDER BY id ASC LIMIT ?,?");
            while (resultSet.next()) {
                listCompany.add(ResultSetMapper.mapperCompany(resultSet));
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCompany;
    }

    /**
     * @return count - The number of companies
     */
    public int getNumberOfCompanies() {
        int count = 0;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS nbCompanies FROM company");
            while (resultSet.next()) {
                count = resultSet.getInt("nbCompanies");
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    /**
     * delete - delete a Company
     * 
     * @param cn - Connection to use
     * @param id - id of the Company to delete
     * @throws SQLException 
     */
    public void delete(Connection cn, long id) {
        try (PreparedStatement stCompany = cn.prepareStatement("DELETE FROM company WHERE id=?")) {
            stCompany.setLong(1, id);
            stCompany.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Cannot delete company with ID ", id, e);	
            e.printStackTrace();
        }
    }

}