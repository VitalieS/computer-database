package com.excilys.computerdatabase.persistence.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.QCompany;
import com.excilys.computerdatabase.model.QComputer;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;

/**
 * @author Vitalie SOVA
 *
 */
@Repository
public class CompanyDAO {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //private JdbcTemplate jdbcTemplate;

    // private final static CompanyDAO COMPANY_DAO_INSTANCE;
    private final static Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);

    // TODO Implement company creation
    // private final static String SQL_CREATE = "INSERT INTO company (name) VALUES ('?')";
    private final static String SQL_GET_NUMBER_COMPANIES = "SELECT COUNT(*) FROM company";

    /* static {
        COMPANY_DAO_INSTANCE = new CompanyDAO();
        LOG = LoggerFactory.getLogger(CompanyDAO.class);
    }

    public static CompanyDAO getInstance() {
        return COMPANY_DAO_INSTANCE;
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }*/

    /**
     * @return companyList - The list of companies
     */
    public List<Company> getCompaniesList() {
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        return query.from(qCompany).list(qCompany);

    }

    /**
     * @param choiceId - The id of the selected company
     * @return company - The selected company object
     */
    public Company getCompanyById(Long id) {
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        return query.from(qCompany).where(qCompany.companyId.eq(id)).uniqueResult(qCompany);
    }

    /**
     * delete - Deletes a Company
     * @param id - The id of the Company to delete
     */
    public void delete(long id) {
        QCompany qCompany = QCompany.company;
        new HibernateDeleteClause(sessionFactory.getCurrentSession(), qCompany).where(qCompany.companyId.eq(id)).execute();
    }

    /**
     * @param idBegin - The id of the first company
     * @param idEnd - The id of the last company
     * @return listCompany - The list of companies in the selected range
     */
    public List<Company> getCompanyInRange(long idBegin, long idEnd) {
        QComputer qComputer = QComputer.computer;
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        //return query.from(qComputer).leftJoin(qComputer.company, qCompany).limit(idEnd).offset(idBegin).list(qComputer);
        return query.from(qCompany)/*.leftJoin(qComputer.company, qCompany)*/.limit(idEnd).offset(idBegin).list(qCompany);
    }

    /**
     * @return count - The number of companies
     */
    public int getNumberOfCompanies() {
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        long x = query.from(qCompany).count();
        return (int) x;
    }
}