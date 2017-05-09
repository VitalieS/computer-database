package com.excilys.computerdatabase.web;

import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagLib extends SimpleTagSupport {
    private String uri;
    private int currentPage;
    private int maxPages;
    private int maxLinks = 10;

    private Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }

    @Override
    public void doTag() throws JspException {
        Writer out = getWriter();
        boolean lastPage = currentPage == maxPages;
        int pgStart = Math.max(currentPage - maxLinks / 2, 1);
        int pgEnd = pgStart + maxLinks;
        if (pgEnd > maxPages + 1) {
            int diff = pgEnd - maxPages;
            pgStart -= diff - 1;
            if (pgStart < 1) {
                pgStart = 1;
            }
            pgEnd = maxPages + 1;
        }
        try {
            out.write("<ul class=\"pagination\">");
            if (currentPage > 1) {
                out.write(constructLink(currentPage - 1, "&laquo;", "paginatorPrev"));
            }
            for (int i = pgStart; i < pgEnd; i++) {
                out.write(constructLink(i));
            }
            if (!lastPage) {
                out.write(constructLink(currentPage + 1, "&raquo;", "paginatorNext paginatorLast"));
            }
            out.write("</ul>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
    }

    private String constructLink(int page) {
        return constructLink(page, String.valueOf(page), null);
    }

    private String constructLink(int page, String text, String className) {
        StringBuilder link = new StringBuilder("<li");
        if (className != null) {
            link.append(" class=\"");
            link.append(className);
            link.append("\"");
        }
        link.append(">").append("<a href=\"").append(uri.replace("##", String.valueOf(page))).append("\">").append(text).append("</a></li>");
        return link.toString();
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setCurrentPage(int currPage) {
        this.currentPage = currPage;
    }

    public void setMaxPages(int totalPages) {
        this.maxPages = totalPages;
    }

    public void setMaxLinks(int maxLinks) {
        this.maxLinks = maxLinks;
    }
}