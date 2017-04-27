package com.excilys.computerdatabase.persistence.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;

    private final static CompanyDAO COMPANY_DAO_INSTANCE;
    private final static Logger LOG;

    private final static String SQL_GET_COMPANIES_LIST = "SELECT * FROM company ORDER BY id ASC";
    private final static String SQL_GET_COMPANY_BY_ID = "SELECT * FROM company WHERE id = ?";
    // TODO Implement company creation
    // private final static String SQL_CREATE = "INSERT INTO company (name) VALUES ('?')";
    private final static String SQL_GET_NUMBER_COMPANIES = "SELECT COUNT(*) FROM company";
    private final static String SQL_GET_COMPANY_IN_RANGE = "SELECT * FROM company ORDER BY id ASC LIMIT ?,?";
    private final static String SQL_DELETE = "DELETE FROM company WHERE id = ?";

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
    public List<Company> getCompaniesList() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        return this.jdbcTemplate.query(SQL_GET_COMPANIES_LIST, new CompanyMapperJDBC());
    }

    /**
     * @param choiceId - The id of the selected company
     * @return company - The selected company object
     */
    public Company getCompanyById(Long id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        Company company;
        try {
            company = this.jdbcTemplate.queryForObject(SQL_GET_COMPANY_BY_ID, new Object[] { id }, new CompanyMapperJDBC());
        } catch (EmptyResultDataAccessException e) {
            company = null;
        }
        return company;
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - The list of companies in the selected range
     */
    public List<Company> getCompanyInRange(long idBegin, long idEnd) {
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
        jdbcTemplate = new JdbcTemplate(dataSource);
        int count = this.jdbcTemplate.queryForObject(SQL_GET_NUMBER_COMPANIES, new Object[] { }, Integer.class);
        if (count > 0) {
            return count;
        }
        return count;
    }

    /**
     * delete - Deletes a Company
     * @param id - The id of the Company to delete
     */
    public void delete(long id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (DataAccessException e) {
            LOG.error("Couldn't delete the comptuter");
        }
    }
}