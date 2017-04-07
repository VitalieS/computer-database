package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

import com.excilys.computerdatabase.model.entities.Computer;
import com.excilys.computerdatabase.model.entities.Page;
import com.excilys.computerdatabase.persistance.ComputerDAO;

/**
 * @author Vitalie SOVA
 *
 */

public enum ComputerService {
    INSTANCE;

    private ComputerDAO compDAO = ComputerDAO.ComputerDao;

    /**
     * @return computerList - An ArrayList of computers
     */
    public ArrayList<Computer> getComputerList() {
        ArrayList<Computer> computerList = compDAO.getComputerList();
        return computerList;
    }

    /**
     * @param idToSelect
     *            - The id of the selected computer
     * @return computerById - The selected computer object
     */
    public Computer getComputerById(Long idToSelect) {
        Computer computerById = compDAO.getComputerById(idToSelect);
        return computerById;
    }

    /**
     * @param newComputer - The new computer object
     * @return generatedKey - The generated key
     */
    public long createComputer(Computer newComputer) {
        long generatedKey = compDAO.createComputer(newComputer);
        return generatedKey;
    }

    /**
     * @param id
     *            - The id
     * @param computer
     *            - The computer
     */
    public void updateComputer(Long id, Computer computer){
        compDAO.updateComputer(id, computer);
    }

    /**
     * @param idToDelete
     *            - The id of the computer to delete
     */
    public void deleteComputer(long idToDelete) {
        compDAO.deleteComputer(idToDelete);
    }

    public void deleteComputers(List<Long> recuperationListSuppresionRequestPost) {
        for (Long idToDelete : recuperationListSuppresionRequestPost) {
            compDAO.deleteComputer(idToDelete);
        }
    }

    /**
     * @return nbOfComputers - The number of computers
     */
    public int getNumberOfComputers() {
        return compDAO.getNumberOfComputers();
    }

    /**
     * @param idBegin
     *            - The id of the first computer
     * @param idEnd
     *            - The id of the last computer
     * @return listAllComputer - An ArrayList of all computers
     */
    public ArrayList<Computer> getComputerInRange(long idBegin, long idEnd) {
        ArrayList<Computer> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> {
            listAllComputer.add(computer);
        });
        return listAllComputer;
    }

    public List<Computer> getComputerInRangeNb(Long i, int elementsByPage) {
        List<Computer> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRangeNb(i, elementsByPage).forEach(computer -> {
            listAllComputer.add(computer);
        });
        return listAllComputer;
    }

    public static List<Computer> listComputers(long debut, int nbItems) {
        List<Computer> liste = new ArrayList<Computer>();
        ComputerDAO compDAO = ComputerDAO.ComputerDao;
        liste = compDAO.getComputerInRangeNb(debut, nbItems);
        return liste;
    }

    public int pageNumber() throws SQLException, ConfigurationException {
        return ComputerDAO.ComputerDao.getNumberOfComputers() / Page.elementsByPage;
    }

    public static int maxPages(int nbId) {
        ComputerDAO compDAO = ComputerDAO.ComputerDao;
        int nbPages = compDAO.countPages(nbId);
        return nbPages;
    };

}
