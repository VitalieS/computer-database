package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page.SortingBy;
import com.excilys.computerdatabase.persistance.dao.impl.ComputerDAOTest;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.persistance.mappers.ComputerMapper;

/**
 * @author Vitalie SOVA
 */
public enum ComputerServiceTest {
    INSTANCE;

    private static ComputerDAOTest computerDAO = ComputerDAOTest.ComputerDao;

    /**
     * @return computerList - An ArrayList of computers
     */
    public ArrayList<Computer> getComputerList() {
        return computerDAO.getComputerList();
    }

    /**
     * @param idToSelect - The id of the selected computer
     * @return computerById - The selected computer object
     */
    public Computer getComputerById(Long idToSelect) {
        return computerDAO.getComputerById(idToSelect);
    }

    /**
     * @param newComputer - The new computer object
     * @return generatedKey - The generated key
     */
    public long createComputer(ComputerDTO newComputer) {
        return computerDAO.createComputer(newComputer);
    }

    /**
     * @param id - The id
     * @param computer - The computer
     */
    public void updateComputer(Long id, ComputerDTO computer) {
        computerDAO.updateComputer(id, computer);
    }

    /**
     * @param idToDelete - The id of the computer to delete
     */
    public void deleteComputer(long idToDelete) {
        computerDAO.deleteComputer(idToDelete);
    }

    /**
     * @param idBegin -  The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listAllComputer - An ArrayList of all computers
     */
    public ArrayList<ComputerDTO> getComputerInRange(long idBegin, long idEnd) {
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        computerDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> { listAllComputer.add(ComputerMapper.mapper(computer));
        });
        return listAllComputer;
    }

    public ArrayList<ComputerDTO> getComputerInRangeNb(Long debut, int nbId, SortingBy sort, String search) {
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        computerDAO.getComputerInRangeNb(debut, nbId, sort, search).forEach(computer -> { listAllComputer.add(ComputerMapper.mapper(computer)); });
        return listAllComputer;
    };

    /**
     * @return nbOfComputers - The number of computers
     */
    public int getNumberOfComputers() {
        return computerDAO.getNumberOfComputers();
    }

    public static int getNumberOfPages(int elementsByPage) {
        return computerDAO.getNumberOfComputers() / elementsByPage;
    }
}
