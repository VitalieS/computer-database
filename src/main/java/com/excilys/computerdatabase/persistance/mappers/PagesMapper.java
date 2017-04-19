package com.excilys.computerdatabase.persistance.mappers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class PagesMapper {

    public static void fromDashboard(HttpServletRequest request) {
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

        request.setAttribute("computerList", ComputerService.INSTANCE.getComputerInRangeNb(idFirst, number, Page.SortingBy.getSort(sort), search));
        request.setAttribute("nbComputer", ComputerService.INSTANCE.getNumberOfComputers());
        request.setAttribute("search", search);
        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }
        request.setAttribute("sort", sort);
        request.setAttribute("submit", number);
        request.setAttribute("maxPage", ComputerService.getNumberOfPages(number));
    }

    public static ArrayList<Company> fromAdd(HttpServletRequest request) {
        ArrayList<Company> companyList = CompanyService.INSTANCE.getCompaniesList();
        request.setAttribute("companyList", companyList);
        return companyList;
    }

    public static void fromEdit(HttpServletRequest request) {
        ComputerDTO computerToEdit = ComputerMapper.mapper(ComputerService.INSTANCE.getComputerById(Long.valueOf(request.getParameter("computerId"))));
        request.setAttribute("computerToEdit", computerToEdit);
        request.setAttribute("companyOfTheEditedComputer", CompanyService.INSTANCE.getCompanyById(computerToEdit.getCompanyId()));
        request.setAttribute("companyList", CompanyService.INSTANCE.getCompaniesList());
    }

    public static void delete(HttpServletRequest request) {
        if (request.getParameter("selection") != null) {
            String toDeleteSelection = request.getParameter("selection");
            String[] toDeleteSelectionIds = toDeleteSelection.split(",");
            for (String toDeleteSelectionId : toDeleteSelectionIds) {
                ComputerService.INSTANCE.deleteComputer(Long.parseLong(toDeleteSelectionId));
            }
        }
    }
}
