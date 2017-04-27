package com.excilys.computerdatabase.persistence;

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
public class ComputerDAOTest {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    private static ComputerDAO computerDAO = (ComputerDAO) context.getBean("computerDAO");
    private static CompanyDAO companyDAO = (CompanyDAO) context.getBean("companyDAO");

    /**
     * Test - getComputerList
     */
    @Test
    public void testGetComputerList() {
        int nb = computerDAO.getNumberOfComputers();
        List<Computer> list = computerDAO.getComputerList();
        assertEquals(nb, list.size());
    }

    /**
     * Test - getComputerById when the Id exists
     */
    @Test
    public void testGetComputerByIDExistant() {
        Computer c = computerDAO.getComputerById(Long.valueOf(50));
        assertNotNull(c);

    }

    /**
     * Test - getComputerById when the Id is negative
     */
    @Test
    public void testGetComputerByIDNegative() {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = computerDAO.getComputerById(Long.valueOf(randomNegative));
        assertNull(c);
    }

    /**
     * Test - Crate a new computer
     */
    @Test
    public void testCreateComputer() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer Insert").build();
        int numberofComputers = computerDAO.getNumberOfComputers();
        Long newId = computerDAO.createComputer(c);
        assertNotNull(computerDAO.getComputerById(newId));
        assertEquals(numberofComputers + 1, computerDAO.getNumberOfComputers());
        computerDAO.deleteComputer(newId);
    }

    /**
     * Test - Update a computer
     */
    @Test
    public void testUpdateComputer() {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer Update").build();;
        Long newId = computerDAO.createComputer(c);
        int numberofComputers = computerDAO.getNumberOfComputers();
        // Je modifie cet ordinateur
        ComputerDTO c1 = new ComputerDTO.ComputerBuilder("New computer update").build();;
        computerDAO.updateComputer(newId, c1);
        assertNotNull(computerDAO.getComputerById(newId));
        assertEquals(numberofComputers,
                computerDAO.getNumberOfComputers());
        // Je supprime cet ordinateur
        computerDAO.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     */
    @Test
    public void testDeleteComputer()
            throws SQLException, ClassNotFoundException, ConfigurationException {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer delete").build();;
        int numberofComputers = computerDAO.getNumberOfComputers();

        Long newId = computerDAO.createComputer(c);

        computerDAO.deleteComputer(newId);

        assertNull(computerDAO.getComputerById(newId));
        assertEquals(numberofComputers,
                computerDAO.getNumberOfComputers());
    }

    /**
     * Test - getNumberOfComputers
     */
    @Test
    public void testGetNumberOfComputers() {
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New computer test number").build();;
        int nb = computerDAO.getNumberOfComputers();
        Long newC = computerDAO.createComputer(c);
        computerDAO.deleteComputer(newC);
        int newNb = computerDAO.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    @Test
    public void testgetComputerInRange() {
        ArrayList<Computer> listComputers = computerDAO.getComputerInRange(1, 10);
        assertEquals(10, listComputers.size());
    }

}
