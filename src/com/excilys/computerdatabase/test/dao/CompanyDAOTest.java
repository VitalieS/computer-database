package com.excilys.computerdatabase.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.excilys.computerdatabase.entity.Company;
import com.excilys.computerdatabase.entity.Computer;
import com.excilys.computerdatabase.persistance.CompanyDAO;
import com.excilys.computerdatabase.persistance.ComputerDAO;

public class CompanyDAOTest {

	CompanyDAO companyDAO;

	@Test
	public void testGetCompanyList() throws SQLException {
		int nb = CompanyDAO.getNumberOfCompanies();
		ArrayList<Company> list = CompanyDAO.getCompaniesList();
		assertEquals(nb, list.size());
	}

	@Test
	public void testGetCompanyByIDExistant() throws SQLException  {
		
		Company c = CompanyDAO.getCompanyById(Long.valueOf(10));
		assertNotNull(c);
		
		// We can test with a random ID between 1 and the number of companies, but the test can fail
		// when it tests a deleted company
		/* int randomID = ThreadLocalRandom.current().nextInt(1, CompanyDAO.getNumberOfCompanies());
		Company c = CompanyDAO.getCompanyById(Long.valueOf(randomID));
		assertNotNull(c1) */
	}
	
	@Test
	public void testGetCompanyByIDNegative() throws SQLException  {
		int randomNegative = ThreadLocalRandom.current().nextInt(-500, -1 + 1);
		Company c = CompanyDAO.getCompanyById(Long.valueOf(randomNegative));
		assertNull(c);
	}
	
	/**
	 * Test - getNumberCompanies
	 * @throws SQLException 
	 */
	@Test
	public void testGetNumberOfComputers() throws SQLException {
		//Company c = new Company(null, "New Company Test Number");
		int nb = CompanyDAO.getNumberOfCompanies();
		//Long newC = ComputerDAO.insertComputer(c);
		//ComputerDAO.deleteComputer(newC);
		//int newNb = ComputerDAO.getNumberOfComputers();
		// We assume the number of companies will always be 42 as it can't be edited, inserted or deleted
		assertEquals(42, nb);
	}
	
	@Test
	public void testCreateCompanyObject() throws SQLException {
		// Bean pattern
		Company c1 = new Company(Long.valueOf(42), "Test Create Bean");
		c1.setId(Long.valueOf(5));
		c1.setName("New Test Bean Create Company");
		assertNotNull(c1);
	}
}
