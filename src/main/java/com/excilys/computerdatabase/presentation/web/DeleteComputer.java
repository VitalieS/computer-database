package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer.
 */
public class DeleteComputer extends HttpServlet {

    private static final long serialVersionUID = 2939527082585895656L;

    static final Logger LOG = LoggerFactory.getLogger(DeleteComputer.class);

    @Autowired
    public ComputerService computerService;

    @Override
    public void init() {
        WebApplicationContext contextApp = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.computerService = (ComputerService) contextApp.getBean("computerService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        delete(request);
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }

    public void delete(HttpServletRequest request) {
        if (request.getParameter("selection") != null) {
            String toDeleteSelection = request.getParameter("selection");
            String[] toDeleteSelectionIds = toDeleteSelection.split(",");
            for (String toDeleteSelectionId : toDeleteSelectionIds) {
                computerService.deleteComputer(Long.parseLong(toDeleteSelectionId));
            }
        }
    }
}