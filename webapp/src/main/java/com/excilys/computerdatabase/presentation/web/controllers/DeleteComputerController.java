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

import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer.
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerController extends HttpServlet {

    private static final long serialVersionUID = -7393279402298261991L;

    private static final Logger LOG = LoggerFactory
            .getLogger(DashboardController.class);

    @Autowired
    public ComputerService computerService;

    public ComputerService getComputerService() {
        return computerService;
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }

    public DeleteComputerController() {
        super();
    }

    @RequestMapping(method = RequestMethod.POST)
    private ModelAndView delete(@RequestParam(value = "selection", required = false) String selection) {
        LOG.info("Entering DeleteComputerServlet");
        if (selection != null) {
            String toDeleteSelection = selection;
            String[] toDeleteSelectionIds = toDeleteSelection.split(",");
            for (String toDeleteSelectionId : toDeleteSelectionIds) {
                computerService.deleteComputer(Long.parseLong(toDeleteSelectionId));
            }
        }
        return new ModelAndView("redirect:/dashboard");
    }

}