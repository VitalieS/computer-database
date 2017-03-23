package main.java.com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.sql.SQLException;

// Import required java libraries

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;

import main.java.com.excilys.computerdatabase.model.entities.Computer;
import main.java.com.excilys.computerdatabase.model.entities.Page;
import main.java.com.excilys.computerdatabase.service.ComputerService;

@WebServlet(name = "DashboardServlet", urlPatterns = { "/dashboard" })
public class DashboardServlet extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = 458516061330357823L;

	private ComputerService computerService;
	private List<Computer> listeComputers;
	private int numPage;
	private int indexPage;
	private int nbComputers;

	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			nbComputers = computerService.getNumberOfComputers();
			indexPage = Page.getIndex();
			listeComputers = ComputerService.INSTANCE.getComputerList();
		} catch (ConfigurationException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("numComputers", nbComputers);
		request.setAttribute("index", indexPage);
		request.setAttribute("listeComputers", listeComputers);
		request.setAttribute("numPage", numPage);
		request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action") != null) {
			if (request.getParameter("action").equals("next")) {
				Page.next();
			}
			if (request.getParameter("action").equals("page")) {
				try {
					numPage = ComputerService.INSTANCE.pageNumber();
				} catch (ConfigurationException | SQLException e) {
					e.printStackTrace();
				}
				if (Integer.parseInt(request.getParameter("num")) < 0
						|| Integer.parseInt(request.getParameter("num")) > numPage) {
					System.out.println("tentative");
				} else {
					Page.setIndex(Integer.parseInt(request.getParameter("num")));
				}
			}
			if (request.getParameter("action").equals("previous")) {
				Page.previous();
			}
			if (request.getParameter("action").equals("change_max_obj")) {
				if (Integer.parseInt(request.getParameter("num")) != 10
						&& Integer.parseInt(request.getParameter("num")) != 50
						&& Integer.parseInt(request.getParameter("num")) != 100) {
					System.out.println("tentative");

				} else {
					Page.setNumberPerPage(Integer.parseInt(request.getParameter("num")));
					try {
						numPage = ComputerService.INSTANCE.pageNumber();
					} catch (ConfigurationException | SQLException e) {
						e.printStackTrace();
					}
					Page.setIndex(0);
				}

			}
		}

		try {
			listeComputers = ComputerService.INSTANCE.getComputerList();
			indexPage = Page.getIndex();
			nbComputers = ComputerService.INSTANCE.getNumberOfComputers();
		} catch (ConfigurationException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("numComputers", nbComputers);
		request.setAttribute("index", indexPage);
		request.setAttribute("listeComputers", listeComputers);
		request.setAttribute("numPage", numPage);
		request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
	}

}