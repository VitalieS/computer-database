package com.excilys.computerdatabase.persistance.mappers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.service.CompanyServiceTest;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.ComputerServiceTest;

public class ServletMapper {

    static final Logger LOG = LoggerFactory.getLogger(ServletMapper.class);

    public static void getDashboard(HttpServletRequest request) {
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

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
        ComputerService computerService = (ComputerService) applicationContext.getBean("computerService");
        request.setAttribute("computerList", computerService.getComputerInRangeNb(idFirst, number, Page.SortingBy.getSort(sort), search));
        //request.setAttribute("computerList", ComputerServiceTest.INSTANCE.getComputerInRangeNb(idFirst, number, Page.SortingBy.getSort(sort), search));
        ((AbstractApplicationContext) applicationContext).close();
        request.setAttribute("nbComputer", ComputerServiceTest.INSTANCE.getNumberOfComputers());
        request.setAttribute("search", search);
        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }
        request.setAttribute("sort", sort);
        request.setAttribute("submit", number);
        // request.setAttribute("maxPage", ComputerService.getNumberOfPages(number));
    }

    public static ArrayList<Company> getAdd(HttpServletRequest request) {
        ArrayList<Company> companyList = CompanyServiceTest.INSTANCE.getCompaniesList();
        /*
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        CompanyService companyService = (CompanyService) applicationContext.getBean("companyService");
        ArrayList<Company> companyList = companyService.getCompaniesList();
        ((AbstractApplicationContext) applicationContext).close();
         */
        request.setAttribute("companyList", companyList);
        return companyList;
    }

    public static void getEdit(HttpServletRequest request) {
        ComputerDTO computerToEdit = ComputerMapper.mapper(ComputerServiceTest.INSTANCE.getComputerById(Long.valueOf(request.getParameter("computerId"))));
        request.setAttribute("computerToEdit", computerToEdit);
        request.setAttribute("companyOfTheEditedComputer", CompanyServiceTest.INSTANCE.getCompanyById(computerToEdit.getCompanyId()));
        request.setAttribute("companyList", CompanyServiceTest.INSTANCE.getCompaniesList());
    }

    public static void delete(HttpServletRequest request) {
        if (request.getParameter("selection") != null) {
            String toDeleteSelection = request.getParameter("selection");
            String[] toDeleteSelectionIds = toDeleteSelection.split(",");
            for (String toDeleteSelectionId : toDeleteSelectionIds) {
                ComputerServiceTest.INSTANCE.deleteComputer(Long.parseLong(toDeleteSelectionId));
            }
        }
    }

    public static ComputerDTO mapperToDTO(HttpServletRequest request, ArrayList<Company> list) {
        String computerId = request.getParameter("computerId");
        Long id = (computerId == null || computerId.trim().isEmpty() ? null : Long.parseLong(computerId.trim()));
        String computerName = request.getParameter("computerName");
        String name = (computerName == null || computerName.trim().isEmpty() ? null : computerName.trim());
        String introducedDate = request.getParameter("introducedDate");
        String introduced = (introducedDate == null || introducedDate.trim().isEmpty() ? null : introducedDate.trim());
        String discontinuedDate = request.getParameter("discontinuedDate");
        String discontinued = (discontinuedDate == null || discontinuedDate.trim().isEmpty() ? null  : discontinuedDate.trim());
        String companyId = request.getParameter("companyId");
        Long companyIdl = (companyId == null || companyId.trim().isEmpty() ? null : Long.parseLong(companyId.trim()));
        String companyName = (companyId == null ? null : list.get(Integer.parseInt(companyId.trim())).getCompanyName());
        LOG.info("Trying to add: " + computerId + " " + computerName + " " + introducedDate + " " + discontinuedDate + " " + companyId);
        return new ComputerDTO.ComputerBuilder(name).id(id).introducedDate(introduced).discontinuedDate(discontinued)
                .companyId(companyIdl).companyName(companyName).build();
    }
}
