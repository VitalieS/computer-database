package main.java.com.excilys.computerdatabase.model;

import java.util.List;

/**
 * @author Vitalie SOVA
 *
 */
public class Page<E> {
    private List<E> pageObject;
    private int currentPage = 1;

    public static int elementsByPage = 10;

    /**
     * @param newList - A list
     */
    public Page(List<E> newList) {
        pageObject = newList;
    }

    /**
     * @return - The number of pages
     */
    public int getNbPage() {
        return pageObject.size() / elementsByPage;
    }

    /**
     * @param iStart - The id of the first item
     * @param iEnd - The id of the last computer
     * @return - The sublist between the parameters
     */
    public List<E> getPage(int iStart, int iEnd) {
        return pageObject.subList(iStart, iEnd);
    }

    /**
     * @param idPage - The id of the page
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
        if (currentPage < getNbPage()) {
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
}
