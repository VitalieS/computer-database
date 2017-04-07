package com.excilys.computerdatabase.presentation.cliui;

import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.configuration.ConfigurationException;

/**
 * @author Vitalie SOVA
 *
 */
public class View {

    /**
     * The main method.
     *
     * @param args - Arguments main
     * @throws SQLException
     *             - The SQL exception
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void main(String[] args) throws SQLException, ConfigurationException {

        System.out.println("Welcome, master !");
        System.out.println("What do you want to do today ?");
        int choice;
        Scanner keyboard = new Scanner(System.in);

        ComputerView computerView = new ComputerView();
        CompanyView companyView = new CompanyView();

        inputloop : while (true) {
            printMenu();

            while (!keyboard.hasNextInt()) {
                System.out.println("Nope, that's not it!");
                keyboard.next();
            }
            choice = keyboard.nextInt();

            switch (choice) {
                case 1 :
                    computerView.showComputersList();
                    break;
                case 2 :
                    companyView.showCompaniesList();
                    break;
                case 3 :
                    computerView.showComputersDetails();
                    break;
                case 4 :
                    companyView.showCompaniesDetails();
                    break;
                case 5 :
                    computerView.createComputer();
                    break;
                case 6 :
                    computerView.updateComputer();
                    break;
                case 7 :
                    computerView.deleteComputer();
                    break;
                case 8 :
                    computerView.showComputersListPage();
                    break;
                case 9 :
                    companyView.showCompaniesListPage();
                    break;
                case 10 :
                    computerView.showComputersListPageCallingAll();
                    break;
                case 11 :
                    companyView.showCompaniesListPageCallingAll();
                    break;
                case 12 :
                    System.out.println("Good bye !");
                    break inputloop;
                case 13 :
                    break;
                default :
                    System.out.println("Invalid option, try again!");
                    break;
            }
        }
        keyboard.close();
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