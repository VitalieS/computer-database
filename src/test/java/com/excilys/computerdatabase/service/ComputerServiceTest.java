package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import com.excilys.computerdatabase.model.entities.Computer;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerServiceTest {

    /**
     * Test - getComputerList
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetComputerList() {
        int nb = ComputerService.INSTANCE.getNumberOfComputers();
        ArrayList<Computer> list = ComputerService.INSTANCE.getComputerList();
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
    public void testGetComputerByIDExistant() throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = ComputerService.INSTANCE.getComputerById(Long.valueOf(50));
        assertNotNull(c);
        // We can test with a random ID between 1 and the number of computers,
        // but the test can fail when it tests a deleted entry.
        /*
         * int randomID = ThreadLocalRandom.current().nextInt(1,
         * ComputerDAO.getNumberOfComputers()); Computer c1 =
         * ComputerDAO.getComputerById(Long.valueOf(randomID));
         * assertNotNull(c1);
         */
    }

    /**
     * Test - getComputerById when the Id is negative
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetComputerByIDNegative() throws SQLException, ClassNotFoundException, ConfigurationException {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = ComputerService.INSTANCE
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
    public void testCreate() throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = new Computer("New computer Insert");
        int numberofComputers = ComputerService.INSTANCE.getNumberOfComputers();
        Long newId = ComputerService.INSTANCE.createComputer(c);
        assertNotNull(ComputerService.INSTANCE.getComputerById(newId));
        System.out.println("This is number of computers" + numberofComputers);
        assertEquals(numberofComputers + 1, ComputerService.INSTANCE.getNumberOfComputers());
        ComputerService.INSTANCE.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testDelete() throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = new Computer("New Computer Delete");
        int numberofComputers = ComputerService.INSTANCE.getNumberOfComputers();
        Long newId = ComputerService.INSTANCE.createComputer(c);

        ComputerService.INSTANCE.deleteComputer(newId);

        assertNull(ComputerService.INSTANCE.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerService.INSTANCE.getNumberOfComputers());
    }

    /**
     * Test - Update a computer
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testUpdate() throws SQLException, ClassNotFoundException, ConfigurationException {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        Computer c = new Computer("New Computer Update");
        Long newId = ComputerService.INSTANCE.createComputer(c);
        int numberofComputers = ComputerService.INSTANCE.getNumberOfComputers();

        // Je modifie cet ordinateur
        Computer c1 = new Computer("New ComputerUpdate");
        ComputerService.INSTANCE.updateComputer(newId, c1);

        assertNotNull(ComputerService.INSTANCE.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerService.INSTANCE.getNumberOfComputers());

        // Je supprime cet ordinateur
        ComputerService.INSTANCE.deleteComputer(newId);

    }

    /**
     * Test - getNumberOfComputers
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetNumberOfComputers() throws SQLException, ClassNotFoundException, ConfigurationException {
        Computer c = new Computer("New Computer Test Number");
        int nb = ComputerService.INSTANCE.getNumberOfComputers();
        Long newC = ComputerService.INSTANCE.createComputer(c);
        ComputerService.INSTANCE.deleteComputer(newC);
        int newNb = ComputerService.INSTANCE.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    /**
     * Test - Creation of Computer Objects
     *
     * @throws SQLException
     */
    @Test
    public void testCreateComputerObject() throws SQLException {
        // Bean pattern
        Computer c1 = new Computer("Test Create Bean");
        c1.setComputerId(Long.valueOf(5));
        c1.setCompanyId(Long.valueOf(8));
        assertNotNull(c1);

        // Telescope pattern
        Computer c2 = new Computer("Test Create Telescope Computer", null, null,
                Long.valueOf(5));
        assertNotNull(c2);

        // Builder pattern
        Computer c3 = new Computer.ComputerBuilder()
                .name("Test Create Build Computer").id(Long.valueOf(5)).build();
        assertNotNull(c3);
    }

    @Test
    public void testgetComputerInRange() throws SQLException, ConfigurationException {
        ArrayList<Computer> haha;
        haha = ComputerService.INSTANCE.getComputerInRange(1, 10);
        System.out.println(haha);
        assertEquals(10, haha.size());
    }

}
