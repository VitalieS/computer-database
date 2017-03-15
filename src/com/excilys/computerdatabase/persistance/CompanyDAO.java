package com.excilys.computerdatabase.persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.computerdatabase.entity.Company;

public class CompanyDAO {

	public CompanyDAO() {
		
	}

	public static ArrayList<Company> getCompaniesList() throws SQLException {
		ConnectionDAO conDB = null;
		try {
			ArrayList<Company> companyList = new ArrayList<Company>();

			conDB = ConnectionDAO.getInstance();
			conDB.executeQuerySelect("SELECT * FROM company");
			ResultSet r = conDB.getResults();

			while (r.next()) {
				Company c = new Company(r.getLong("id"), r.getString("name"));
				companyList.add(c);
			}
			return companyList;
		} finally {
			// conDB.closeConnexion();
		}
	}

	public static Company getCompanyById(Long id) throws SQLException {
		ConnectionDAO conDB = ConnectionDAO.getInstance();

		conDB.executeQuerySelect("SELECT * FROM company WHERE id = " + id);

		ResultSet r = conDB.getResults();
		Company company = null;
		if (r.next()) {
			company = new Company(r.getLong("id"), r.getString("name"));
		}
		return company;
	}
	
	public static int getNumberOfCompanies() throws SQLException {
		ConnectionDAO conDB = ConnectionDAO.getInstance();

		String query = "SELECT COUNT(*) AS nbOfCompanies FROM company";
		
		conDB.executeQuerySelect(query);
		ResultSet r = conDB.getResults();
		int count = 0;
		while(r.next()){
		    count = r.getInt("nbOfCompanies");
		 }
		System.out.println(count);
		return count;
		
	}
	


}