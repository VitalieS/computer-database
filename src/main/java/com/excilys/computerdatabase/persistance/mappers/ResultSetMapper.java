package com.excilys.computerdatabase.persistance.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Company.CompanyBuilder;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.persistance.dao.impl.CompanyDAO;

public enum ResultSetMapper {
    INSTANCE;

    public Company mapperCompany(ResultSet rs) {
        CompanyBuilder company = new Company.CompanyBuilder();
        try {
            company.id(rs.getLong("id"));
            company.name(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company.build();
    }

    public Computer mapperComputer(ResultSet resultSet) {
        ComputerBuilder computer = null;
        try {
            computer = new Computer.ComputerBuilder(resultSet.getString("name"));
            computer.id(resultSet.getLong("id"));
            LocalDate introducedDate = (resultSet.getDate("introduced") == null) ? null : resultSet.getDate("introduced").toLocalDate();
            computer.introducedDate(introducedDate);
            LocalDate discontinuedDate = (resultSet.getDate("discontinued") == null) ? null : resultSet.getDate("discontinued").toLocalDate();
            computer.discontinuedDate(discontinuedDate);
            Long companyId = resultSet.getLong("company_id");
            Company company = null;
            if (companyId != null) {
                company = CompanyDAO.CompanyDAO.getCompanyById(companyId);
            }
            computer.company(company);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computer.build();
    }
}
