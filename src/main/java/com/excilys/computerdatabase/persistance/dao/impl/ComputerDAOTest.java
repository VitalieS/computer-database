package com.excilys.computerdatabase.persistance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistance.ConnectionHikari;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.persistance.mappers.ResultSetMapper;

/**
 * @author Vitalie SOVA
 *
 */
public enum ComputerDAOTest {
    ComputerDao;

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ComputerDAOTest.class);

    /**
     * @return computerList - The list of computers
     */
    public ArrayList<Computer> getComputerList() {
        ArrayList<Computer> computerList = new ArrayList<Computer>();
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM computer");
            while (resultSet.next()) {
                computerList.add(ResultSetMapper.INSTANCE.mapperComputer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return computerList;
    }

    /**
     * @param choiceId - The id of the selected computer
     * @return computer - The selected computer object
     */
    public Computer getComputerById(Long choiceId) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT c.id as id, c.name as name, c.introduced as introduced, c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?");
            preparedStatement.setLong(1, choiceId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                computer = ResultSetMapper.INSTANCE.mapperComputer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, preparedStatement);
        }
        return computer;
    }

    /**
     * @param c - The computer object to create
     * @return generatedKey - The generated Key
     */
    public Long createComputer(ComputerDTO c) {
        String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(";
        if (c.getComputerName() == null) {
            query += null + ", ";
        } else {
            query += "'" + c.getComputerName() + "', ";
        }
        if (c.getIntroducedDate() == null) {
            query += null + ", ";
        } else {
            query += "'" + c.getIntroducedDate() + "', ";
        }
        if (c.getDiscontinuedDate() == null) {
            query += null + ", ";
        } else {
            query += "'" + c.getDiscontinuedDate() + "', ";
        }
        if (c.getCompanyId() == null) {
            query += null + ")";
        } else {
            query += c.getCompanyId() + ")";
        }

        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Long generatedkey = null;
        try {
            statement = connection.createStatement();
            int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                LOG.info("Succes");
            } else {
                LOG.info("Fail");
            }
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedkey = resultSet.getLong(1);
                LOG.info("Auto Generated Primary Key " + generatedkey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return generatedkey;
    }

    /**
     * @param id - The id of the computer to update
     * @param c - The computer object to update with
     */
    public void updateComputer(Long id, ComputerDTO c) {
        String query = "UPDATE computer SET name = '" + c.getComputerName()
        + "', introduced = ";
        if (c.getIntroducedDate() == null) {
            query += null + ", discontinued = ";
        } else {
            query += "'" + c.getIntroducedDate() + "', discontinued = ";
        }
        if (c.getDiscontinuedDate() == null) {
            query += null + ", company_id = ";
        } else {
            query += "'" + c.getDiscontinuedDate() + "', company_id = ";
        }
        if (c.getCompanyId() == null) {
            query += null + " WHERE id = " + id;
        } else {
            query += c.getCompanyId() + " WHERE id = " + id;
        }
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                LOG.info("Succes");
            } else {
                LOG.info("Fail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(statement);
        }

    }

    /**
     * @param id - The id of the computer to delete
     */
    public void deleteComputer(Long id) {
        String query = "DELETE FROM computer WHERE id = " + id;
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                LOG.info("Succes");
            } else {
                LOG.info("Fail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(statement);
        }
    }

    /**
     * @return count - The number of computers
     */
    public int getNumberOfComputers() {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) AS nbOfComputers FROM computer");
            while (resultSet.next()) {
                count = resultSet.getInt("nbOfComputers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        }
        return count;
    }

    /**
     * @param idBegin - The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listComputer - The list of companies in the selected range
     */
    public ArrayList<Computer> getComputerInRange(long idBegin, long idEnd) {
        String query = "SELECT * FROM computer LIMIT ?,?";
        ArrayList<Computer> computerList = new ArrayList<>();
        try (Connection connection = ConnectionHikari.CONNECTION.getConnection();
                PreparedStatement selectPStatement = connection.prepareStatement(query);) {
            selectPStatement.setLong(1, idBegin);
            selectPStatement.setLong(2, idEnd);
            try (ResultSet resultSet = selectPStatement.executeQuery()) {
                while (resultSet.next()) {
                    computerList.add(ResultSetMapper.INSTANCE.mapperComputer(resultSet));
                }
                ConnectionHikari.CONNECTION.close(resultSet, selectPStatement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;
    }

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String COMPANY_NAME = "company_name";
    private static final String COMPANY_ID            = "company_id";

    private static final String SQL_SEARCH = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name like ? ORDER BY %s ASC LIMIT ?,?";

    private static final String SQL_SEARCH_WITHOUT = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name like ? LIMIT ?,?";

    public List<Computer> getComputerInRangeNb(long idFirst, int number, Page.SortingBy sort, String search) {
        List<Computer> computerList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionHikari.CONNECTION.getConnection();
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
                computerList.add(ResultSetMapper.INSTANCE.mapperComputer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, preparedStatement);
        }
        return computerList;
    }
}