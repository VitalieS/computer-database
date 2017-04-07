package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;

import com.excilys.computerdatabase.model.entities.Company;
import com.excilys.computerdatabase.persistance.CompanyDAO;

/**
 * @author Vitalie SOVA
 *
 */
public enum CompanyService {
    INSTANCE;

    private CompanyDAO compDAO = CompanyDAO.CompanyDAO;

    /**
     * @return companyList - An ArrayList of companies
     * @throws SQLException - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public ArrayList<Company> getCompaniesList() /*throws SQLException, ConfigurationException*/ {
        ArrayList<Company> companyList = compDAO.getCompaniesList();
        return companyList;
    }

    /**
     * @param idToSelect - The id of the selected company
     * @return companyById - The selected company object
     * @throws SQLException - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Company getCompanyById(Long idToSelect) /*throws SQLException, ConfigurationException */ {
        Company companyById = compDAO.getCompanyById(idToSelect);
        return companyById;
    }

    /**
     * @return nbOfCompanies - The number of companies
     * @throws SQLException - The SQL Exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public int getNumberOfCompanies() throws SQLException, ConfigurationException {
        int nbOfCompanies = compDAO.getNumberOfCompanies();
        return nbOfCompanies;
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - An ArrayList of all companies
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) throws SQLException, ConfigurationException {
        ArrayList<Company> listCompany = new ArrayList<>();
        compDAO.getCompanyInRange(idBegin, idEnd).forEach(company -> {
            listCompany.add(company);
        });
        return listCompany;
    }
}
