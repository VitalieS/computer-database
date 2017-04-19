package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistance.dao.impl.ComputerDAO;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerDAOTest {

    /**
     * Test - getComputerList
     */
    @Test
    public void testGetComputerList() {
        int nb = ComputerDAO.ComputerDao.getNumberOfComputers();
        ArrayList<Computer> list = ComputerDAO.ComputerDao.getComputerList();
        assertEquals(nb, list.size());
    }

    /**
     * Test - getComputerById when the Id exists
     */
    @Test
    public void testGetComputerByIDExistant() {
        Computer c = ComputerDAO.ComputerDao.getComputerById(Long.valueOf(50));
        assertNotNull(c);

    }

    /**
     * Test - getComputerById when the Id is negative
     */
    @Test
    public void testGetComputerByIDNegative() {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = ComputerDAO.ComputerDao.getComputerById(Long.valueOf(randomNegative));
        assertNull(c);
    }

    /**
     * Test - Crate a new computer
     */
    @Test
    public void testCreateComputer() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer Insert").build();
        int numberofComputers = ComputerDAO.ComputerDao.getNumberOfComputers();
        Long newId = ComputerDAO.ComputerDao.createComputer(c);
        assertNotNull(ComputerDAO.ComputerDao.getComputerById(newId));
        assertEquals(numberofComputers + 1, ComputerDAO.ComputerDao.getNumberOfComputers());
        ComputerDAO.ComputerDao.deleteComputer(newId);
    }

    /**
     * Test - Update a computer
     */
    @Test
    public void testUpdateComputer() {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer Update").build();;
        Long newId = ComputerDAO.ComputerDao.createComputer(c);
        int numberofComputers = ComputerDAO.ComputerDao.getNumberOfComputers();
        // Je modifie cet ordinateur
        ComputerDTO c1 = new ComputerDTO.ComputerBuilder("New computer update").build();;
        ComputerDAO.ComputerDao.updateComputer(newId, c1);
        assertNotNull(ComputerDAO.ComputerDao.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerDAO.ComputerDao.getNumberOfComputers());
        // Je supprime cet ordinateur
        ComputerDAO.ComputerDao.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     */
    @Test
    public void testDeleteComputer()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer delete").build();;
        int numberofComputers = ComputerDAO.ComputerDao.getNumberOfComputers();

        Long newId = ComputerDAO.ComputerDao.createComputer(c);

        ComputerDAO.ComputerDao.deleteComputer(newId);

        assertNull(ComputerDAO.ComputerDao.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerDAO.ComputerDao.getNumberOfComputers());
    }

    /**
     * Test - getNumberOfComputers
     */
    @Test
    public void testGetNumberOfComputers() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer test number").build();;
        int nb = ComputerDAO.ComputerDao.getNumberOfComputers();
        Long newC = ComputerDAO.ComputerDao.createComputer(c);
        ComputerDAO.ComputerDao.deleteComputer(newC);
        int newNb = ComputerDAO.ComputerDao.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    @Test
    public void testgetComputerInRange() {
        ArrayList<Computer> listComputers = ComputerDAO.ComputerDao.getComputerInRange(1, 10);
        assertEquals(10, listComputers.size());
    }

}
