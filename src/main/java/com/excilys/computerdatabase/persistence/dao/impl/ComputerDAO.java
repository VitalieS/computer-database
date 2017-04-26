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
	
    private final static CompanyDAO COMPUTER_DAO_INSTANCE;
    private final static Logger LOG;

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
    public ArrayList<Computer> getComputerList() {
        ArrayList<Computer> computerList = new ArrayList<Computer>();
        ResultSet resultSet = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            resultSet = statement.executeQuery("SELECT * FROM computer");
            while (resultSet.next()) {
                computerList.add(ResultSetMapper.mapperComputer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * @param choiceId - The id of the selected computer
     * @return computer - The selected computer object
     */
    public Computer getComputerById(Long choiceId) {
        Computer computer = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id as id, c.name as name, c.introduced as introduced, c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?");
            preparedStatement.setLong(1, choiceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                computer = ResultSetMapper.mapperComputer(resultSet);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

        Long generatedkey = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                LOG.info("Succes");
            } else {
                LOG.info("Fail");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedkey = resultSet.getLong(1);
                LOG.info("Auto Generated Primary Key " + generatedkey);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                LOG.info("Succes");
            } else {
                LOG.info("Fail");
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id - The id of the computer to delete
     */
    public void deleteComputer(Long id) {
        String query = "DELETE FROM computer WHERE id = " + id;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            int results = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                LOG.info("Succes");
            } else {
                LOG.info("Fail");
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return count - The number of computers
     */
    public int getNumberOfComputers() {
        int count = 0;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS nbOfComputers FROM computer");
            while (resultSet.next()) {
                count = resultSet.getInt("nbOfComputers");
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

    private String ID = "id";
    private String NAME = "name";
    private String INTRODUCED = "introduced";
    private String DISCONTINUED = "discontinued";
    private String COMPANY_NAME = "company_name";
    private String COMPANY_ID = "company_id";

    String SQL_SEARCH = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name like ? ORDER BY %s ASC LIMIT ?,?";

    String SQL_SEARCH_WITHOUT = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id = company.id WHERE c.name LIKE ? OR company.name like ? LIMIT ?,?";

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
    
    public void deleteByCompany(Connection cn, long id) {
        try (PreparedStatement st = cn.prepareStatement("DELETE FROM computer WHERE company_id=?")) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            LOG.error("ComputerDAO : deleteByCompany() catched SQLException", e);
            e.printStackTrace();
        }
    }
}