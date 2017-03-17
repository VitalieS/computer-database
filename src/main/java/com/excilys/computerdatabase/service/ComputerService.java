package main.java.com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.persistance.ComputerDAO;

/**
 * @author Vitalie SOVA
 *
 */

public class ComputerService {

    private ComputerDAO compDAO = ComputerDAO.ComputerDao;

    /**
     * @return computerList - An ArrayList of computers
     * @throws SQLException - The SQL Exception
     */
    public ArrayList<Computer> getComputerList() throws SQLException {
        ArrayList<Computer> computerList = compDAO.getComputerList();
        return computerList;
    }

    /**
     * @param idToSelect - The id of the selected computer
     * @return computerById - The selected computer object
     * @throws SQLException - The SQL Exception
     */
    public Computer getComputerById(Long idToSelect) throws SQLException {
        Computer computerById = compDAO.getComputerById(idToSelect);
        return computerById;
    }

    /**
     * @param newComputer - The new computer object
     * @return generatedKey - The generated key
     * @throws SQLException - The SQL Exception
     */
    public long createComputer(Computer newComputer) throws SQLException {
        long generatedKey = compDAO.createComputer(newComputer);
        return generatedKey;
    }

    /**
     * @param id - The id
     * @param computer - The computer
     * @throws SQLException - The SQL Exception
     */
    public void updateComputer(Long id, Computer computer) throws SQLException {
        compDAO.updateComputer(id, computer);
    }

    /**
     * @param idToDelete - The id of the computer to delete
     * @throws SQLException - The SQL Exception
     */
    public void deleteComputer(long idToDelete) throws SQLException {
        compDAO.deleteComputer(idToDelete);
    }

    /**
     * @return nbOfComputers - The number of computers
     * @throws SQLException - The SQL Exception
     */
    public int getNumberOfComputers() throws SQLException {
        int nbOfComputers = compDAO.getNumberOfComputers();
        return nbOfComputers;
    }

    /**
     * @param idBegin - The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listAllComputer - An ArrayList of all computers
     * @throws SQLException - The SQL Exception
     */
    public ArrayList<Computer> getComputerInRange(long idBegin, long idEnd)
            throws SQLException {
        /*
         * ArrayList<Computer> computerList =
         * compDAO.getComputerInRange(idBegin, idEnd);
         * System.out.println(compDAO.getComputerInRange(idBegin, idEnd));
         * System.out.println(computerList); return computerList;
         */
        ArrayList<Computer> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> {
            listAllComputer.add(computer);
        });
        return listAllComputer;
    }
}
