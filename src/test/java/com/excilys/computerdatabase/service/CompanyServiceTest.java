package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.model.Company;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    /**
     * Test - getCompanyList
     */
    @Test
    public void testGetCompanyList() {
        ArrayList<Company> listCompanies = companyService.getCompaniesList();
        int nb = companyService.getNumberOfCompanies();
        assertEquals(nb, listCompanies.size());
    }

    /**
     * Test - getCompanyById
     */
    @Test
    public void testGetCompanyById() {
        ArrayList<Company> listAllCompany = companyService.getCompaniesList();
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
        // We assume the number of companies will always be 42 as it can't be
        // edited, inserted or deleted
        assertEquals(42, nb);
    }
}
