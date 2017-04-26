package com.excilys.computerdatabase.presentation.cliui;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * @author Vitalie SOVA
 *
 */
public class View {
	private static ComputerService computerService;
	private static CompanyService companyService;
	private static ComputerView computerView;
	private static CompanyView companyView;
	static {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		computerService = (ComputerService) context.getBean("computerService");
		companyService = (CompanyService) context.getBean("companyService");
		computerView = (ComputerView) context.getBean("computerView");
		companyView = (CompanyView) context.getBean("companyView");
	}
	
	/**
	 * The main method.
	 *
	 * @param args
	 *            - Arguments main
	 */
	public static void main(String[] args) {

		System.out.println("Welcome, master !");
		System.out.println("What do you want to do today ?");
		
        switchMenu();

	}

	private static void switchMenu() {
		int choice;

		Scanner keyboard = new Scanner(System.in);

		inputloop: while (true) {
			printMenu();

			while (!keyboard.hasNextInt()) {
				System.out.println("Nope, that's not it!");
				keyboard.next();
			}
			choice = keyboard.nextInt();

			switch (choice) {
			case 1:
				computerView.showComputersList();
				break;
			case 2:
				companyView.showCompaniesList();
				break;
			case 3:
				computerView.showComputersDetails();
				break;
			case 4:
				companyView.showCompaniesDetails();
				break;
			case 5:
				computerView.createComputer();
				break;
			case 6:
				computerView.updateComputer();
				break;
			case 7:
				computerView.deleteComputer();
				break;
			case 8:
				computerView.showComputersListPage();
				break;
			case 9:
				companyView.showCompaniesListPage();
				break;
			case 10:
				computerView.showComputersListPageCallingAll();
				break;
			case 11:
				companyView.showCompaniesListPageCallingAll();
				break;
			case 12:
				System.out.println("Good bye !");
				break inputloop;
			case 13:
				break;
			default:
				System.out.println("Invalid option, try again!");
				break;
			}
		}
		// keyboard.close();		
	}

	/**
	 * Prints the menu.
	 */
	private static void printMenu() {
		System.out.println("Here are your options : ");
		System.out.println("------------------------");
		System.out.println("1. List computers");
		System.out.println("2. List companies");
		System.out.println("3. Show computer details");
		System.out.println("4. Show company details");
		System.out.println("5. Create a computer");
		System.out.println("6. Update a computer");
		System.out.println("7. Delete a computer");
		System.out.println("8. List specific page of computers");
		System.out.println("9. List specific page of companies");
		System.out.println("10. List specific page of computers");
		System.out.println("11. List specific page of companies");
		System.out.println("12. EXIT");
		System.out.println("------------------------");
		System.out.println("What's your choice ?");
	}
}
