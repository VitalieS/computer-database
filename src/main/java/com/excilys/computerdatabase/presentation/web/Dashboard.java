package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.service.ComputerService;



public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = -7393279402298261991L;

    public static final String VUE = "/views/dashboard.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("get");
        int nbId = 10;
        if (request.getParameter("submit") != null) {
            nbId = (Integer.parseInt(request.getParameter("submit")));
        }

        Long debut = (long) 1;
        if (request.getParameter("page") != null) {
            Long nbPage = (Long.parseLong(request.getParameter("page")));
            debut = ((nbPage - 1) * nbId + 1);
        }

        request.setAttribute("computerList",
                ComputerService.INSTANCE.getComputerInRangeNb(debut, nbId));
        request.setAttribute("nbComputer",
                ComputerService.INSTANCE.getNumberOfComputers());

        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }

        request.setAttribute("maxPage", ComputerService.maxPages(nbId));

        request.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("post");
        String idList = "";
        if (request.getParameter("selection") != null) {
            System.out.println("Selection is" + request.getParameter("selection"));
            idList = request.getParameter("selection");
        }
        String[] ids = idList.split(",");
        for (String idStr : ids) {
            long id = Long.parseLong(idStr);
            ComputerService.INSTANCE.deleteComputer(id);
        }
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");

    }
}