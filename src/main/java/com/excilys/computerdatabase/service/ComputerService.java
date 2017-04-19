package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page.SortingBy;
import com.excilys.computerdatabase.persistance.dao.impl.ComputerDAO;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.persistance.mappers.ComputerMapper;

/**
 * @author Vitalie SOVA
 */
public enum ComputerService {
    INSTANCE;

    private ComputerDAO compDAO = ComputerDAO.ComputerDao;

    /**
     * @return computerList - An ArrayList of computers
     */
    public ArrayList<Computer> getComputerList() {
        return compDAO.getComputerList();
    }

    /**
     * @param idToSelect - The id of the selected computer
     * @return computerById - The selected computer object
     */
    public Computer getComputerById(Long idToSelect) {
        return compDAO.getComputerById(idToSelect);
    }

    /**
     * @param newComputer - The new computer object
     * @return generatedKey - The generated key
     */
    public long createComputer(ComputerDTO newComputer) {
        return compDAO.createComputer(newComputer);
    }

    /**
     * @param id - The id
     * @param computer - The computer
     */
    public void updateComputer(Long id, ComputerDTO computer) {
        compDAO.updateComputer(id, computer);
    }

    /**
     * @param idToDelete - The id of the computer to delete
     */
    public void deleteComputer(long idToDelete) {
        compDAO.deleteComputer(idToDelete);
    }

    /**
     * @param idBegin -  The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listAllComputer - An ArrayList of all computers
     */
    public ArrayList<ComputerDTO> getComputerInRange(long idBegin, long idEnd) {
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> { listAllComputer.add(ComputerMapper.mapper(computer));
        });
        return listAllComputer;
    }

    public ArrayList<ComputerDTO> getComputerInRangeNb(Long debut, int nbId, SortingBy sort, String search) {
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        compDAO.getComputerInRangeNb(debut, nbId, sort, search).forEach(computer -> { listAllComputer.add(ComputerMapper.mapper(computer)); });
        return listAllComputer;
    };

    /**
     * @return nbOfComputers - The number of computers
     */
    public int getNumberOfComputers() {
        return compDAO.getNumberOfComputers();
    }

    public static int getNumberOfPages(int elementsByPage) {
        return ComputerDAO.ComputerDao.getNumberOfComputers() / elementsByPage;
    }
}
