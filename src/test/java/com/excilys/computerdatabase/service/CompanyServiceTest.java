package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import com.excilys.computerdatabase.model.entities.Company;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyServiceTest {

    //private CompanyService companyService = new CompanyService();

    /**
     * Test - getCompanyList
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetCompanyList() throws SQLException, ClassNotFoundException, ConfigurationException {
        ArrayList<Company> listCompanies = CompanyService.INSTANCE.getCompaniesList();
        int nb = CompanyService.INSTANCE.getNumberOfCompanies();
        assertEquals(nb, listCompanies.size());
    }

    /**
     * Test - getCompanyById
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetCompanyById() throws SQLException, ClassNotFoundException, ConfigurationException {
        ArrayList<Company> listAllCompany = CompanyService.INSTANCE.getCompaniesList();
        Company randomCompany = listAllCompany
                .get((int) (Math.random() * listAllCompany.size()));
        Long idToTest = randomCompany.getCompanyId();
        Company selectCompany = CompanyService.INSTANCE.getCompanyById(idToTest);
        assert idToTest == selectCompany.getCompanyId();
    }

    /**
     * Test - getNumberOfCompanies
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetNumberOfCompanies() throws SQLException, ClassNotFoundException, ConfigurationException {
        int nb = CompanyService.INSTANCE.getNumberOfCompanies();
        // We assume the number of companies will always be 42 as it can't be
        // edited, inserted or deleted
        assertEquals(42, nb);
    }

    /**
     * Test - Creation of Company Objects
     *
     * @throws SQLException
     */
    @Test
    public void testCreateCompanyObject() throws SQLException {
        // Bean pattern
        Company c1 = new Company(Long.valueOf(42), "Test Create Bean");
        c1.setCompanyId(Long.valueOf(5));
        c1.setCompanyName("New Test Bean Create Company");
        assertNotNull(c1);
    }

}
