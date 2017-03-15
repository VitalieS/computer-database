package com.excilys.computerdatabase.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.excilys.computerdatabase.entity.Computer;
import com.excilys.computerdatabase.persistance.ComputerDAO;

public class ComputerDAOTest {

	@Test
	public void testGetComputerList() throws SQLException {
		int nb = ComputerDAO.getNumberOfComputers();
		ArrayList<Computer> list = ComputerDAO.getComputerList();
		assertEquals(nb, list.size());
	}

	@Test
	public void testGetComputerByIDExistant() throws SQLException {

		Computer c = ComputerDAO.getComputerById(Long.valueOf(10));
		assertNotNull(c);

		// We can test with a random ID between 1 and the number of computers,
		// but the test can fail
		// when it tests a deleted entry.
		/*
		 * int randomID = ThreadLocalRandom.current().nextInt(1,
		 * ComputerDAO.getNumberOfComputers()); Computer c1 =
		 * ComputerDAO.getComputerById(Long.valueOf(randomID));
		 * assertNotNull(c1)
		 */
	}

	@Test
	public void testGetComputerByIDNegative() throws SQLException {
		int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
		Computer c = ComputerDAO.getComputerById(Long.valueOf(randomNegative));
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
		int numberofComputers = ComputerDAO.getNumberOfComputers();

		Long newId = ComputerDAO.createComputer(c);

		assertNotNull(ComputerDAO.getComputerById(newId));
		assertEquals(numberofComputers + 1, ComputerDAO.getNumberOfComputers());
		ComputerDAO.deleteComputer(newId);
	}

	/**
	 * Test - Delete a computer
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testDelete() throws SQLException {
		Computer c = new Computer("New Computer Delete");
		int numberofComputers = ComputerDAO.getNumberOfComputers();
		Long newId = ComputerDAO.createComputer(c);

		ComputerDAO.deleteComputer(newId);

		assertNull(ComputerDAO.getComputerById(newId));
		assertEquals(numberofComputers, ComputerDAO.getNumberOfComputers());
	}

	/**
	 * Test - Delete a computer
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testUpdate() throws SQLException {
		// Je cree un nouveau ordinateur que j'insere dans la base de donnee
		Computer c = new Computer("New Computer Update");
		Long newId = ComputerDAO.createComputer(c);
		int numberofComputers = ComputerDAO.getNumberOfComputers();

		// Je modifie cet ordinateur
		Computer c1 = new Computer("New ComputerUpdate");
		ComputerDAO.updateComputer(newId, c1);

		assertNotNull(ComputerDAO.getComputerById(newId));
		assertEquals(numberofComputers, ComputerDAO.getNumberOfComputers());

		// Je supprime cet ordinateur
		ComputerDAO.deleteComputer(newId);

	}

	/**
	 * Test - getNumberComputers
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetNumberOfComputers() throws SQLException {
		Computer c = new Computer("New Computer Test Number");
		int nb = ComputerDAO.getNumberOfComputers();
		Long newC = ComputerDAO.createComputer(c);
		ComputerDAO.deleteComputer(newC);
		int newNb = ComputerDAO.getNumberOfComputers();
		assertEquals(nb, newNb);
	}

	@Test
	public void testCreateComputerObject() throws SQLException {
		// Bean pattern
		Computer c1 = new Computer("Test Create Bean");
		c1.setId(Long.valueOf(5));
		c1.setManufacturerId(Long.valueOf(8));
		assertNotNull(c1);

		// Telescope pattern
		Computer c2 = new Computer(Long.valueOf(800), "Test Create Telescope Computer", null, null, Long.valueOf(5));
		assertNotNull(c2);

		// Builder pattern
		Computer c3 = new Computer.ComputerBuilder("Test Create Build Computer").id(Long.valueOf(5)).build();
		assertNotNull(c3);
	}
}
