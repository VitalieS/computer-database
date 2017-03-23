package main.java.com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import main.java.com.excilys.computerdatabase.model.entities.Computer;
import main.java.com.excilys.computerdatabase.model.entities.Page;
import main.java.com.excilys.computerdatabase.persistance.ComputerDAO;

/**
 * @author Vitalie SOVA
 *
 */

public enum ComputerService {
    INSTANCE;

    private ComputerDAO compDAO = ComputerDAO.ComputerDao;

    /**
     * @return computerList - An ArrayList of computers
     * @throws SQLException
     *             - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public ArrayList<Computer> getComputerList()
            throws SQLException, ConfigurationException {
        ArrayList<Computer> computerList = compDAO.getComputerList();
        return computerList;
    }

    /**
     * @param idToSelect
     *            - The id of the selected computer
     * @return computerById - The selected computer object
     * @throws SQLException
     *             - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Computer getComputerById(Long idToSelect)
            throws SQLException, ConfigurationException {
        Computer computerById = compDAO.getComputerById(idToSelect);
        return computerById;
    }

    /**
     * @param newComputer
     *            - The new computer object
     * @return generatedKey - The generated key
     * @throws SQLException
     *             - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public long createComputer(Computer newComputer)
            throws SQLException, ConfigurationException {
        long generatedKey = compDAO.createComputer(newComputer);
        return generatedKey;
    }

    /**
     * @param id
     *            - The id
     * @param computer
     *            - The computer
     * @throws SQLException
     *             - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void updateComputer(Long id, Computer computer)
            throws SQLException, ConfigurationException {
        compDAO.updateComputer(id, computer);
    }

    /**
     * @param idToDelete
     *            - The id of the computer to delete
     * @throws SQLException
     *             - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void deleteComputer(long idToDelete)
            throws SQLException, ConfigurationException {
        compDAO.deleteComputer(idToDelete);
    }

    public void deleteComputers(
            List<Long> recuperationListSuppresionRequestPost)
                    throws SQLException, ConfigurationException {
        for (Long idToDelete : recuperationListSuppresionRequestPost) {
            System.out.println("J'essaie de supprimer" + idToDelete);
            compDAO.deleteComputer(idToDelete);
        }
    }

    /**
     * @return nbOfComputers - The number of computers
     * @throws SQLException
     *             - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public int getNumberOfComputers()
            throws SQLException, ConfigurationException {
        int nbOfComputers = compDAO.getNumberOfComputers();
        return nbOfComputers;
    }

    /**
     * @param idBegin
     *            - The id of the first computer
     * @param idEnd
     *            - The id of the last computer
     * @return listAllComputer - An ArrayList of all computers
     * @throws SQLException
     *             - The SQL Exception
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public ArrayList<Computer> getComputerInRange(long idBegin, long idEnd)
            throws SQLException, ConfigurationException {
        ArrayList<Computer> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> {
            listAllComputer.add(computer);
        });
        // System.out.println("List of computers in range:" + listAllComputer);
        return listAllComputer;
    }

    public List<Computer> getComputerInRangeNb(int elementsByPage, int i)
            throws SQLException, ConfigurationException {
        // TODO Auto-generated method stub
        ArrayList<Computer> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRangeNb(elementsByPage, i).forEach(computer -> {
            listAllComputer.add(computer);
        });
        // System.out.println("List of computers in range:" + listAllComputer);
        return listAllComputer;
    }

    public int pageNumber() throws SQLException, ConfigurationException {
        return ComputerDAO.ComputerDao.getNumberOfComputers()
                / Page.elementsByPage;
    }

}
