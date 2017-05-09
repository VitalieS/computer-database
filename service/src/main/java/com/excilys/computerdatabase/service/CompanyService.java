package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.dao.impl.CompanyDAO;
import com.excilys.computerdatabase.persistence.dao.impl.ComputerDAO;

/**
 * @author Vitalie SOVA
 */
@Service
public class CompanyService {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private ComputerDAO computerDAO;
    
    @Autowired
    private CompanyDAO companyDAO;

    /**
     * @return companyList - An ArrayList of companies
     */
    @Transactional(readOnly=true)
    public List<Company> getCompaniesList() {
        return companyDAO.getCompaniesList();
    }

    /**
     * @param idToSelect - The id of the selected company
     * @return companyById - The selected company object
     */
    @Transactional(readOnly=true)
    public Company getCompanyById(Long idToSelect) {
        Company companyById = companyDAO.getCompanyById(idToSelect);
        return companyById;
    }

    /**
     * @return nbOfCompanies - The number of companies
     */
    @Transactional(readOnly=true)
    public int getNumberOfCompanies() {
        return companyDAO.getNumberOfCompanies();
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - An ArrayList of all companies
     */
    @Transactional(readOnly=true)
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        ArrayList<Company> listCompany = new ArrayList<>();
        companyDAO.getCompanyInRange(idBegin, idEnd).forEach(company -> {
            listCompany.add(company);
        });
        return listCompany;
    }

    /**
     * delete a Company and all related Computers. rollback if there is any problem in the transaction
     *
     * @param id - id of the Company to delete
     * @throws SQLException
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Long id) {
    	List<Computer> computers = computerDAO.getAllByCompany(id);
		for (Computer computer : computers) {
			computerDAO.deleteByCompany(computer.getComputerId());
		}
		companyDAO.delete(id);
    }
}
