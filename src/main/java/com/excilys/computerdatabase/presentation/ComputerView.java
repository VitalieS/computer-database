package main.java.com.excilys.computerdatabase.presentation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.model.Page;
import main.java.com.excilys.computerdatabase.service.ComputerService;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerView {

    /**
     *
     */
    private static ComputerService computerService = new ComputerService();
    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * @throws SQLException - The SQL exception
     */
    protected void showComputersList() throws SQLException {
        ArrayList<Computer> computerList = computerService.getComputerList();
        for (Computer c : computerList) {
            System.out.println(c.toString());
        }
    }

    /**
     *
     */
    protected void showComputersDetails() {
        @SuppressWarnings("resource")
        // TODO Manage scanner leak
        Scanner keyboardComputerId = new Scanner(System.in);
        System.out.println("What's your computer ID?");
        int number;
        try {
            number = Integer.parseInt(keyboardComputerId.nextLine());
            Computer computer = computerService
                    .getComputerById(Long.valueOf(number));
            System.out.println("Computer found:" + computer.toString());
            return; // this will escape the while loop
        } catch (Exception e) {
            System.out.println("No computer has this id. Try again.");
            return;
        }
    }

    /**
     * @throws SQLException - The SQL exception
     */
    @SuppressWarnings("resource")
    // TODO Manage scanner leak
    protected void createComputer() throws SQLException {
        Scanner keyboardNewComputername = new Scanner(System.in);
        System.out.println("What's the new computer name?");
        String newName = keyboardNewComputername.nextLine();
        Computer c = new Computer();
        if (newName != null && !newName.trim().isEmpty()) {
            c.setComputerName(newName);
        } else {
            System.out.println("The computer can't have empty name field");
            return;
        }

        Scanner keyboardNewComputerIDate = new Scanner(System.in);
        System.out.println("What's the new computer iDate? [dd/mm/yyyy]");
        String newIDate = keyboardNewComputerIDate.nextLine();
        if (newIDate != null && !newIDate.trim().isEmpty()) {
            LocalDate newIDate1LD = LocalDate.parse(newIDate, DATE_FORMAT);
            c.setIntroducedDate(newIDate1LD);
        }

        Scanner keyboardNewComputerDDate = new Scanner(System.in);
        System.out.println("What's the new computer dDate? [dd/mm/yyyy]");
        String newDDate = keyboardNewComputerDDate.nextLine();
        if (newDDate != null && !newDDate.trim().isEmpty()) {
            LocalDate newDDate1LD = LocalDate.parse(newDDate, DATE_FORMAT);
            c.setIntroducedDate(newDDate1LD);
        }

        Scanner keyboardNewComputerManufacturer = new Scanner(System.in);
        System.out.println("What's the new computer manufacturer");
        String newManufacturer = keyboardNewComputerManufacturer.nextLine();
        if (newManufacturer != null && !newManufacturer.trim().isEmpty()) {
            c.setCompanyId(Long.valueOf(newManufacturer));
        }

        System.out.println(c.toString());

        Long key = computerService.createComputer(c);
        Computer addedCompany = computerService.getComputerById(key);
        System.out.println(addedCompany.toString());
    }

    /**
     * @throws SQLException - The SQL exception
     */
    @SuppressWarnings("resource")
    // TODO Manage scanner leak
    protected void updateComputer() throws SQLException {
        Scanner keyboardID = new Scanner(System.in);
        System.out.println("Which computer do you want to modify ?");
        int numberID;
        Computer computer;
        try {
            numberID = Integer.parseInt(keyboardID.nextLine());
            computer = computerService.getComputerById(Long.valueOf(numberID));
            System.out.println("This is the computer you want to edit:");
            System.out.println(computer.toString());
        } catch (Exception e) {
            System.out.println("That is not a valid id. Try again.");
            return;
        }

        Computer c1 = new Computer();
        c1.setComputerId(Long.valueOf(numberID));
        Scanner keyboardNewComputername1 = new Scanner(System.in);
        System.out.println("What's the new computer name?");
        String newName1 = keyboardNewComputername1.nextLine();
        if (newName1 != null && !newName1.trim().isEmpty()) {
            c1.setComputerName(newName1);
        } else {
            c1.setComputerName(computer.getComputerName());
        }

        Scanner keyboardNewComputerIDate1 = new Scanner(System.in);
        System.out.println("What's the new computer iDate? [dd/mm/yyyy]");
        String newIDate1 = keyboardNewComputerIDate1.nextLine();
        if (newIDate1 != null && !newIDate1.trim().isEmpty()) {
            LocalDate newIDate1LD = LocalDate.parse(newIDate1, DATE_FORMAT);
            c1.setIntroducedDate(newIDate1LD);
        } else {
            c1.setIntroducedDate(computer.getIntroducedDate());
        }

        Scanner keyboardNewComputerDDate1 = new Scanner(System.in);
        System.out.println("What's the new computer dDate? [dd/mm/yyyy]");
        String newDDate1 = keyboardNewComputerDDate1.nextLine();
        if (newDDate1 != null && !newDDate1.trim().isEmpty()) {
            LocalDate newDDateDD = LocalDate.parse(newDDate1, DATE_FORMAT);
            c1.setDiscontinuedDate(newDDateDD);
        } else {
            c1.setDiscontinuedDate(computer.getDiscontinuedDate());
        }

        Scanner keyboardNewComputerManufacturer1 = new Scanner(System.in);
        System.out.println("What's the new computer manufacturer");
        String newmanufacturer1 = keyboardNewComputerManufacturer1.nextLine();
        if (newmanufacturer1 != null && !newmanufacturer1.trim().isEmpty()) {
            c1.setCompanyId(Long.valueOf(newmanufacturer1));
        } else {
            c1.setCompanyId(computer.getCompanyId());
        }

        System.out.println(c1.toString());
        computerService.updateComputer(Long.valueOf(numberID), c1);
        System.out.println(computerService
                .getComputerById(Long.valueOf(numberID)).toString());
    }

    /**
     *
     */
    protected void deleteComputer() {
        @SuppressWarnings("resource")
        // TODO Manage scanner leak
        Scanner keyboardDelete = new Scanner(System.in);
        System.out.println("Which computer do you want to delete ?");
        Computer computerDelete;
        int numberDelete;
        try {
            numberDelete = Integer.parseInt(keyboardDelete.nextLine());
            computerDelete = computerService
                    .getComputerById(Long.valueOf(numberDelete));
            System.out.println("This is the computer you want to delete");
            System.out.println(computerDelete.toString());
            // TODO Confirm deletion
            computerService.deleteComputer(Long.valueOf(numberDelete));
        } catch (Exception e) {
            System.out.println("That is not a valid id. Try again.");
            return;
        }
    }

    /**
     * @throws SQLException - The SQL exception
     */
    protected void showComputersListPageCallingAll() throws SQLException {
        Page<Computer> pageComputers = new Page<>(
                computerService.getComputerList());
        System.out.println(
                "There are currently " + pageComputers.getNbPage() + " pages");
        Scanner keyboardShowPage = new Scanner(System.in);
        System.out.print("Which page do you want to list ? ");
        int pageNb = keyboardShowPage.nextInt();

        List<Computer> newPage = pageComputers.getPageInRange(pageNb);
        for (Computer c : newPage) {
            System.out.println(c.toString());
        }

        while (true) {
            Scanner seeNext = new Scanner(System.in);
            System.out.print(
                    "Write 0 if you want to go to the previous page or 1 if you want to go to the next one. \n");
            int newpageNb = seeNext.nextInt();
            if (newpageNb == 1) {
                List<Computer> newPageNext = pageComputers.getNextPage();
                for (Computer e : newPageNext) {
                    System.out.println(e.toString());
                }
            } else if (newpageNb == 0) {
                List<Computer> newPagePrev = pageComputers.getPrevPage();
                for (Computer e : newPagePrev) {
                    System.out.println(e.toString());
                }
            } else {
                return;
            }
        }
    }

    /**
     * @throws SQLException - The SQL exception
     */
    public void showComputersListPage() throws SQLException {
        // System.out.println("There are currently " + pageComputers.getNbPage()
        // + " pages");
        Scanner keyboardShowPage = new Scanner(System.in);
        System.out.print("Which page do you want to list ? ");
        int pageNb = keyboardShowPage.nextInt();

        // System.out.println("Access to company page n°" +
        // pageComputers.getNbPage());

        long idBegin = pageNb * Page.elementsByPage;
        long idEnd = pageNb * Page.elementsByPage + Page.elementsByPage;
        System.out.println(idBegin);
        System.out.println(idEnd);

        Page<Computer> pageComputers = new Page<>(
                computerService.getComputerInRange(idBegin, idEnd));
        List<Computer> newPages = pageComputers.getPage((int) idBegin,
                (int) idEnd);
        for (Computer c : newPages) {
            System.out.println("Ha" + c.toString());
        }
    }
}
