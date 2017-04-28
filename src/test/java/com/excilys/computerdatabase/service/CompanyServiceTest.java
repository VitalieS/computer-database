package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computerdatabase.model.Company;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyServiceTest {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    private static CompanyService companyService = (CompanyService) context.getBean("companyService");

    /**
     * Test - getCompanyList
     */
    @Test
    public void testGetCompanyList() {
        List<Company> listCompanies = companyService.getCompaniesList();
        int nb = companyService.getNumberOfCompanies();
        assertEquals(nb, listCompanies.size());
    }

    /**
     * Test - getCompanyById
     */
    @Test
    public void testGetCompanyById() {
        List<Company> listAllCompany = companyService.getCompaniesList();
        Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
        Long idToTest = randomCompany.getCompanyId();
        Company selectCompany = companyService.getCompanyById(idToTest);
        assert idToTest == selectCompany.getCompanyId();
    }

    /**
     * Test - getNumberOfCompanies
     */
    @Test
    public void testGetNumberOfCompanies() {
        int nb = companyService.getNumberOfCompanies();
        // We assume the number of companies will always be 41 as it can't be
        // edited, inserted or deleted
        assertEquals(41, nb);
    }
}
