package com.excilys.computerdatabase.presentation.cliui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.model.entities.Company;
import com.excilys.computerdatabase.model.entities.Page;
import com.excilys.computerdatabase.service.CompanyService;

/**
 * @author Vitalie SOVA
 *
 */
public class CompanyView {

    /**
     *
     */
    protected void showCompaniesList() {
        ArrayList<Company> companyList = CompanyService.INSTANCE.getCompaniesList();
        for (Company e : companyList) {
            System.out.println(e.toString());
        }
    }

    protected void showCompaniesDetails() {
        @SuppressWarnings("resource")
        // TODO Manage scanner leak
        Scanner keyboardCompanyID = new Scanner(System.in);
        System.out.println("What's your company ID ?");
        int numberC;
        try {
            numberC = Integer.parseInt(keyboardCompanyID.nextLine());
            Company company = CompanyService.INSTANCE.getCompanyById(Long.valueOf(numberC));
            System.out.println(company.toString());
            return; // this will escape the while loop
        } catch (Exception e) {
            System.out.println("That is not a valid id. Try again.");
            return;
        }
    }

    /**
     *
     */
    protected void showCompaniesListPageCallingAll() {
        Page<Company> pageCompany = new Page<>(CompanyService.INSTANCE.getCompaniesList());
        System.out.println("There are currently " + pageCompany.getNumPage() + " pages");
        Scanner keyboardShowPage = new Scanner(System.in);
        System.out.print("Which page do you want to list ? ");
        int pageNb = keyboardShowPage.nextInt();

        List<Company> newPage = pageCompany.getPageInRange(pageNb);
        for (Company e : newPage) {
            System.out.println(e.toString());
        }

        while (true) {
            Scanner seeNext = new Scanner(System.in);
            System.out.print("Write 0 if you want to go to the previous page or 1 if you want to go to the next one. \n");
            int newpageNb = seeNext.nextInt();
            if (newpageNb == 1) {
                List<Company> newPageNext = pageCompany.getNextPage();
                for (Company e : newPageNext) {
                    System.out.println(e.toString());
                }
            } else if (newpageNb == 0) {
                List<Company> newPagePrev = pageCompany.getPrevPage();
                for (Company e : newPagePrev) {
                    System.out.println(e.toString());
                }
            } else {
                return;
            }
        }
    }

    /**
     *
     */
    public void showCompaniesListPage() {
        // TODO Auto-generated method stub
    }
}
