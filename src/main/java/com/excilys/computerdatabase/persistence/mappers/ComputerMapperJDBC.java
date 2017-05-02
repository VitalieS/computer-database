package com.excilys.computerdatabase.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;

public class ComputerMapperJDBC implements RowMapper<Computer>  {

	public Computer mapRow(ResultSet resultSet, int rowNum) {
        ComputerBuilder computer = null;
        try {
            computer = new Computer.ComputerBuilder(resultSet.getString("name"));
            computer.id(resultSet.getLong("id"));
            LocalDate introducedDate = (resultSet.getDate("introduced") == null) ? null
                    : resultSet.getDate("introduced").toLocalDate();
            computer.introducedDate(introducedDate);
            LocalDate discontinuedDate = (resultSet.getDate("discontinued") == null) ? null
                    : resultSet.getDate("discontinued").toLocalDate();
            computer.discontinuedDate(discontinuedDate);
            Long companyId = resultSet.getLong("company_id");
            String companyName= resultSet.getString("company_name");
            Company company = null;
            if (companyId != null) {
                company = new Company.CompanyBuilder().id(companyId).name(companyName).build();
            }
            computer.company(company);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computer.build();
	}
}
