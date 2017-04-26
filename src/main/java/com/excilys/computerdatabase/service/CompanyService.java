package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dao.impl.CompanyDAO;
import com.excilys.computerdatabase.persistence.dao.impl.ComputerDAO;

/**
 * @author Vitalie SOVA
 */
@Service/*("companyService")*/
public class CompanyService {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
  	private CompanyDAO companyDAO;

    public CompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    /**
     * @return companyList - An ArrayList of companies
     */
    public ArrayList<Company> getCompaniesList() {
        //ArrayList<Company> companyList = new ArrayList<Company>();
        //companyList = companyDAO.getCompaniesList();
        return companyDAO.getCompaniesList();
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
