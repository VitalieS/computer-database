package com.excilys.computerdatabase.presentation.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.persistance.dto.ComputerDTO;
import com.excilys.computerdatabase.persistance.mappers.PagesMapper;
import com.excilys.computerdatabase.persistance.mappers.ServletMapper;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.Validate;

/**
 * Servlet implementation class EditComputer.
 */
public class EditComputer extends HttpServlet {

    private static final long serialVersionUID = 5322583920825901807L;

    public static final String VUE = "/WEB-INF/views/editComputer.jsp";

    static final Logger LOG = LoggerFactory.getLogger(EditComputer.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET method called on " + this.getClass().getSimpleName());
        PagesMapper.fromEdit(request);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("POST method called on " + this.getClass().getSimpleName());
        ComputerDTO computer = ServletMapper.mapperToDTO(request, PagesMapper.fromAdd(request));
        Map<String, String> errors = Validate.validate(computer);
        if (!errors.isEmpty()) {
            LOG.debug("There are errors, launched viewcomputer view again with errors.");
            request.setAttribute("exception", errors.toString());
            doGet(request, response);
            return;
        }
        ComputerService.INSTANCE.updateComputer(computer.getComputerId(), computer);
        response.sendRedirect(getServletContext().getContextPath() + "/dashboard");
    }
}