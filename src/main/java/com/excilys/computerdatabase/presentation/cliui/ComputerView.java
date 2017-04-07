package com.excilys.computerdatabase.presentation.cliui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.model.entities.Computer;
import com.excilys.computerdatabase.model.entities.Page;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * @author Vitalie SOVA
 *
 */
public class ComputerView {

    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     *
     */
    protected void showComputersList() {
        ArrayList<Computer> computerList = ComputerService.INSTANCE.getComputerList();
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
            Computer computer = ComputerService.INSTANCE.getComputerById(Long.valueOf(number));
            System.out.println("Computer found:" + computer.toString());
            return; // this will escape the while loop
        } catch (Exception e) {
            System.out.println("No computer has this id. Try again.");
            return;
        }
    }

    /**
     *
     */
    @SuppressWarnings("resource")
    // TODO Manage scanner leak
    protected void createComputer() {
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

        Long key = ComputerService.INSTANCE.createComputer(c);
        Computer addedCompany = ComputerService.INSTANCE.getComputerById(key);
        System.out.println(addedCompany.toString());
    }

    /**
     *
     */
    @SuppressWarnings("resource")
    // TODO Manage scanner leak
    protected void updateComputer() {
        Scanner keyboardID = new Scanner(System.in);
        System.out.println("Which computer do you want to modify ?");
        int numberID;
        Computer computer;
        try {
            numberID = Integer.parseInt(keyboardID.nextLine());
            computer = ComputerService.INSTANCE.getComputerById(Long.valueOf(numberID));
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
        ComputerService.INSTANCE.updateComputer(Long.valueOf(numberID), c1);
        System.out.println(ComputerService.INSTANCE.getComputerById(Long.valueOf(numberID)).toString());
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
            computerDelete = ComputerService.INSTANCE.getComputerById(Long.valueOf(numberDelete));
            System.out.println("This is the computer you want to delete");
            System.out.println(computerDelete.toString());
            // TODO Confirm deletion
            ComputerService.INSTANCE.deleteComputer(Long.valueOf(numberDelete));
        } catch (Exception e) {
            System.out.println("That is not a valid id. Try again.");
            return;
        }
    }

    /**
     *
     */
    protected void showComputersListPageCallingAll() {
        Page<Computer> pageComputers = new Page<>(
                ComputerService.INSTANCE.getComputerList());
        System.out.println(
                "There are currently " + pageComputers.getNumPage() + " pages");
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
     *
     */
    public void showComputersListPage() {
        Scanner keyboardShowPage = new Scanner(System.in);
        System.out.print("Which page do you want to list ? ");
        int pageNb = keyboardShowPage.nextInt();

        long idBegin = pageNb * Page.elementsByPage;
        long idEnd = pageNb * Page.elementsByPage + Page.elementsByPage;
        System.out.println(idBegin);
        System.out.println(idEnd);

        Page<Computer> pageComputers = new Page<>(
                ComputerService.INSTANCE.getComputerInRange(idBegin, idEnd));
        List<Computer> newPages = pageComputers.getPage((int) idBegin,
                (int) idEnd);
        for (Computer c : newPages) {
            System.out.println("Ha" + c.toString());
        }
    }
}
