package main.java.com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import main.java.com.excilys.computerdatabase.model.entities.Computer;

/**
 * @author Vitalie SOVA
 *
 */
public enum ComputerDAO {
    ComputerDao;

    /**
     * @return computerList - The list of computers
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public ArrayList<Computer> getComputerList() throws SQLException, ConfigurationException  {
        ArrayList<Computer> computerList = new ArrayList<Computer>();
        Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet r = null;
        try {
            statement = connection.createStatement();
            r = statement.executeQuery("SELECT * FROM computer");
            while (r.next()) {
                Computer c = new Computer.ComputerBuilder()
                        .name(r.getString("name")).id(r.getLong("id"))
                        .company(r.getLong("company_id")).build();
                if (r.getString("introduced") != null) {
                    c.setIntroducedDate(r.getDate("introduced").toLocalDate());
                }
                if (r.getString("discontinued") != null) {
                    c.setDiscontinuedDate(r.getDate("discontinued").toLocalDate());
                }
                computerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(r);
        }
        return computerList;

    }

    /**
     * @param choiceId
     *            - The id of the selected computer
     * @return computer - The selected computer object
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public Computer getComputerById(Long choiceId) throws SQLException, ConfigurationException  {
        Connection connection = ConnectionDB.CONNECTION.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Computer computer = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT c.id as id ,c.name as name ,c.introduced as introduced ,c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?");
            preparedStatement.setLong(1, choiceId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                computer = new Computer.ComputerBuilder()
                        .name(resultSet.getString("name"))
                        .id(resultSet.getLong("id"))
                        .company(resultSet.getLong("company_id")).build();
                if (resultSet.getString("introduced") != null) {
                    computer.setIntroducedDate(
                            resultSet.getDate("introduced").toLocalDate());
                }
                if (resultSet.getString("discontinued") != null) {
                    computer.setDiscontinuedDate(
                            resultSet.getDate("discontinued").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(preparedStatement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);
        }
        return computer;

    }

    /**
     * @param c
     *            - The computer object to create
     * @return generatedKey - The generated Key
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public Long createComputer(Computer c) throws SQLException, ConfigurationException  {
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

        Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Long generatedkey = null;
        try {
            statement = connection.createStatement();
            int results = statement.executeUpdate(query,
                    Statement.RETURN_GENERATED_KEYS);
            if (results == 1) {
                System.out.println("Succes");
            } else {
                System.out.println("Fail");
            }

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedkey = resultSet.getLong(1);
                System.out
                .println("Auto Generated Primary Key " + generatedkey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);
        }
        return generatedkey;
    }

    /**
     * @param id
     *            - The id of the computer to update
     * @param c
     *            - The computer object to update with
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public void updateComputer(Long id, Computer c) throws SQLException, ConfigurationException {
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
        Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
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
        } finally {
            ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
        }

    }

    /**
     * @param id
     *            - The id of the computer to delete
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public void deleteComputer(Long id) throws SQLException, ConfigurationException {
        String query = "DELETE FROM computer WHERE id = " + id;
        Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
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
        } finally {
            ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
        }
    }

    /**
     * @return count - The number of computers
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ConfigurationException 
     */
    public int getNumberOfComputers() throws ConfigurationException, SQLException {
        Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT COUNT(*) AS nbOfComputers FROM computer");
            while (resultSet.next()) {
                count = resultSet.getInt("nbOfComputers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);
        }
        return count;
    }

    /**
     * @param idBegin
     *            - The id of the first computer
     * @param idEnd
     *            - The id of the last computer
     * @return listComputer - The list of companies in the selected range
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ConfigurationException 
     */
    public ArrayList<Computer> getComputerInRange(long idBegin, long idEnd) throws ConfigurationException {
        String query = "SELECT * FROM computer LIMIT ?,?";
        ArrayList<Computer> listComputer = new ArrayList<>();
        try (Connection conn = ConnectionDB.CONNECTION.getConnection();
                PreparedStatement selectPStatement = conn
                        .prepareStatement(query);) {
            selectPStatement.setLong(1, idBegin);
            selectPStatement.setLong(2, idEnd);
            System.out.println(idBegin);
            System.out.println(idEnd);
            try (ResultSet rs = selectPStatement.executeQuery()) {
                while (rs.next()) {
                    LocalDate getIntroduced;
                    if (rs.getTimestamp(3) != null) {
                        getIntroduced = rs.getTimestamp(3).toLocalDateTime()
                                .toLocalDate();
                    } else {
                        getIntroduced = null;
                    }
                    LocalDate getDiscontinued;
                    if (rs.getTimestamp(4) != null) {
                        getDiscontinued = rs.getTimestamp(4).toLocalDateTime()
                                .toLocalDate();
                    } else {
                        getDiscontinued = null;
                    }
                    listComputer.add(new Computer.ComputerBuilder()
                            .id(Long.valueOf(rs.getInt(1)))
                            .name(rs.getString(2)).introducedDate(getIntroduced)
                            .discontinuedDate(getDiscontinued)
                            .company(Long.valueOf(rs.getInt(5))).build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nonnb " + listComputer);
        return listComputer;
    }

    public List<Computer> getComputerInRangeNb(int number, long idFirst) throws ConfigurationException, SQLException {
        List<Computer> computers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionDB.CONNECTION.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT c.id as id ,c.name as name ,c.introduced as introduced ,c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id ORDER BY c.id ASC LIMIT ?,?");
            preparedStatement.setLong(1, idFirst);
            preparedStatement.setInt(2, number);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LocalDate getIntroduced;
                if (rs.getTimestamp(3) != null) {
                    getIntroduced = rs.getTimestamp(3).toLocalDateTime()
                            .toLocalDate();
                } else {
                    getIntroduced = null;
                }
                LocalDate getDiscontinued;
                if (rs.getTimestamp(4) != null) {
                    getDiscontinued = rs.getTimestamp(4).toLocalDateTime()
                            .toLocalDate();
                } else {
                    getDiscontinued = null;
                }
                computers.add(new Computer.ComputerBuilder()
                        .id(Long.valueOf(rs.getInt(1)))
                        .name(rs.getString(2)).introducedDate(getIntroduced)
                        .discontinuedDate(getDiscontinued)
                        .company(Long.valueOf(rs.getInt(5))).build());
            }
        } catch (SQLException e) {
            System.out.println("Hmmmmm");
            e.printStackTrace();
        } finally {
            //ConnectionDB.CONNECTION.closeConnection(connection);
            //ConnectionDB.CONNECTION.closeStatement(preparedStatement);
            //ConnectionDB.CONNECTION.closeResulSet(rs);
        }
        System.out.println("nb " + computers);
        return computers;
    }

}
