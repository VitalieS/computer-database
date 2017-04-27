package com.excilys.computerdatabase.presentation.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ComputerMapper;
import com.excilys.computerdatabase.persistence.mappers.ServletMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.Validator;

/**
 * Servlet implementation class EditComputer.
 */
public class EditComputer extends HttpServlet {

    private static final long serialVersionUID = 5322583920825901807L;

    public static final String VUE = "/WEB-INF/views/editComputer.jsp";

    static final Logger LOG = LoggerFactory.getLogger(EditComputer.class);

    @Autowired
    public ComputerService computerService;

    @Autowired
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
        getEdit(request);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        ComputerDTO computer = ServletMapper.mapperToDTO(request, getEdit(request));
        Map<String, String> errors = Validator.validate(computer);
        if (!errors.isEmpty()) {
            LOG.debug("There are errors, launched viewcomputer view again with errors.");
            request.setAttribute("exception", errors.toString());
            doGet(request, response);
            return;
        }
        computerService.updateComputer(computer.getComputerId(), computer);
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }

    public List<Company> getEdit(HttpServletRequest request) {
        ComputerDTO computerToEdit = ComputerMapper.mapper(computerService.getComputerById(Long.valueOf(request.getParameter("computerId"))));
        List<Company> companyList = companyService.getCompaniesList();
        Company companyOfTheEditedComputer = companyService.getCompanyById(computerToEdit.getCompanyId());
        request.setAttribute("computerToEdit", computerToEdit);
        request.setAttribute("companyOfTheEditedComputer", companyOfTheEditedComputer);
        request.setAttribute("companyList", companyList);
        return companyList;
    }
}