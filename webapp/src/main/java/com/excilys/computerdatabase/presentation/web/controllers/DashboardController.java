package com.excilys.computerdatabase.presentation.web.controllers;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController extends HttpServlet {

    private static final long serialVersionUID = -7393279402298261991L;

    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    public ComputerService computerService;

    public ComputerService getComputerService() {
        return computerService;
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }

    public DashboardController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    private ModelAndView dashboard(@RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "submit", required = false) Long submit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false) String sort) {
        LOG.info("Entering DashboardServlet");
        return getDashboard(submit, page, search, sort);
    }

    private ModelAndView getDashboard(Long submit, String page, String search, String sort) {
        int number = 10;
        if (submit != null) {
            number = submit.intValue();
        }

        Long idFirst = (long) 1;
        if (page != null) {
            Long nbPage = (Long.parseLong(page));
            idFirst = ((nbPage - 1) * number + 1);
        }

        String sortBy = null;
        if (sort != null) {
            sortBy = sort;
        }

        String searchName = null;
        if (search != null) {
            searchName = search;
        } else {
            searchName = "";
        }
        ModelAndView mav = new ModelAndView("dashboard");
        mav.addObject("computerList", computerService.getComputerInRangeNb(idFirst, number, Page.SortingBy.getSort(sortBy), searchName));
        mav.addObject("nbComputer", computerService.getNumberOfComputers());
        mav.addObject("search", searchName);
        if (page != null) {
            mav.addObject("currentPage", page);
        } else {
            mav.addObject("currentPage", 1);
        }
        mav.addObject("sort", sortBy);
        mav.addObject("submit", number);
        mav.addObject("maxPage", computerService.getNumberOfPages(number));

        return mav;

    }
}