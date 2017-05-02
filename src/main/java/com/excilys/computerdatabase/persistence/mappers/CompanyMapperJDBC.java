package com.excilys.computerdatabase.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Company.CompanyBuilder;

public class CompanyMapperJDBC implements RowMapper<Company>  {

	public Company mapRow(ResultSet rs, int rowNum) {

		CompanyBuilder company = new Company.CompanyBuilder();
		try {
			company.id(rs.getLong("id"));
			company.name(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company.build();
	}
}
