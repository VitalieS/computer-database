package com.excilys.computerdatabase.model.entities;

import java.util.List;

/**
 * @author Vitalie SOVA
 *
 */
public class Page<E> {
    public static int numberPerPage = 50;
    private int pageNumber;

    private static int index = 0;

    public int getPageNumber() {
        return pageNumber;
    }

    public static int getNumberPerPage() {
        return numberPerPage;
    }

    /**
     * @return index index
     */
    public static int getIndex() {
        return index;
    }

    /**
     * @param index
     *            index
     */
    public static void setIndex(int in) {
        index = in;
    }

    /**
     */
    public static void next() {
        index = index + 1;
    }

    /**
     */
    public static void previous() {

        index = index - 1;

    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber;
        } else {
            throw new IllegalArgumentException("A page number must be positive");
        }
    }

    private int currentPage = 1;
    public static int elementsByPage = 50;

    private List<E> pageObject;

    /**
     * @param newList
     *            - A list
     */
    public Page(List<E> newList) {
        pageObject = newList;
    }

    /**
     * @return - The number of pages
     */
    public int getNumPage() {
        return pageObject.size() / elementsByPage;
    }

    /**
     * @return - The number of elements par page
     */
    public int getNbParPage() {
        return elementsByPage;
    }

    /**
     * @param iStart
     *            - The id of the first item
     * @param iEnd
     *            - The id of the last computer
     * @return - The sublist between the parameters
     */
    public List<E> getPage(int iStart, int iEnd) {
        return pageObject.subList(iStart, iEnd);
    }

    /**
     * @param idPage
     *            - The id of the page
     * @return - The sublist of the page
     */
    public List<E> getPageInRange(int idPage) {
        int iStart = idPage * elementsByPage;
        int iEnd = idPage * elementsByPage + elementsByPage;
        if (iEnd > pageObject.size()) {
            iEnd = pageObject.size();
        }
        System.out.println("Item from " + iStart + " to " + iEnd);
        currentPage = idPage;
        return pageObject.subList(iStart, iEnd);
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

    public static void setNumberPerPage(int parseInt) {
        numberPerPage = parseInt;
    }

}
