package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.excilys.computerdatabase.model.Company;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyServiceTest {

    //private CompanyService companyService = new CompanyService();

    /**
     * Test - getCompanyList
     */
    @Test
    public void testGetCompanyList() {
        ArrayList<Company> listCompanies = CompanyService.INSTANCE.getCompaniesList();
        int nb = CompanyService.INSTANCE.getNumberOfCompanies();
        assertEquals(nb, listCompanies.size());
    }

    /**
     * Test - getCompanyById
     */
    @Test
    public void testGetCompanyById() {
        ArrayList<Company> listAllCompany = CompanyService.INSTANCE.getCompaniesList();
        Company randomCompany = listAllCompany.get((int) (Math.random() * listAllCompany.size()));
        Long idToTest = randomCompany.getCompanyId();
        Company selectCompany = CompanyService.INSTANCE.getCompanyById(idToTest);
        assert idToTest == selectCompany.getCompanyId();
    }

    /**
     * Test - getNumberOfCompanies
     */
    @Test
    public void testGetNumberOfCompanies() {
        int nb = CompanyService.INSTANCE.getNumberOfCompanies();
        // We assume the number of companies will always be 42 as it can't be
        // edited, inserted or deleted
        assertEquals(42, nb);
    }
}
