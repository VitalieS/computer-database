package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.persistance.mappers.ServletMapper;

/**
 * Servlet implementation class DeleteComputer.
 */
public class DeleteComputer extends HttpServlet {

    private static final long serialVersionUID = 2939527082585895656L;

    static final Logger LOG = LoggerFactory.getLogger(DeleteComputer.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        ServletMapper.delete(request);
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }
}