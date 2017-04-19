package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerServiceTest {

    /**
     * Test - getComputerList
     */
    @Test
    public void testGetComputerList() {
        int nb = ComputerService.INSTANCE.getNumberOfComputers();
        ArrayList<Computer> list = ComputerService.INSTANCE.getComputerList();
        assertEquals(nb, list.size());
    }

    /**
     * Test - getComputerById when the Id exists
     */
    @Test
    public void testGetComputerByIDExistant() {
        Computer c = ComputerService.INSTANCE.getComputerById(Long.valueOf(50));
        assertNotNull(c);
    }

    /**
     * Test - getComputerById when the Id is negative
     */
    @Test
    public void testGetComputerByIDNegative() {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = ComputerService.INSTANCE.getComputerById(Long.valueOf(randomNegative));
        assertNull(c);
    }

    /**
     * Test - Crate a new computer
     */
    @Test
    public void testCreate() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Insert").build();
        int numberofComputers = ComputerService.INSTANCE.getNumberOfComputers();
        Long newId = ComputerService.INSTANCE.createComputer(c);
        assertNotNull(ComputerService.INSTANCE.getComputerById(newId));
        System.out.println("This is number of computers" + numberofComputers);
        assertEquals(numberofComputers + 1, ComputerService.INSTANCE.getNumberOfComputers());
        ComputerService.INSTANCE.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     */
    @Test
    public void testDelete() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Delete").build();
        int numberofComputers = ComputerService.INSTANCE.getNumberOfComputers();
        Long newId = ComputerService.INSTANCE.createComputer(c);

        ComputerService.INSTANCE.deleteComputer(newId);

        assertNull(ComputerService.INSTANCE.getComputerById(newId));
        assertEquals(numberofComputers,
                ComputerService.INSTANCE.getNumberOfComputers());
    }

    /**
     * Test - Update a computer
     */
    @Test
    public void testUpdate() {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Update").build();
        Long newId = ComputerService.INSTANCE.createComputer(c);
        int numberofComputers = ComputerService.INSTANCE.getNumberOfComputers();
        // Je modifie cet ordinateur
        ComputerDTO c1 = new ComputerDTO.ComputerBuilder("New Computer Update").build();
        ComputerService.INSTANCE.updateComputer(newId, c1);
        assertNotNull(ComputerService.INSTANCE.getComputerById(newId));
        assertEquals(numberofComputers, ComputerService.INSTANCE.getNumberOfComputers());
        // Je supprime cet ordinateur
        ComputerService.INSTANCE.deleteComputer(newId);
    }

    /**
     * Test - getNumberOfComputers
     */
    @Test
    public void testGetNumberOfComputers() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Test Number").build();
        int nb = ComputerService.INSTANCE.getNumberOfComputers();
        Long newC = ComputerService.INSTANCE.createComputer(c);
        ComputerService.INSTANCE.deleteComputer(newC);
        int newNb = ComputerService.INSTANCE.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    @Test
    public void testGetComputerInRange() throws SQLException, ConfigurationException {
        ArrayList<ComputerDTO> listComputers = ComputerService.INSTANCE.getComputerInRange(1, 10);
        assertEquals(10, listComputers.size());
    }

}
