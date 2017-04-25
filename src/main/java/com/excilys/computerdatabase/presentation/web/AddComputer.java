package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ServletMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.Validator;

/**
 * Servlet implementation class AddComputer.
 */
public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 2761476641006993989L;

    public static final String VUE = "/WEB-INF/views/addComputer.jsp";

    static final Logger LOG = LoggerFactory.getLogger(AddComputer.class);

    public ComputerService computerService;
    public CompanyService companyService;

    @Override
    public void init() {
        WebApplicationContext contextApp = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.computerService = (ComputerService)contextApp.getBean("computerService");
        this.companyService = (CompanyService)contextApp.getBean("companyService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        getAdd(request);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        ComputerDTO computer = ServletMapper.mapperToDTO(request, getAdd(request));
        Map<String, String> errors = Validator.validate(computer);
        if (!errors.isEmpty()) {
            LOG.debug("Errors found while adding the computer.");
            request.setAttribute("exception", errors.toString());
            doGet(request, response);
            return;
        }
        computerService.createComputer(computer);
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }

    public ArrayList<Company> getAdd(HttpServletRequest request) {
        ArrayList<Company> companyList = companyService.getCompaniesList();
        request.setAttribute("companyList", companyList);
        return companyList;
    }

}