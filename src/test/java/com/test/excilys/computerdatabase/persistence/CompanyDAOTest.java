package test.java.com.test.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.service.CompanyService;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyDAOTest {

    private CompanyService companyService = new CompanyService();

    /**
     * Test - getCompanyList
     *
     * @throws SQLException
     */
    @Test
    public void testGetCompanyList() throws SQLException {
        ArrayList<Company> listCompanies = companyService.getCompaniesList();
        int nb = companyService.getNumberOfCompanies();
        assertEquals(nb, listCompanies.size());
    }

    /**
     * Test - getCompanyById
     *
     * @throws SQLException
     */
    @Test
    public void testGetCompanyById() throws SQLException {
        ArrayList<Company> listAllCompany = companyService.getCompaniesList();
        Company randomCompany = listAllCompany
                .get((int) (Math.random() * listAllCompany.size()));
        Long idToTest = randomCompany.getId();
        Company selectCompany = companyService.getCompanyById(idToTest);
        assert idToTest == selectCompany.getId();
    }

    /**
     * Test - getNumberOfCompanies
     *
     * @throws SQLException
     */
    @Test
    public void testGetNumberOfCompanies() throws SQLException {
        int nb = companyService.getNumberOfCompanies();
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
        c1.setId(Long.valueOf(5));
        c1.setName("New Test Bean Create Company");
        assertNotNull(c1);
    }

}
