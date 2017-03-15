package com.excilys.computerdatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.excilys.computerdatabase.entity.Company;
import com.excilys.computerdatabase.entity.Computer;
import com.excilys.computerdatabase.persistance.CompanyDAO;
import com.excilys.computerdatabase.persistance.ComputerDAO;

import java.time.format.DateTimeFormatter;;

public class Main {

	public static void main(String[] args) throws SQLException {

		DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println("Welcome, master !");
		System.out.println("What do you want to do today ?");
		int choice;
		Scanner keyboard = new Scanner(System.in);

		inputloop: while (true) {
			System.out.println("Here are your options : ");
			System.out.println("------------------------");

			System.out.println("1. List computers");
			System.out.println("2. List companies");
			System.out.println("3. Show computer details");
			System.out.println("4. Show company details");
			System.out.println("5. Create a computer");
			System.out.println("6. Update a computer");
			System.out.println("7. Delete a computer");
			System.out.println("10. EXIT");
			System.out.println("------------------------");

			System.out.println("What's your choice ?");

			choice = keyboard.nextInt();

			switch (choice) {
			case 1:
				ArrayList<Computer> computerList = ComputerDAO.getComputerList();
				for (Computer e : computerList)
					e.showInfo();
				break;
			case 2:
				ArrayList<Company> companyList = CompanyDAO.getCompaniesList();
				for (Company e : companyList)
					e.showInfo();
				break;
			case 3:
				Scanner keyboardComputerId = new Scanner(System.in);
				System.out.println("What's your computer ID?");
				int number;
				try {
					number = Integer.parseInt(keyboardComputerId.nextLine());
					Computer computer = ComputerDAO.getComputerById(Long.valueOf(number));
					computer.showInfo();
					break; // this will escape the while loop
				} catch (Exception e) {
					System.out.println("That is not a valid id. Try again.");
					break;
				}
			case 4:
				Scanner keyboardCompanyID = new Scanner(System.in);
				System.out.println("What's your company ID ?");
				int numberC;
				try {
					numberC = Integer.parseInt(keyboardCompanyID.nextLine());
					Company company = CompanyDAO.getCompanyById(Long.valueOf(numberC));
					company.showInfo();
					break; // this will escape the while loop
				} catch (Exception e) {
					System.out.println("That is not a valid id. Try again.");
					break;
				}

			case 5:
				Scanner keyboardNewComputername = new Scanner(System.in);
				System.out.println("What's the new computer name?");
				String newName = keyboardNewComputername.nextLine();

				Scanner keyboardNewComputerIDate = new Scanner(System.in);
				System.out.println("What's the new computer iDate? [dd/mm/yyyy]");
				LocalDate newIDate = LocalDate.parse(keyboardNewComputerIDate.nextLine(), DATE_FORMAT);

				Scanner keyboardNewComputerDDate = new Scanner(System.in);
				System.out.println("What's the new computer dDate? [dd/mm/yyyy]");
				LocalDate newDDate = LocalDate.parse(keyboardNewComputerDDate.nextLine(), DATE_FORMAT);

				Scanner keyboardNewComputerManufacturer = new Scanner(System.in);
				System.out.println("What's the new computer manufacturer");
				String newManufacturer = keyboardNewComputerManufacturer.nextLine();

				Computer c = new Computer.ComputerBuilder(newName).introducedDate(newIDate).discontinuedDate(newDDate)
						.company(Long.valueOf(newManufacturer)).build();

				c.showInfo();

				Long key = ComputerDAO.createComputer(c);
				Computer addedCompany = ComputerDAO.getComputerById(key);
				addedCompany.showInfo();
				break;
			case 6:
				Scanner keyboardID = new Scanner(System.in);
				System.out.println("Which computer do you want to modify ?");
				int numberID;
				try {
					numberID = Integer.parseInt(keyboardID.nextLine());
					Computer computer = ComputerDAO.getComputerById(Long.valueOf(numberID));
					System.out.println("This is the computer you want to edit:");
					computer.showInfo();
				} catch (Exception e) {
					System.out.println("That is not a valid id. Try again.");
					break;
				}
				
				Computer c1 = null;
				Scanner keyboardNewComputername1 = new Scanner(System.in);
				System.out.println("What's the new computer name?");
				String newName1 = keyboardNewComputername1.nextLine();
				if(newName1 != null && !newName1.trim().isEmpty()) {
					c1 = new Computer.ComputerBuilder(newName1).build();
				}

				Scanner keyboardNewComputerIDate1 = new Scanner(System.in);
				System.out.println("What's the new computer iDate? [dd/mm/yyyy]");
				String newIDate1 = keyboardNewComputerIDate1.nextLine();
				if(newIDate1 != null && !newIDate1.trim().isEmpty()) {
					LocalDate newIDate1LD = LocalDate.parse(newIDate1, DATE_FORMAT);
					c1.setIntroducedDate(newIDate1LD);
				}

				Scanner keyboardNewComputerDDate1 = new Scanner(System.in);
				System.out.println("What's the new computer dDate? [dd/mm/yyyy]");
				String newDDate1 = keyboardNewComputerDDate1.nextLine();
				if(newIDate1 != null && !newIDate1.trim().isEmpty()) {
					LocalDate newDDateDD = LocalDate.parse(newDDate1, DATE_FORMAT);
					c1.setDiscontinuedDate(newDDateDD);
				}

				Scanner keyboardNewComputerManufacturer1 = new Scanner(System.in);
				System.out.println("What's the new computer manufacturer");
				String newmanufacturer1 = keyboardNewComputerManufacturer1.nextLine();
				if(newmanufacturer1 != null && !newName1.trim().isEmpty()) {
					c1.setManufacturerId(Long.valueOf(newmanufacturer1));
				}

				c1.showInfo();
				ComputerDAO.updateComputer(Long.valueOf(numberID), c1);
				ComputerDAO.getComputerById(Long.valueOf(numberID)).showInfo();
				break;
			case 7:
				Scanner keyboardDelete = new Scanner(System.in);
				System.out.println("Which computer do you want to delete ?");
				
				int numberDelete;
				try {
					numberDelete = Integer.parseInt(keyboardDelete.nextLine());
					Computer computer = ComputerDAO.getComputerById(Long.valueOf(numberDelete));
					System.out.println("This is the computer you want to delete");
					computer.showInfo();
					ComputerDAO.deleteComputer(Long.valueOf(numberDelete));
				} catch (Exception e) {
					System.out.println("That is not a valid id. Try again.");
					break;
				}
				
				break;
			case 10:
				System.out.println("Good bye !");
				break inputloop;
			default:
				System.out.println("Invalid option, try again!");
				break;
			}
		}
		keyboard.close();

	}

}
