package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.persistance.mappers.ServletMapper;
import com.excilys.computerdatabase.service.ComputerServiceTest;
import com.excilys.computerdatabase.service.Validator;

/**
 * Servlet implementation class AddComputer.
 */
public class AddComputer extends HttpServlet {

    private static final long serialVersionUID = 2761476641006993989L;

    public static final String VUE = "/WEB-INF/views/addComputer.jsp";

    static final Logger LOG = LoggerFactory.getLogger(AddComputer.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        ServletMapper.getAdd(request);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        ComputerDTO computer = ServletMapper.mapperToDTO(request, ServletMapper.getAdd(request));
        Map<String, String> errors = Validator.validate(computer);
        if (!errors.isEmpty()) {
            LOG.debug("There are errors, launched addcomputer view again with errors.");
            request.setAttribute("exception", errors.toString());
            doGet(request, response);
            return;
        }
        ComputerServiceTest.INSTANCE.createComputer(computer);
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }
}