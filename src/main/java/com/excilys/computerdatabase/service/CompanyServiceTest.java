package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistance.dao.impl.CompanyDAOTest;

/**
 * @author Vitalie SOVA
 *
 */
public enum CompanyServiceTest {
    INSTANCE;

    private CompanyDAOTest companyDAO = CompanyDAOTest.CompanyDAO;

    /**
     * @return companyList - An ArrayList of companies
     */
    public ArrayList<Company> getCompaniesList() {
        ArrayList<Company> companyList = companyDAO.getCompaniesList();
        return companyList;
    }

    /**
     * @param idToSelect - The id of the selected company
     * @return companyById - The selected company object
     */
    public Company getCompanyById(Long idToSelect) {
        Company companyById = companyDAO.getCompanyById(idToSelect);
        return companyById;
    }

    /**
     * @return nbOfCompanies - The number of companies
     */
    public int getNumberOfCompanies() {
        return companyDAO.getNumberOfCompanies();
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - An ArrayList of all companies
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        ArrayList<Company> listCompany = new ArrayList<>();
        companyDAO.getCompanyInRange(idBegin, idEnd).forEach(company -> {
            listCompany.add(company);
        });
        return listCompany;
    }
}
