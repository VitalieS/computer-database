package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.entities.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 2761476641006993989L;

    public static final String VUE = "/views/addComputer.jsp";

    static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("companyList", CompanyService.INSTANCE.getCompaniesList());
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Computer comp = new Computer.ComputerBuilder()
                .name(request.getParameter("computerName"))
                .introducedDate(request.getParameter("introducedDate"))
                .discontinuedDate(request.getParameter("discontinuedDate"))
                .company(Long.valueOf(request.getParameter("companyId"))).build();
        ComputerService.INSTANCE.createComputer(comp);

        request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
