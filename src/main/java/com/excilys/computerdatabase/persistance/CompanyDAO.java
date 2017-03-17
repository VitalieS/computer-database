package main.java.com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.java.com.excilys.computerdatabase.model.Company;

/**
 * @author Vitalie SOVA
 *
 */
public enum CompanyDAO {
    CompanyDAO;

    /**
     * @return companyList - The list of companies
     * @throws SQLException - The SQL exception
     */
    public ArrayList<Company> getCompaniesList() throws SQLException {
        ConnectionDB conDB = null;
        // try {
        ArrayList<Company> companyList = new ArrayList<Company>();

        conDB = ConnectionDB.CONNECTION.getInstance();
        conDB.executeQuerySelect("SELECT * FROM company");
        ResultSet r = conDB.getResults();

        while (r.next()) {
            Company c = new Company(r.getLong("id"), r.getString("name"));
            companyList.add(c);
        }
        return companyList;
        /*
         * } finally { // ConnectionDB.CONNECTION.closeConnexion(); }
         */
    }

    /**
     * @param choiceId - The id of the selected company
     * @return company - The selected company object
     * @throws SQLException - The SQL exception
     */
    public Company getCompanyById(Long choiceId) throws SQLException {
        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();

        conDB.executeQuerySelect("SELECT * FROM company WHERE id = " + choiceId);

        ResultSet r = conDB.getResults();
        Company company = null;
        if (r.next()) {
            company = new Company(r.getLong("id"), r.getString("name"));
        }
        return company;
    }

    /**
     * @return count - The number of companies
     * @throws SQLException - The SQL exception
     */
    public int getNumberOfCompanies() throws SQLException {
        ConnectionDB conDB = ConnectionDB.CONNECTION.getInstance();

        String query = "SELECT COUNT(*) AS nbOfCompanies FROM company";

        conDB.executeQuerySelect(query);
        ResultSet r = conDB.getResults();
        int count = 0;
        while (r.next()) {
            count = r.getInt("nbOfCompanies");
        }
        System.out.println(count);
        return count;
    }

    /**
     * @param idBegin
     *            - The id of the first company
     * @param idEnd
     *            - The id of the last company
     * @return listCompany - The list of companies in the selected range
     */
    public ArrayList<Company> getCompanyInRange(long idBegin, long idEnd) {
        String query = "select * from company limit ?,?";
        ArrayList<Company> listCompany = new ArrayList<>();
        try (Connection conn = ConnectionDB.CONNECTION.getConnection();
                PreparedStatement selectPStatement = conn
                        .prepareStatement(query);) {
            selectPStatement.setLong(1, idBegin);
            selectPStatement.setLong(2, idEnd);
            try (ResultSet rs = selectPStatement.executeQuery()) {
                while (rs.next()) {
                    listCompany.add(new Company(rs.getLong("id"),
                            rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCompany;
    }

}