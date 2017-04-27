package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page.SortingBy;
import com.excilys.computerdatabase.persistence.dao.impl.ComputerDAO;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ComputerMapper;

/**
 * @author Vitalie SOVA
 */
@Service/*("computerService")*/
public class ComputerService {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(ComputerService.class);

    @Autowired
    private ComputerDAO computerDAO;

    public ComputerDAO getComputerDAO() {
        return computerDAO;
    }

    public void setComputerDAO(ComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }

    /**
     * @return computerList - An ArrayList of computers
     */
    public List<Computer> getComputerList() {
        LOG.info("HuhService" + computerDAO.getComputerList());
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
        LOG.info("Trying to get the computers - Service");
        ArrayList<ComputerDTO> listAllComputer = new ArrayList<>();
        LOG.info("The list is empty" + listAllComputer);
        computerDAO.getComputerInRangeNb(debut, nbId, sort, search).forEach(computer -> { listAllComputer.add(ComputerMapper.mapper(computer)); });
        LOG.info("The returned list is empty" + listAllComputer);
        return listAllComputer;
    };

    /**
     * @return nbOfComputers - The number of computers
     */
    public int getNumberOfComputers() {
        return computerDAO.getNumberOfComputers();
    }

    public int getNumberOfPages(int elementsByPage) {
        return computerDAO.getNumberOfComputers() / elementsByPage;
    }
}
