package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.entities.Page;
import com.excilys.computerdatabase.service.ComputerService;



public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = -7393279402298261991L;

    public static final String VUE = "/views/dashboard.jsp";

    static final Logger LOG = LoggerFactory.getLogger(Dashboard.class);

    String sort;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        int nbId = 10;
        if (request.getParameter("submit") != null) {
            nbId = (Integer.parseInt(request.getParameter("submit")));
        }

        Long debut = (long) 1;
        if (request.getParameter("page") != null) {
            Long nbPage = (Long.parseLong(request.getParameter("page")));
            debut = ((nbPage - 1) * nbId + 1);
        }

        String search = null;
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }

        sort = null;
        if (request.getParameter("sort") != null) {
            sort = request.getParameter("sort");
        }

        request.setAttribute("computerList", ComputerService.INSTANCE.getComputerInRangeNb(debut, nbId, Page.SortingBy.getSort(sort), search));
        request.setAttribute("nbComputer", ComputerService.INSTANCE.getNumberOfComputers());
        request.setAttribute("search", search);

        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }
        request.setAttribute("sort", sort);
        request.setAttribute("submit", nbId);
        request.setAttribute("maxPage", ComputerService.getNumberOfPages(nbId));
        request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        System.out.println("Selection is" + request.getParameter("selection"));
        String toDeleteSelection = "";
        if (request.getParameter("selection") != null) {
            toDeleteSelection = request.getParameter("selection");
            System.out.println("Get is" + toDeleteSelection);
            String[] toDeleteSelectionIds = toDeleteSelection.split(",");
            System.out.println(toDeleteSelectionIds);
            for (String toDeleteSelectionId : toDeleteSelectionIds) {
                ComputerService.INSTANCE.deleteComputer(Long.parseLong(toDeleteSelectionId));
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }

}