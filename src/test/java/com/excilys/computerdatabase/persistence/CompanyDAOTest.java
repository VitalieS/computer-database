package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dao.impl.CompanyDAO;
import com.excilys.computerdatabase.persistence.dao.impl.ComputerDAO;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyDAOTest {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    private static ComputerDAO computerDAO = (ComputerDAO) context.getBean("computerDAO");
    private static CompanyDAO companyDAO = (CompanyDAO) context.getBean("companyDAO");

    /**
     * Test - getCompanyList
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ConfigurationException
     */
    @Test
    public void testGetCompaniesList() throws SQLException, ClassNotFoundException, ConfigurationException {
        ArrayList<Company> listCompanies = companyDAO.getCompaniesList();
        int nb = companyDAO.getNumberOfCompanies();
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
        ArrayList<Company> listAllCompany = companyDAO.getCompaniesList();
        Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
        Long idToTest = randomCompany.getCompanyId();
        Company selectCompany = companyDAO.getCompanyById(idToTest);
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
        int nb = companyDAO.getNumberOfCompanies();
        // We assume the number of companies will always be 42 as it can't be
        // edited, inserted or deleted
        assertEquals(42, nb);
    }


}
