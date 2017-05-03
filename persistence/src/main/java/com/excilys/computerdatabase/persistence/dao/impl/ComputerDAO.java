package com.excilys.computerdatabase.persistence.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.model.QCompany;
import com.excilys.computerdatabase.model.QComputer;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;

/**
 * @author Vitalie SOVA
 *
 */
@Repository
public class ComputerDAO {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * @return computerList - The list of computers
     */
    public List<Computer> getComputerList() {
        QComputer qComputer = QComputer.computer;
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        return query.from(qComputer).leftJoin(qComputer.company, qCompany).list(qComputer);
    }

    /**
     * @param id - The id of the selected computer
     * @return computer - The selected computer object
     */
    public Computer getComputerById(Long id) {
        QComputer qComputer = QComputer.computer;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        Computer com = query.from(qComputer).where(qComputer.computerId.eq(id)).uniqueResult(qComputer);
        return com;
    }

    /**
     * @param c - The computer object to create
     * @return
     */
    public Long createComputer(ComputerDTO computer) {
        sessionFactory.getCurrentSession().save(computer);
        LOG.info("The generated key is" + computer.getComputerId());
        return computer.getComputerId();
    }

    /**
     * @param id - The id of the computer to update
     * @param c - The computer object to update with
     */
    public void updateComputer(Long id, ComputerDTO computer) {
        sessionFactory.getCurrentSession().update(computer);
    }

    /**
     * @return count - The number of computers
     */
    public int getNumberOfComputers() {
        QComputer qComputer = QComputer.computer;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        long x = query.from(qComputer).count();
        return (int) x;
    }

    /**
     * @param idBegin - The id of the first computer
     * @param idEnd - The id of the last computer
     * @return listComputer - The list of companies in the selected range
     */
    public List<Computer> getComputerInRange(long idBegin, long idEnd) {
        QComputer qComputer = QComputer.computer;
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        return query.from(qComputer).leftJoin(qComputer.company, qCompany).limit(idEnd).offset(idBegin).list(qComputer);
    }

    public List<Computer> getComputerInRangeNb(long idFirst, int number, Page.SortingBy sort, String search) {
        QComputer qComputer = QComputer.computer;
        QCompany qCompany = QCompany.company;
        JPQLQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        return query.from(qComputer).leftJoin(qComputer.company, qCompany).limit(number).offset(idFirst).list(qComputer);
        /*
        ArrayList<Computer> computerList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);){
            if(sort != null ) {
                String sql = String.format(SQL_SEARCH, sort.toString());
                preparedStatement = connection.prepareStatement(sql);
            } else {
                preparedStatement = connection.prepareStatement(SQL_SEARCH_WITHOUT);
            }
            String searchPattern = search != null ? search + "%" : "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setLong(3, idFirst);
            preparedStatement.setInt(4, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                computerList.add(ResultSetMapper.mapperComputer(resultSet));
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computerList;*/
    }

    /**
     * @param id - The id of the computer to delete
     */
    public void deleteComputer(Long computerId) {
        QComputer qComputer = QComputer.computer;
        new HibernateDeleteClause(sessionFactory.getCurrentSession(), qComputer).where(qComputer.computerId.eq(computerId)).execute();
    }

    public void deleteByCompany(Long companyId) {
        QComputer qComputer = QComputer.computer;
        new HibernateDeleteClause(sessionFactory.getCurrentSession(), qComputer).where(qComputer.company.companyId.eq(companyId)).execute();
    }

    public List<Computer> getAllByCompany(Long id) {
        if (id == null || id < 0) {
            LOG.error("Invalid company id - getAllByCompany)");
        }
        HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
        QComputer computer = QComputer.computer;
        List<Computer> computers = query.from(computer).where(computer.company.companyId.eq(id)).list(computer);
        return computers;
    }
}