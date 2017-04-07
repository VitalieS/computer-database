package com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.entities.Computer;

/**
 * @author Vitalie SOVA
 *
 */
public enum ComputerDAO {
    ComputerDao;


    /**
     * @return computerList - The list of computers
     */
    public ArrayList<Computer> getComputerList() {
        ArrayList<Computer> computerList = new ArrayList<Computer>();
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM computer");
            while (resultSet.next()) {
                Computer c = new Computer.ComputerBuilder()
                        .name(resultSet.getString("name")).id(resultSet.getLong("id"))
                        .company(resultSet.getLong("company_id")).build();
                if (resultSet.getDate("introduced") != null) {
                    System.out.println(resultSet.getString("introduced"));
                    System.out.println(resultSet.getDate("introduced"));
                    c.setIntroducedDate(resultSet.getDate("introduced").toLocalDate());
                }
                if (resultSet.getDate("discontinued") != null) {
                    c.setDiscontinuedDate(resultSet.getDate("discontinued").toLocalDate());
                }
                computerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);*/
        }
        System.out.println("Is it null ?" + computerList);
        return computerList;

    }

    /**
     * @param choiceId
     *            - The id of the selected computer
     * @return computer - The selected computer object
     */
    public Computer getComputerById(Long choiceId) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
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
            ConnectionHikari.CONNECTION.close(resultSet, preparedStatement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(preparedStatement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);*/
        }
        return computer;

    }

    /**
     * @param c - The computer object to create
     * @return generatedKey - The generated Key
     */
    public Long createComputer(Computer c) {
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
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
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
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);*/
        }
        return generatedkey;
    }

    /**
     * @param id - The id of the computer to update
     * @param c - The computer object to update with
     */
    public void updateComputer(Long id, Computer c) {
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
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
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
            ConnectionHikari.CONNECTION.close(statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);*/
        }

    }

    /**
     * @param id - The id of the computer to delete
     */
    public void deleteComputer(Long id) {
        String query = "DELETE FROM computer WHERE id = " + id;
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
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
            ConnectionHikari.CONNECTION.close(statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);*/
        }
    }

    /**
     * @return count - The number of computers
     */
    public int getNumberOfComputers() {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
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
            ConnectionHikari.CONNECTION.close(resultSet, statement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(statement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet);*/
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
        ArrayList<Computer> listComputer = new ArrayList<>();
        try (Connection connection = ConnectionHikari.CONNECTION.getConnection();
                // Connection connection = ConnectionDB.CONNECTION.getConnection();
                PreparedStatement selectPStatement = connection.prepareStatement(query);) {
            selectPStatement.setLong(1, idBegin);
            selectPStatement.setLong(2, idEnd);
            try (ResultSet resultSet = selectPStatement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate getIntroduced;
                    if (resultSet.getTimestamp(3) != null) {
                        getIntroduced = resultSet.getTimestamp(3).toLocalDateTime().toLocalDate();
                    } else {
                        getIntroduced = null;
                    }
                    LocalDate getDiscontinued;
                    if (resultSet.getTimestamp(4) != null) {
                        getDiscontinued = resultSet.getTimestamp(4).toLocalDateTime().toLocalDate();
                    } else {
                        getDiscontinued = null;
                    }
                    listComputer.add(new Computer.ComputerBuilder()
                            .id(Long.valueOf(resultSet.getInt(1)))
                            .name(resultSet.getString(2)).introducedDate(getIntroduced)
                            .discontinuedDate(getDiscontinued)
                            .company(Long.valueOf(resultSet.getInt(5))).build());
                }
                ConnectionHikari.CONNECTION.close(resultSet, selectPStatement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listComputer;
    }

    public List<Computer> getComputerInRangeNb(long idFirst, int number) {
        List<Computer> computers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionHikari.CONNECTION.getConnection();
            // connection = ConnectionDB.CONNECTION.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT c.id as id ,c.name as name ,c.introduced as introduced ,c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id ORDER BY c.id ASC LIMIT ?,?");
            preparedStatement.setLong(1, idFirst);
            preparedStatement.setInt(2, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LocalDate getIntroduced;
                if (resultSet.getTimestamp(3) != null) {
                    getIntroduced = resultSet.getTimestamp(3).toLocalDateTime()
                            .toLocalDate();
                } else {
                    getIntroduced = null;
                }
                LocalDate getDiscontinued;
                if (resultSet.getTimestamp(4) != null) {
                    getDiscontinued = resultSet.getTimestamp(4).toLocalDateTime()
                            .toLocalDate();
                } else {
                    getDiscontinued = null;
                }
                computers.add(new Computer.ComputerBuilder()
                        .id(Long.valueOf(resultSet.getInt(1))).name(resultSet.getString(2))
                        .introducedDate(getIntroduced)
                        .discontinuedDate(getDiscontinued)
                        .company(Long.valueOf(resultSet.getInt(5))).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            ConnectionHikari.CONNECTION.close(resultSet, preparedStatement);
            /* ConnectionDB.CONNECTION.closeConnection(connection);
            ConnectionDB.CONNECTION.closeStatement(preparedStatement);
            ConnectionDB.CONNECTION.closeResulSet(resultSet); */
        }
        System.out.println("nb " + computers);
        return computers;
    }

    public int countPages(int nbId) {
        Connection connection = ConnectionHikari.CONNECTION.getConnection();
        // Connection connection = ConnectionDB.CONNECTION.getConnection();
        int maxId = 0;
        int nbPages = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM computer");
            resultSet.next();
            maxId = resultSet.getInt("count");
            resultSet.close();
            if (nbId != 0) {
                if (maxId % nbId == 0) {
                    nbPages = maxId / nbId;
                } else {
                    nbPages = maxId / nbId + 1;
                }
            }
            ConnectionHikari.CONNECTION.close(resultSet, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nbPages;
    }

}
