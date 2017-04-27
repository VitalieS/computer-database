package com.excilys.computerdatabase.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ResultSetMapper;

/**
 * @author Vitalie SOVA
 *
 */
@Repository
public class ComputerDAO {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private final static CompanyDAO COMPUTER_DAO_INSTANCE;
    private final static Logger LOG;

    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String INTRODUCED = "introduced";
    private final static String DISCONTINUED = "discontinued";
    private final static String COMPANY_NAME = "company_name";
    private final static String COMPANY_ID = "company_id";

    private final static String SQL_GET_COMPUTERS_LIST = "SELECT * FROM computer ORDER BY id ASC";
    private final static String SQL_GET_COMPUTER_BY_ID = "SELECT c.id as id, c.name as name, c.introduced as introduced, c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?";
    private final static String SQL_CREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private final static String SQL_DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id=?";
    private final static String SQL_SEARCH = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? ORDER BY %s ASC LIMIT ?,?";
    private final static String SQL_SEARCH_WITHOUT = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name LIKE ? LIMIT ?,?";

    static {
        COMPUTER_DAO_INSTANCE = new CompanyDAO();
        LOG = LoggerFactory.getLogger(CompanyDAO.class);
    }

    public static CompanyDAO getInstance() {
        return COMPUTER_DAO_INSTANCE;
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * @return computerList - The list of computers
     */
    public List<Computer> getComputerList() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        return this.jdbcTemplate.query(SQL_GET_COMPUTERS_LIST, new ComputerMapperJDBC());
    }

    /**
     * @param id - The id of the selected computer
     * @return computer - The selected computer object
     */
    public Computer getComputerById(Long id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        Computer computer;
        try {
            computer = this.jdbcTemplate.queryForObject(SQL_GET_COMPUTER_BY_ID, new Object[] { id }, new ComputerMapperJDBC());
        } catch (EmptyResultDataAccessException e) {
            computer = null;
        }
        return computer;

    }

    /**
     * @param c - The computer object to create
     * @return generatedKey - The generated Key
     */
    public Long createComputer(ComputerDTO computer) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        KeyHolder generatedKey = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);

                pStatement.setString(1, computer.getComputerName());
                if (computer.getIntroducedDate() != null) {
                    pStatement.setString(2, computer.getIntroducedDate().toString());
                } else {
                    pStatement.setString(2, null);
                }
                if (computer.getDiscontinuedDate() != null) {
                    pStatement.setString(3, computer.getDiscontinuedDate().toString());
                } else {
                    pStatement.setString(3, null);
                }
                if (computer.getCompanyName() != null && computer.getCompanyId() != 0) {
                    pStatement.setLong(4, computer.getCompanyId());
                } else {
                    pStatement.setString(4, null);
                }
                return pStatement;
            }
        }, generatedKey);

        return generatedKey.getKey().longValue();
    }

    /**
     * @param id - The id of the computer to update
     * @param c - The computer object to update with
     */
    public void updateComputer(Long id, ComputerDTO computer) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pStatement = connection.prepareStatement(SQL_UPDATE);
                pStatement.setString(1, computer.getComputerName());
                if (computer.getIntroducedDate() != null) {
                    pStatement.setString(2, computer.getIntroducedDate().toString());
                } else {
                    pStatement.setString(2, null);
                }
                if (computer.getDiscontinuedDate() != null) {
                    pStatement.setString(3, computer.getDiscontinuedDate().toString());
                } else {
                    pStatement.setString(3, null);
                }
                if (computer.getCompanyName() != null && computer.getCompanyId() != 0) {
                    pStatement.setLong(4, computer.getCompanyId());
                    pStatement.setString(5, computer.getCompanyName());
                } else {
                    pStatement.setString(4, null);
                    pStatement.setString(5, null);
                }
                return pStatement;
            }
        });

        /*jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(SQL_UPDATE,
                new Object[] { computer.getComputerName(),
                        (computer.getIntroducedDate() != null ? (computer.getIntroducedDate().toString()) : (null)),
                        (computer.getDiscontinuedDate() != null ? (computer.getDiscontinuedDate().toString()) : (null)),
                        (computer.getCompanyId() != null ? (computer.getCompanyId()) : 0),
                        id });*/
    }

    private final static String SQL_GET_NUMBER_COMPUTERS = "SELECT COUNT(*) AS nbComputers FROM computer";

    /**
     * @return count - The number of computers
     */
    public int getNumberOfComputers() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        return this.jdbcTemplate.queryForObject(SQL_GET_NUMBER_COMPUTERS, new Object[] { }, Integer.class);
    }

    /**
     * @param idBegin - The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listComputer - The list of companies in the selected range
     */
    public ArrayList<Computer> getComputerInRange(long idBegin, long idEnd) {
        String query = "SELECT * FROM computer LIMIT ?,?";
        ArrayList<Computer> computerList = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement selectPStatement = connection.prepareStatement(query);) {
            selectPStatement.setLong(1, idBegin);
            selectPStatement.setLong(2, idEnd);
            try (ResultSet resultSet = selectPStatement.executeQuery()) {
                while (resultSet.next()) {
                    computerList.add(ResultSetMapper.mapperComputer(resultSet));
                }
                resultSet.close();
            }
            connection.close();
            selectPStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;
    }

    public ArrayList<Computer> getComputerInRangeNb(long idFirst, int number, Page.SortingBy sort, String search) {
        ArrayList<Computer> computerList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);){
            if(sort != null ) {
                String sql = String.format(SQL_SEARCH, sort.toString());
                preparedStatement = connection.prepareStatement(sql);
            } else {
                preparedStatement = connection.prepareStatement(SQL_SEARCH_WITHOUT);
            }
            String searchPattern = search != null ? search + "%" : "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setLong(3, idFirst);
            preparedStatement.setInt(4, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                computerList.add(ResultSetMapper.mapperComputer(resultSet));
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * @param id - The id of the computer to delete
     */
    public void deleteComputer(Long id) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(SQL_DELETE, id);
    }

    public void deleteByCompany(long id) throws PersistenceException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.update(SQL_DELETE_BY_COMPANY, id);
        } catch (DataAccessException e) {
            LOG.error("ComputerDAO : deleteByCompany() catched DataAccesException", e);
            throw new PersistenceException("ComputerDAO : deleteByCompany() catched DataAccesException");
        }
    }
}