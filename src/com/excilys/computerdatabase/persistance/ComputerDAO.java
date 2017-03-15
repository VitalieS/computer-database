package com.excilys.computerdatabase.persistance;

import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.excilys.computerdatabase.entity.Computer;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StatementImpl;

public class ComputerDAO {

	public static ArrayList<Computer> getComputerList() throws SQLException {
		ConnectionDAO conDB = null;
		try {
			ArrayList<Computer> computerList = new ArrayList<Computer>();

			conDB = ConnectionDAO.getInstance();
			conDB.executeQuerySelect("SELECT * FROM computer");
			ResultSet r = conDB.getResults();

			while (r.next()) {

				Computer c = new Computer.ComputerBuilder(r.getString("name")).id(r.getLong("id"))
						.company(r.getLong("company_id")).build();
				if (r.getString("introduced") != null) {
					c.setIntroducedDate(r.getDate("introduced").toLocalDate());
				}
				if (r.getString("discontinued") != null) {
					c.setDiscontinuedDate(r.getDate("discontinued").toLocalDate());

				}
				computerList.add(c);
			}
			return computerList;
		} finally {
			// conDB.closeConnexion();
		}
	}

	public static Computer getComputerById(Long choiceId) throws SQLException {
		ConnectionDAO conDB = ConnectionDAO.getInstance();

		conDB.executeQuerySelect("SELECT * FROM computer WHERE id=" + choiceId);

		Computer computer = null;

		ResultSet r = conDB.getResults();
		
		if (r.next()) {
			computer = new Computer.ComputerBuilder(r.getString("name")).id(r.getLong("id"))
					.company(r.getLong("company_id")).build();
			if (r.getString("introduced") != null) {
				computer.setIntroducedDate(r.getDate("introduced").toLocalDate());
			}
			if (r.getString("discontinued") != null) {
				computer.setDiscontinuedDate(r.getDate("discontinued").toLocalDate());
			}
		}
		return computer;
		
	}

	public static Long createComputer(Computer c) throws SQLException {

	
			
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(";
		
		if (c.getComputerName() == null) {
			query += null + ", ";
		} else {
			query += "'" + c.getComputerName() + "', ";
		}
		if (c.getIntroducedDate() == null) {
			query += null + ", ";
		} else {
			query += "'" + c.getIntroducedDate() + "', ";
		}
		if (c.getDiscontinuedDate() == null) {
			query += null + ", ";
		} else {
			query += "'" + c.getDiscontinuedDate() + "', ";
		}
		if (c.getManufacturerId() == null) {
			query += null + ")";
		} else {
			query += c.getManufacturerId() + ")";
		}

		System.out.println(query);
		ConnectionDAO conDB = ConnectionDAO.getInstance();
		conDB.executeQueryDataManipulation(query);

		Long generatedkey = null;
		ResultSet rs = conDB.getStatement().getGeneratedKeys();
		if (rs.next()) {
			generatedkey = rs.getLong(1);
			System.out.println("Auto Generated Primary Key " + generatedkey);
		}
		return generatedkey;
	}

	public static void updateComputer(Long id, Computer c) throws SQLException {

		String query = "UPDATE computer SET name = '" + c.getComputerName() + "', introduced = ";

		/*
		 * if ((Long) c.getComputerId() == null) { query += null +
		 * ", discontinued = "; } else { query += "'" + (Long) c.getComputerId()
		 * + "', introduced = "; }
		 */

		if (c.getIntroducedDate() == null) {
			query += null + ", discontinued = ";
		} else {
			query += "'" + c.getIntroducedDate() + "', discontinued = ";
		}
		if (c.getDiscontinuedDate() == null) {
			query += null + ", company_id = ";
		} else {
			query += "'" + c.getDiscontinuedDate() + "', company_id = ";
		}
		if ((Long) c.getManufacturerId() == null) {
			query += null + " WHERE id = " + id;
		} else {
			query += c.getManufacturerId() + " WHERE id = " + id;
		}

		System.out.println(query);
		ConnectionDAO conDB = ConnectionDAO.getInstance();
		conDB.executeQueryDataManipulation(query);
	}

	public static void deleteComputer(Long id) throws SQLException {

		String query = "DELETE FROM computer WHERE id = " + id;
		System.out.println(query);
		ConnectionDAO conDB = ConnectionDAO.getInstance();
		conDB.executeQueryDataManipulation(query);
	}

	public static int getNumberOfComputers() throws SQLException {
		ConnectionDAO conDB = ConnectionDAO.getInstance();

		String query = "SELECT COUNT(*) AS nbofcomputers FROM computer";

		conDB.executeQuerySelect(query);
		ResultSet r = conDB.getResults();
		int count = 0;
		while (r.next()) {
			count = r.getInt("nbofComputers");
		}
		System.out.println(count);
		return count;

	}

}
