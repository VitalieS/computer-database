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

import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = -7393279402298261991L;

    private static final String VUE = "/WEB-INF/views/dashboard.jsp";

    private static final Logger LOG = LoggerFactory.getLogger(Dashboard.class);

    @Autowired
    public ComputerService computerService;

    @Override
    public void init() {
        WebApplicationContext contextApp = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.computerService = (ComputerService) contextApp.getBean("computerService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        getDashboard(request);
        request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    public void getDashboard(HttpServletRequest request) {
        int number = 10;
        if (request.getParameter("submit") != null) {
            number = (Integer.parseInt(request.getParameter("submit")));
        }

        Long idFirst = (long) 1;
        if (request.getParameter("page") != null) {
            Long nbPage = (Long.parseLong(request.getParameter("page")));
            idFirst = ((nbPage - 1) * number + 1);
        }

        String sort = null;
        if (request.getParameter("sort") != null) {
            sort = request.getParameter("sort");
        }

        String search = null;
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }
        request.setAttribute("computerList", computerService.getComputerInRangeNb(idFirst, number, Page.SortingBy.getSort(sort), search));
        request.setAttribute("nbComputer", computerService.getNumberOfComputers());
        request.setAttribute("search", search);
        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }
        request.setAttribute("sort", sort);
        request.setAttribute("submit", number);
        request.setAttribute("maxPage", computerService.getNumberOfPages(number));
    }
}