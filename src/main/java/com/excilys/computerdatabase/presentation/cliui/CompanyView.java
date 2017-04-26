package com.excilys.computerdatabase.presentation.cliui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.CompanyService;

/**
 * @author Vitalie SOVA
 *
 */
@Service("companyView")
public class CompanyView {

	private org.slf4j.Logger LOG = LoggerFactory.getLogger(CompanyView.class);

	@Autowired
	private CompanyService companyService;

	/**
	 *
	 */
	protected void showCompaniesList() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		companyList = companyService.getCompaniesList();
		for (Company e : companyList) {
			System.out.println(e.toString());
		}
	}

	/**
	 *
	 */
	protected void showCompaniesDetails() {
		Scanner keyboardCompanyId = new Scanner(System.in);
		System.out.println("What's your company ID ?");
		int numberC;
		try {
			numberC = Integer.parseInt(keyboardCompanyId.nextLine());
			Company company = companyService.getCompanyById(Long.valueOf(numberC));
			System.out.println(company.toString());
			// keyboardCompanyId.close();
			return;
		} catch (Exception e) {
			System.out.println("That is not a valid id. Try again.");
			return;
		}
	}

	/**
	 *
	 */
	protected void showCompaniesListPageCallingAll() {
		Page<Company> pageCompany = new Page<>(companyService.getCompaniesList());
		LOG.info("There are currently " + pageCompany.getNumPage() + " pages");
		Scanner keyboardShowPage = new Scanner(System.in);
		LOG.info("Which page do you want to list ?");
		int pageNb = keyboardShowPage.nextInt();

		List<Company> newPage = pageCompany.getPageInRange(pageNb);
		for (Company e : newPage) {
			System.out.println(e.toString());
		}
		// keyboardShowPage.close();
		while (true) {
			Scanner seeNext = new Scanner(System.in);
			System.out
					.print("Write 0 if you want to go to the previous page or 1 if you want to go to the next one. \n");
			int newpageNb = seeNext.nextInt();
			seeNext.close();
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
		Scanner keyboardShowPage = new Scanner(System.in);
		System.out.print("Which page do you want to list ? ");
		int pageNb = keyboardShowPage.nextInt();

		long idBegin = pageNb * Page.elementsByPage;
		long idEnd = pageNb * Page.elementsByPage + Page.elementsByPage;
		System.out.println(idBegin);
		System.out.println(idEnd);

		Page<Company> pageCompany = new Page<>(companyService.getCompanyInRange(idBegin, idEnd));
		List<Company> newPages = pageCompany.getPage((int) idBegin, (int) idEnd);
		for (Company c : newPages) {
			System.out.println(c.toString());
		}
		// keyboardShowPage.close();
	}

	public void deleteCompany() {
        Scanner keyboardDelete = new Scanner(System.in);
        System.out.println("Which company do you want to delete ?");
        Company companyDelete;
        int numberDelete;
        try {
            numberDelete = Integer.parseInt(keyboardDelete.nextLine());
            companyDelete = companyService.getCompanyById(Long.valueOf(numberDelete));
            System.out.println("This is the computer you want to delete");
            System.out.println(companyDelete.toString());
            companyService.delete(Long.valueOf(numberDelete));
        } catch (Exception e) {
            System.out.println("That is not a valid id. Try again.");
            keyboardDelete.close();
            return;
        }
	}
}
