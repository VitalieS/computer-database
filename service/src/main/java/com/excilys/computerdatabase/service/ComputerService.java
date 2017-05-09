package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page.SortingBy;
import com.excilys.computerdatabase.persistence.dao.impl.ComputerDAO;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ObjectMappers;

/**
 * @author Vitalie SOVA
 */
@Service
public class ComputerService {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ComputerService.class);

    @Autowired
    private ComputerDAO computerDAO;

    /**
     * @return computerList - An ArrayList of computers
     */
    @Transactional(readOnly=true)
    public List<Computer> getComputerList() {
        return computerDAO.getComputerList();
    }

    /**
     * @param idToSelect - The id of the selected computer
     * @return computerById - The selected computer object
     */
    @Transactional(readOnly=true)
    public Computer getComputerById(Long idToSelect) {
        return computerDAO.getComputerById(idToSelect);
    }

    /**
     * @param newComputer - The new computer object
     * @return generatedKey - The generated key
     */
    @Transactional
    public Long createComputer(Computer computer) {
        computer.setIntroducedDate(computer.getIntroducedDate());
        computer.setDiscontinuedDate(computer.getDiscontinuedDate());
        return computerDAO.createComputer(computer);
    }

    /**
     * @param id - The id
     * @param computer - The computer
     */
    @Transactional
    public void updateComputer(Computer computer) {
        computerDAO.updateComputer(computer);
    }

    /**
     * @param idToDelete - The id of the computer to delete
     */
    @Transactional
    public void deleteComputer(long idToDelete) {
        computerDAO.deleteComputer(idToDelete);
    }

    /**
     * @param idBegin -  The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listAllComputer - An ArrayList of all computers
     */
    @Transactional(readOnly = true)
    public ArrayList<ComputerDTO> getComputerInRange(long idBegin, long idEnd) {
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        computerDAO.getComputerInRange(idBegin, idEnd).forEach(computer -> { listAllComputer.add(ObjectMappers.mapper(computer)); });
        return listAllComputer;
    }

    @Transactional(readOnly = true)
    public ArrayList<ComputerDTO> getComputerInRangeNb(Long debut, int nbId, SortingBy sort, String search) {
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        computerDAO.getComputerInRangeNb(debut, nbId, sort, search).forEach(computer -> { listAllComputer.add(ObjectMappers.mapper(computer)); });
        return listAllComputer;
    };

    /**
     * @return nbOfComputers - The number of computers
     */
    @Transactional(readOnly = true)
    public int getNumberOfComputers() {
        return computerDAO.getNumberOfComputers();
    }

    @Transactional(readOnly = true)
    public int getNumberOfPages(int elementsByPage) {
        return computerDAO.getNumberOfComputers() / elementsByPage;
    }
}
