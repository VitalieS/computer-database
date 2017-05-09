package com.excilys.computerdatabase.presentation.cliui;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ObjectMappers;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * @author Vitalie SOVA
 *
 */
@Service("computerView")
public class ComputerView {

    @Autowired
    private ComputerService computerService;

    /**
     *
     */
    protected void showComputersList() {
        List<Computer> computerList = computerService.getComputerList();
        System.out.println("View computer list" + computerList);
        for (Computer c : computerList) {
            System.out.println(c.toString());
        }
    }

    /**
     *
     */
    protected void showComputersDetails() {
        Scanner keyboardComputerId = new Scanner(System.in);
        System.out.println("What's your computer ID?");
        int number;
        try {
            number = Integer.parseInt(keyboardComputerId.nextLine());
            Computer computer = computerService.getComputerById(Long.valueOf(number));
            System.out.println("Computer found:" + computer.toString());
            //keyboardComputerId.close();
            return;
        } catch (Exception e) {
            System.out.println("No computer has this id. Try again.");
            return;
        }
    }

    /**
     *
     */
    protected void createComputer() {
        Scanner keyboardNewComputername = new Scanner(System.in);
        System.out.println("What's the new computer name?");
        String newName = keyboardNewComputername.nextLine();
        ComputerDTO c = new ComputerDTO.ComputerBuilder("New Computer").build();
        if (newName != null && !newName.trim().isEmpty()) {
            c.setComputerName(newName);
        } else {
            System.out.println("The computer can't have empty name field");
            keyboardNewComputername.close();
            return;
        }

        Scanner keyboardNewComputerIDate = new Scanner(System.in);
        System.out.println("What's the new computer iDate? [yyyy-mm-dd]");
        String newIDate = keyboardNewComputerIDate.nextLine();
        if (newIDate != null && !newIDate.trim().isEmpty()) {
            c.setIntroducedDate(newIDate);
        }

        Scanner keyboardNewComputerDDate = new Scanner(System.in);
        System.out.println("What's the new computer dDate? [yyyy-mm-dd]");
        String newDDate = keyboardNewComputerDDate.nextLine();
        if (newDDate != null && !newDDate.trim().isEmpty()) {
            c.setIntroducedDate(newDDate);
        }

        Scanner keyboardNewComputerManufacturer = new Scanner(System.in);
        System.out.println("What's the new computer manufacturer");
        String newManufacturer = keyboardNewComputerManufacturer.nextLine();
        if (newManufacturer != null && !newManufacturer.trim().isEmpty()) {
            c.setCompanyId(Long.valueOf(newManufacturer));
        }

        System.out.println(c.toString());
        Computer coo = ObjectMappers.mapper(c);
        Long key = computerService.createComputer(coo);
        Computer addedCompany = computerService.getComputerById(key);
        System.out.println(addedCompany.toString());

        //keyboardNewComputername.close();
        //keyboardNewComputerIDate.close();
        //keyboardNewComputerDDate.close();
        //keyboardNewComputerManufacturer.close();
    }

    /**
     *
     */
    protected void updateComputer() {
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
            keyboardID.close();
            return;
        }

        ComputerDTO c1 = new ComputerDTO.ComputerBuilder("New Computer").build();
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
            c1.setIntroducedDate(newIDate1);
        } else {
            c1.setIntroducedDate(computer.getIntroducedDate().toString());
        }

        Scanner keyboardNewComputerDDate1 = new Scanner(System.in);
        System.out.println("What's the new computer dDate? [dd/mm/yyyy]");
        String newDDate1 = keyboardNewComputerDDate1.nextLine();
        if (newDDate1 != null && !newDDate1.trim().isEmpty()) {
            c1.setDiscontinuedDate(newDDate1);
        } else {
            c1.setDiscontinuedDate(computer.getDiscontinuedDate().toString());
        }

        Scanner keyboardNewComputerManufacturer1 = new Scanner(System.in);
        System.out.println("What's the new computer manufacturer");
        String newmanufacturer1 = keyboardNewComputerManufacturer1.nextLine();
        if (newmanufacturer1 != null && !newmanufacturer1.trim().isEmpty()) {
            c1.setCompanyId(Long.valueOf(newmanufacturer1));
        } else {
            c1.setCompanyId(computer.getCompany().getCompanyId());
        }

        System.out.println(c1.toString());
        computerService.updateComputer(ObjectMappers.mapper(c1));
        System.out.println(computerService.getComputerById(Long.valueOf(numberID)).toString());

        //keyboardID.close();
        //keyboardNewComputername1.close();
        //keyboardNewComputerIDate1.close();
        //keyboardNewComputerDDate1.close();
        //keyboardNewComputerManufacturer1.close();
    }

    /**
     *
     */
    protected void deleteComputer() {
        Scanner keyboardDelete = new Scanner(System.in);
        System.out.println("Which computer do you want to delete ?");
        Computer computerDelete;
        int numberDelete;
        try {
            numberDelete = Integer.parseInt(keyboardDelete.nextLine());
            computerDelete = computerService.getComputerById(Long.valueOf(numberDelete));
            System.out.println("This is the computer you want to delete");
            System.out.println(computerDelete.toString());
            computerService.deleteComputer(Long.valueOf(numberDelete));
        } catch (Exception e) {
            System.out.println("That is not a valid id. Try again.");
            keyboardDelete.close();
            return;
        }
        //keyboardDelete.close();
    }

    /**
     *
     */
    protected void showComputersListPageCallingAll() {
        Page<Computer> pageComputers = new Page<>(computerService.getComputerList());
        System.out.println("There are currently " + pageComputers.getNumPage() + " pages");
        Scanner keyboardShowPage = new Scanner(System.in);
        System.out.print("Which page do you want to list ? ");
        int pageNb = keyboardShowPage.nextInt();

        List<Computer> newPage = pageComputers.getPageInRange(pageNb);
        for (Computer c : newPage) {
            System.out.println(c.toString());
        }
        //keyboardShowPage.close();
        while (true) {
            Scanner seeNext = new Scanner(System.in);
            System.out.print("Write 0 if you want to go to the previous page or 1 if you want to go to the next one. \n");
            int newpageNb = seeNext.nextInt();
            seeNext.close();
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
        //long idEnd = pageNb * Page.elementsByPage + Page.elementsByPage;
        long idEnd = Page.elementsByPage;
        System.out.println(idBegin);
        System.out.println(idEnd);

        Page<ComputerDTO> pageComputers = new Page<>(computerService.getComputerInRange(idBegin, idEnd));
        List<ComputerDTO> newPages = pageComputers.getPage((int) idBegin, (int) idEnd);
        for (ComputerDTO c : newPages) {
            System.out.println(c.toString());
        }
        //keyboardShowPage.close();
    }
}
