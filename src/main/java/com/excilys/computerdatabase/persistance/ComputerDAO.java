package main.java.com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.java.com.excilys.computerdatabase.model.Computer;

/**
 * @author Vitalie SOVA
 *
 */
public enum ComputerDAO {
    ComputerDao;

    /**
     * @return computerList - The list of computers
     * @throws SQLException - The SQL exception
     */
    public ArrayList<Computer> getComputerList() throws SQLException {
        ConnectionDB conDB = null;
        // try {
        ArrayList<Computer> computerList = new ArrayList<Computer>();

        conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQuerySelect("SELECT * FROM computer");
        ResultSet r = conDB.getResults();

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
        return computerList;
        /*
         * } finally { // conDB.closeConnexion(); }
         */
    }

    /**
     * @param choiceId - The id of the selected computer
     * @return computer - The selected computer object
     * @throws SQLException - The SQL exception
     */
    public Computer getComputerById(Long choiceId) throws SQLException {
        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQuerySelect("SELECT * FROM computer WHERE id=" + choiceId);
        ResultSet r = conDB.getResults();

        Computer computer = null;
        if (r.next()) {
            computer = new Computer.ComputerBuilder().name(r.getString("name"))
                    .id(r.getLong("id")).company(r.getLong("company_id"))
                    .build();
            if (r.getString("introduced") != null) {
                computer.setIntroducedDate(
                        r.getDate("introduced").toLocalDate());
            }
            if (r.getString("discontinued") != null) {
                computer.setDiscontinuedDate(
                        r.getDate("discontinued").toLocalDate());
            }
        }
        return computer;

    }

    /**
     * @param c - The computer object to create
     * @return generatedKey - The generated Key
     * @throws SQLException - The SQL exception
     */
    public Long createComputer(Computer c) throws SQLException {
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

        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQueryDataManipulation(query);

        Long generatedkey = null;
        ResultSet rs = conDB.getStatement().getGeneratedKeys();
        if (rs.next()) {
            generatedkey = rs.getLong(1);
            System.out.println("Auto Generated Primary Key " + generatedkey);
        }
        return generatedkey;
    }

    /**
     * @param id - The id of the computer to update
     * @param c - The computer object to update with
     * @throws SQLException - The SQL exception
     */
    public void updateComputer(Long id, Computer c) throws SQLException {
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

        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQueryDataManipulation(query);
    }

    /**
     * @param id - The id of the computer to delete
     * @throws SQLException - The SQL exception
     */
    public void deleteComputer(Long id) throws SQLException {
        String query = "DELETE FROM computer WHERE id = " + id;
        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQueryDataManipulation(query);
    }

    /**
     * @return count - The number of computers
     * @throws SQLException - The SQL exception
     */
    public int getNumberOfComputers() throws SQLException {
        String query = "SELECT COUNT(*) AS nbofcomputers FROM computer";
        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQuerySelect(query);
        ResultSet r = conDB.getResults();
        int count = 0;
        while (r.next()) {
            count = r.getInt("nbofComputers");
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
        return listComputer;
    }
}
