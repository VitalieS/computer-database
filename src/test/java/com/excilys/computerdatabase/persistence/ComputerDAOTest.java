package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import com.excilys.computerdatabase.model.entities.Computer;
import com.excilys.computerdatabase.persistance.ComputerDAO;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerDAOTest {

    /**
     * Test - getComputerList
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetComputerList() {
        int nb = ComputerDAO.ComputerDao.getNumberOfComputers();
        ArrayList<Computer> list = ComputerDAO.ComputerDao.getComputerList();
        assertEquals(nb, list.size());
    }

    /**
     * Test - getComputerById when the Id exists
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetComputerByIDExistant()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = ComputerDAO.ComputerDao.getComputerById(Long.valueOf(50));
        assertNotNull(c);

    }

    /**
     * Test - getComputerById when the Id is negative
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetComputerByIDNegative()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = ComputerDAO.ComputerDao
                .getComputerById(Long.valueOf(randomNegative));
        assertNull(c);
    }

    /**
     * Test - Crate a new computer
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testCreateComputer()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = new Computer("New computer Insert");
        int numberofComputers = ComputerDAO.ComputerDao.getNumberOfComputers();
        Long newId = ComputerDAO.ComputerDao.createComputer(c);
        assertNotNull(ComputerDAO.ComputerDao.getComputerById(newId));
        assertEquals(numberofComputers + 1,
                ComputerDAO.ComputerDao.getNumberOfComputers());
        ComputerDAO.ComputerDao.deleteComputer(newId);
    }

    /**
     * Test - Update a computer
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testUpdateComputer()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        Computer c = new Computer("New Computer Update");
        Long newId = ComputerDAO.ComputerDao.createComputer(c);
        int numberofComputers = ComputerDAO.ComputerDao.getNumberOfComputers();

        // Je modifie cet ordinateur
        Computer c1 = new Computer("New ComputerUpdate");
        ComputerDAO.ComputerDao.updateComputer(newId, c1);

        assertNotNull(ComputerDAO.ComputerDao.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerDAO.ComputerDao.getNumberOfComputers());

        // Je supprime cet ordinateur
        ComputerDAO.ComputerDao.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testDeleteComputer()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = new Computer("New Computer Delete");
        int numberofComputers = ComputerDAO.ComputerDao.getNumberOfComputers();

        Long newId = ComputerDAO.ComputerDao.createComputer(c);

        ComputerDAO.ComputerDao.deleteComputer(newId);

        assertNull(ComputerDAO.ComputerDao.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerDAO.ComputerDao.getNumberOfComputers());
    }

    /**
     * Test - getNumberOfComputers
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetNumberOfComputers()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = new Computer("New Computer Test Number");
        int nb = ComputerDAO.ComputerDao.getNumberOfComputers();
        Long newC = ComputerDAO.ComputerDao.createComputer(c);
        ComputerDAO.ComputerDao.deleteComputer(newC);
        int newNb = ComputerDAO.ComputerDao.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    @Test
    public void testgetComputerInRange() throws SQLException, ConfigurationException {
        ArrayList<Computer> haha;
        haha = ComputerDAO.ComputerDao.getComputerInRange(1, 10);
        assertEquals(10, haha.size());
    }

}
