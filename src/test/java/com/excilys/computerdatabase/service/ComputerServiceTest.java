package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.dao.impl.CompanyDAO;
import com.excilys.computerdatabase.persistence.dao.impl.ComputerDAO;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerServiceTest {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    private static ComputerDAO computerDAO = (ComputerDAO) context.getBean("computerDAO");
    private static CompanyDAO companyDAO = (CompanyDAO) context.getBean("companyDAO");
    private static ComputerService computerService = (ComputerService) context.getBean("computerService");
    private static CompanyService companyService = (CompanyService) context.getBean("companyService");

    /**
     * Test - getComputerList
     */
    @Test
    public void testGetComputerList() {
        int nb = computerService.getNumberOfComputers();
        List<Computer> list = computerService.getComputerList();
        assertEquals(nb, list.size());
    }

    /**
     * Test - getComputerById when the Id exists
     */
    @Test
    public void testGetComputerByIDExistant() {
        Computer c = computerService.getComputerById(Long.valueOf(50));
        assertNotNull(c);
    }

    /**
     * Test - getComputerById when the Id is negative
     */
    @Test
    public void testGetComputerByIDNegative() {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = computerService.getComputerById(Long.valueOf(randomNegative));
        assertNull(c);
    }

    /**
     * Test - Crate a new computer
     */
    @Test
    public void testCreate() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Insert").build();
        int numberofComputers = computerService.getNumberOfComputers();
        Long newId = computerService.createComputer(c);
        assertNotNull(computerService.getComputerById(newId));
        System.out.println("This is number of computers" + numberofComputers);
        assertEquals(numberofComputers + 1, computerService.getNumberOfComputers());
        computerService.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     */
    @Test
    public void testDelete() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Delete").build();
        int numberofComputers = computerService.getNumberOfComputers();
        Long newId = computerService.createComputer(c);

        computerService.deleteComputer(newId);

        assertNull(computerService.getComputerById(newId));
        assertEquals(numberofComputers,
                computerService.getNumberOfComputers());
    }

    /**
     * Test - Update a computer
     */
    @Test
    public void testUpdate() {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Update").build();
        Long newId = computerService.createComputer(c);
        int numberofComputers = computerService.getNumberOfComputers();
        // Je modifie cet ordinateur
        ComputerDTO c1 = new ComputerDTO.ComputerBuilder("New Computer Update").build();
        computerService.updateComputer(newId, c1);
        assertNotNull(computerService.getComputerById(newId));
        assertEquals(numberofComputers, computerService.getNumberOfComputers());
        // Je supprime cet ordinateur
        computerService.deleteComputer(newId);
    }

    /**
     * Test - getNumberOfComputers
     */
    @Test
    public void testGetNumberOfComputers() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer Test Number").build();
        int nb = computerService.getNumberOfComputers();
        Long newC = computerService.createComputer(c);
        computerService.deleteComputer(newC);
        int newNb = computerService.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    @Test
    public void testGetComputerInRange() throws SQLException, ConfigurationException {
        ArrayList<ComputerDTO> listComputers = computerService.getComputerInRange(1, 10);
        assertEquals(10, listComputers.size());
    }

}
