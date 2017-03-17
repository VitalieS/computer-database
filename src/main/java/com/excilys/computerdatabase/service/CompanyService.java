package main.java.com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.persistance.CompanyDAO;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyService {

    private CompanyDAO compDAO = CompanyDAO.CompanyDAO;

    /**
     * @return companyList - An ArrayList of companies
     * @throws SQLException - The SQL Exception
     */
    public ArrayList<Company> getCompaniesList() throws SQLException {
        ArrayList<Company> companyList = compDAO.getCompaniesList();
        return companyList;
    }

    /**
     * @param idToSelect - The id of the selected company
     * @return companyById - The selected company object
     * @throws SQLException - The SQL Exception
     */
    public Company getCompanyById(Long idToSelect) throws SQLException {
        Company companyById = compDAO.getCompanyById(idToSelect);
        return companyById;
    }

    /**
     * @return nbOfCompanies - The number of companies
     * @throws SQLException - The SQL Exception
     */
    public int getNumberOfCompanies() throws SQLException {
        int nbOfCompanies = compDAO.getNumberOfCompanies();
        return nbOfCompanies;
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - An ArrayList of all companies
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        ArrayList<Company> listCompany = new ArrayList<>();
        compDAO.getCompanyInRange(idBegin, idEnd).forEach(company -> {
            listCompany.add(company);
        });
        return listCompany;
    }
}
