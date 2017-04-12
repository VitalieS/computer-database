package com.excilys.computerdatabase.model.entities;

import java.util.List;

/**
 * @author Vitalie SOVA
 */
public class Page<E> {
    private List<E> myPage;
    public static int elementsByPage = 10;
    private int currentPage = 0;

    /**
     * @param newList - A list
     */
    public Page(List<E> newList) {
        myPage = newList;
    }

    /**
     * @param iStart - The id of the first item
     * @param iEnd - The id of the last item
     * @return - The sublist of items between the given parameters
     */

    public List<E> getPage(int iStart, int iEnd) {
        return myPage.subList(iStart, iEnd);
    }

    /**
     * @param idPage - The id of the page
     * @return - The sublist of the page
     */
    public List<E> getPageInRange(int idPage) {
        int iStart = idPage * elementsByPage;
        int iEnd = idPage * elementsByPage + elementsByPage;
        if (iEnd > myPage.size()) {
            iEnd = myPage.size();
        }
        currentPage = idPage;
        return myPage.subList(iStart, iEnd);
    }

    /**
     * @return - The sublist of the next page
     */
    public List<E> getNextPage() {
        if (currentPage < getNumPage()) {
            currentPage++;
        } else {
            System.out.println("That's the last page");
        }
        return getPageInRange(currentPage);
    }

    /**
     * @return - The sublist of the previous page
     */
    public List<E> getPrevPage() {
        if (currentPage > 0) {
            currentPage--;
        } else {
            System.out.println("That's the first page");
        }
        return getPageInRange(currentPage);
    }

    /**
     * @return - The number of pages
     */
    public int getNumPage() {
        return myPage.size() / elementsByPage;
    }

    public enum SortingBy {
        ID("id"),
        NAME("name"),
        INTRODUCED("introduced"),
        DISCONTINUED("discontinued"),
        COMPANY_NAME("company_name");

        private final String text;

        SortingBy(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static SortingBy getSort(String sort) {
            if (sort != null && !sort.trim().isEmpty()) {
                switch (sort) {
                    case "id":
                        return SortingBy.ID;
                    case "name":
                        return SortingBy.NAME;
                    case "introduced":
                        return SortingBy.INTRODUCED;
                    case "discontinued":
                        return SortingBy.DISCONTINUED;
                    case "companyName":
                        return SortingBy.COMPANY_NAME;
                }
            }
            return null;
        }
    }
}
