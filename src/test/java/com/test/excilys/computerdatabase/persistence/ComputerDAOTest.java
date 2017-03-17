package test.java.com.test.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.ComputerService;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerDAOTest {

    private ComputerService computerService = new ComputerService();

    /**
     * Test - getComputerList
     *
     * @throws SQLException
     */
    @Test
    public void testGetComputerList() throws SQLException {
        int nb = computerService.getNumberOfComputers();
        ArrayList<Computer> list = computerService.getComputerList();
        assertEquals(nb, list.size());
    }

    /**
     * Test - getComputerById when the Id exists
     *
     * @throws SQLException
     */
    @Test
    public void testGetComputerByIDExistant() throws SQLException {
        Computer c = computerService.getComputerById(Long.valueOf(10));
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
     */
    @Test
    public void testGetComputerByIDNegative() throws SQLException {
        int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
        Computer c = computerService
                .getComputerById(Long.valueOf(randomNegative));
        assertNull(c);
    }

    /**
     * Test - Crate a new computer
     *
     * @throws SQLException
     */
    @Test
    public void testCreate() throws SQLException {
        Computer c = new Computer("New computer Insert");
        int numberofComputers = computerService.getNumberOfComputers();

        Long newId = computerService.createComputer(c);

        assertNotNull(computerService.getComputerById(newId));
        assertEquals(numberofComputers + 1,
                computerService.getNumberOfComputers());
        computerService.deleteComputer(newId);
    }

    /**
     * Test - Delete a computer
     *
     * @throws SQLException
     */
    @Test
    public void testDelete() throws SQLException {
        Computer c = new Computer("New Computer Delete");
        int numberofComputers = computerService.getNumberOfComputers();
        Long newId = computerService.createComputer(c);

        computerService.deleteComputer(newId);

        assertNull(computerService.getComputerById(newId));
        assertEquals(numberofComputers, computerService.getNumberOfComputers());
    }

    /**
     * Test - Update a computer
     *
     * @throws SQLException
     */
    @Test
    public void testUpdate() throws SQLException {
        // Je cree un nouveau ordinateur que j'insere dans la base de donnee
        Computer c = new Computer("New Computer Update");
        Long newId = computerService.createComputer(c);
        int numberofComputers = computerService.getNumberOfComputers();

        // Je modifie cet ordinateur
        Computer c1 = new Computer("New ComputerUpdate");
        computerService.updateComputer(newId, c1);

        assertNotNull(computerService.getComputerById(newId));
        assertEquals(numberofComputers, computerService.getNumberOfComputers());

        // Je supprime cet ordinateur
        computerService.deleteComputer(newId);

    }

    /**
     * Test - getNumberOfComputers
     *
     * @throws SQLException
     */
    @Test
    public void testGetNumberOfComputers() throws SQLException {
        Computer c = new Computer("New Computer Test Number");
        int nb = computerService.getNumberOfComputers();
        Long newC = computerService.createComputer(c);
        computerService.deleteComputer(newC);
        int newNb = computerService.getNumberOfComputers();
        assertEquals(nb, newNb);
    }

    /**
     * Test - Creation of Computer Objects
     *
     * @throws SQLException
     */
    @Test
    public void testCreateComputerObject() {
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

}
