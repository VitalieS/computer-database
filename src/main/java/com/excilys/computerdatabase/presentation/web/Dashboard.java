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
 * Servlet implementation class Dashboard.
 */
public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = -7393279402298261991L;

    public static final String VUE = "/WEB-INF/views/dashboard.jsp";

    static final Logger LOG = LoggerFactory.getLogger(Dashboard.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        ServletMapper.getDashboard(request);
        request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}