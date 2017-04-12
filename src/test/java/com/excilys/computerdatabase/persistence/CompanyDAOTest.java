package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import com.excilys.computerdatabase.model.entities.Company;
import com.excilys.computerdatabase.persistance.CompanyDAO;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyDAOTest {

    /**
     * Test - getCompanyList
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetCompaniesList() throws SQLException, ClassNotFoundException, ConfigurationException {
        ArrayList<Company> listCompanies = CompanyDAO.CompanyDAO.getCompaniesList();
        int nb = CompanyDAO.CompanyDAO.getNumberOfCompanies();
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
        ArrayList<Company> listAllCompany = CompanyDAO.CompanyDAO.getCompaniesList();
        Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
        Long idToTest = randomCompany.getCompanyId();
        Company selectCompany = CompanyDAO.CompanyDAO.getCompanyById(idToTest);
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
        int nb = CompanyDAO.CompanyDAO.getNumberOfCompanies();
        // We assume the number of companies will always be 42 as it can't be
        // edited, inserted or deleted
        assertEquals(42, nb);
    }


}
