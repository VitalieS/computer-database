package com.excilys.computerdatabase.presentation.web.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ServletMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.Validator;

/**
 * Servlet implementation class AddComputer.
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerController extends HttpServlet {

    private static final long serialVersionUID = 2761476641006993989L;

    public static final String VUE = "/WEB-INF/views/addComputer.jsp";

    static final Logger LOG = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    public ComputerService computerService;
    @Autowired
    public CompanyService companyService;

    @RequestMapping(path = "/addComputer", method = RequestMethod.GET)
    private ModelAndView getAddController() {
        LOG.info("Entering AddServlet - GET");
        return getAdd();
    }

    @RequestMapping(method = RequestMethod.POST)
    private ModelAndView postAddController(@RequestParam(value = "computerId", required = false) String computerId,
            @RequestParam(value = "computerName", required = false) String computerName,
            @RequestParam(value = "introducedDate", required = false) String introducedDate,
            @RequestParam(value = "discontinuedDate", required = false) String discontinuedDate,
            @RequestParam(value = "companyId", required = false) String companyId) {
        LOG.info("Entering AddServlet - POST");
        ModelAndView mav = new ModelAndView("addComputer");
        ComputerDTO computer = ServletMapper.mapperToDTO(computerId, computerName, introducedDate, discontinuedDate, companyId, companyService.getCompaniesList());
        Map<String, String> errors = Validator.validate(computer);
        if (!errors.isEmpty()) {
            LOG.debug("Errors found while adding the computer.");
            mav.addObject("exception", errors.toString());
            getAddController();
        } else {
            computerService.createComputer(computer);
            mav = new ModelAndView("redirect:dashboard");
        }
        return mav;
    }

    public ModelAndView getAdd() {
        ModelAndView mav = new ModelAndView("addComputer");
        mav.setViewName("addComputer");
        List<Company> companyList = companyService.getCompaniesList();
        mav.addObject("companyList", companyList);
        return mav;
    }
}