package main.java.com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;

import main.java.com.excilys.computerdatabase.model.entities.Company;
import main.java.com.excilys.computerdatabase.model.entities.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;

// Extend HttpServlet class
@WebServlet(name = "AddComputerServlet", urlPatterns = { "/add" })
public class AddComputerServlet extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = 6735463320851848975L;

	private CompanyService companyService;
	private List<Company> listeCompanies;

	public void init() throws ServletException {
		try {
			listeCompanies = companyService.getCompaniesList();
		} catch (ConfigurationException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listeCompanies", listeCompanies);
		request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		String introducedDate = request.getParameter("introducedDate");
		String discontinuedDate = request.getParameter("discontinuedDate");
		try {
			listeCompanies = companyService.getCompaniesList();
		} catch (ConfigurationException | SQLException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("listeCompanies", listeCompanies);
		if (Integer.parseInt(request.getParameter("companyId")) == 0 || request.getParameter("companyId") == null) {
			//company = new Company();
		} else {
			//company = new Company(Integer.parseInt(request.getParameter("companyId")));
		}
		if (computerName != null && !computerName.isEmpty() && computerName.matches("^[a-zA-Z ]+$")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate di = null;
			LocalDate dd = null;
			if (introducedDate != null && !introducedDate.isEmpty() && introducedDate.matches("^[0-9]{4}-[0-1][0-9]-[0-3][0-9]$")) {
				try {
					di = LocalDate.parse(introducedDate, formatter);
					if (di.isAfter(LocalDate.now()) || di.getYear() > 2025) {
						System.out.println("Nope");
						request.setAttribute("listeCompanies", listeCompanies);
						request.setAttribute("error", 1);
						request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
					}
				} catch (DateTimeParseException e) {
					System.out.println("Nope");
					request.setAttribute("listeCompanies", listeCompanies);
					request.setAttribute("error", 1);
					request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
				}
			}
			if (discontinuedDate != null && !discontinuedDate.isEmpty() && introducedDate.matches("^[0-9]{4}-[0-1][0-9]-[0-3][0-9]$")) {
				try {
					dd = LocalDate.parse(introducedDate, formatter);
					if (dd.isBefore(di) || dd.getYear() > 2025) {
						System.out.println("Nope");
						request.setAttribute("listeCompanies", listeCompanies);
						request.setAttribute("error", 1);
						request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
					}
				} catch (DateTimeParseException e) {
					System.out.println("Nope");
					request.setAttribute("listeCompanies", listeCompanies);
					request.setAttribute("error", 1);
					request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
				}
			}
			//computer = new Computer.ComputerBuilder().name(request.getParameter("name")).introducedDate(di).discontinuedDate(dd).build();
			//cpS.createComputer(cp);
			request.setAttribute("success", 1);
			request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
		} else {
			System.out.println("Nope");
			request.setAttribute("error", 1);
			request.getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
		}

	}

}