package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.entities.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.Validate;

public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 2761476641006993989L;

    public static final String VUE = "/views/addComputer.jsp";

    static final Logger LOG = LoggerFactory.getLogger(AddComputer.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        request.setAttribute("companyList", CompanyService.INSTANCE.getCompaniesList());
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        String computerName = request.getParameter("computerName");
        String introducedDate = request.getParameter("introducedDate");
        String discontinuedDate = request.getParameter("discontinuedDate");
        String companyId = request.getParameter("companyId");

        LOG.info("Trying to add: " + computerName + "" + introducedDate + "" + discontinuedDate + "" + companyId);

        try {
            Validate.INSTANCE.checkName(computerName);
            Validate.INSTANCE.checkDate(introducedDate);
            Validate.INSTANCE.checkDate(discontinuedDate);
            Validate.INSTANCE.checkDateNotBeforeDate(introducedDate, discontinuedDate);
            Validate.INSTANCE.checkId(companyId);

            //DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Computer comp = new Computer.ComputerBuilder(computerName).build();

            if (introducedDate != null && !introducedDate.trim().isEmpty()) {
                //LocalDate introducedDateLD = LocalDate.parse(introducedDate, form);
                LocalDate introducedDateLD = LocalDate.parse(introducedDate);
                comp.setIntroducedDate(introducedDateLD);
            }
            if (discontinuedDate != null && !discontinuedDate.trim().isEmpty()) {
                //LocalDate discontinuedDateLD = LocalDate.parse(discontinuedDate, form);
                LocalDate discontinuedDateLD = LocalDate.parse(discontinuedDate);
                comp.setDiscontinuedDate(discontinuedDateLD);
            }
            if (companyId != null && !companyId.trim().isEmpty()) {
                comp.setCompanyId(Long.valueOf(companyId));
            }

            System.out.println(comp);
            ComputerService.INSTANCE.createComputer(comp);
            response.sendRedirect(getServletContext().getContextPath() + "/dashboard");

        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", e.getMessage());
            doGet(request, response);
            //request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        }
    }
}