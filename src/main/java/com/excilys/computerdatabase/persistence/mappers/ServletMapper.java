package com.excilys.computerdatabase.persistence.mappers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;

public class ServletMapper {

    private static final Logger LOG = LoggerFactory.getLogger(ServletMapper.class);

    public static ComputerDTO mapperToDTO(HttpServletRequest request, List<Company> list) {
        String computerId = request.getParameter("computerId");
        Long id = (computerId == null || computerId.trim().isEmpty() ? null : Long.parseLong(computerId.trim()));
        String computerName = request.getParameter("computerName");
        String name = (computerName == null || computerName.trim().isEmpty() ? null : computerName.trim());
        String introducedDate = request.getParameter("introducedDate");
        String introduced = (introducedDate == null || introducedDate.trim().isEmpty() ? null : introducedDate.trim());
        String discontinuedDate = request.getParameter("discontinuedDate");
        String discontinued = (discontinuedDate == null || discontinuedDate.trim().isEmpty() ? null  : discontinuedDate.trim());
        String companyId = request.getParameter("companyId");
        Long companyIdl = (companyId == null || companyId.trim().isEmpty() ? null : Long.parseLong(companyId.trim()));
        String companyName = (companyId == null ? null : list.get(Integer.parseInt(companyId.trim())).getCompanyName());
        LOG.info("Trying to add: " + computerId + " " + computerName + " " + introducedDate + " " + discontinuedDate + " " + companyId);
        return new ComputerDTO.ComputerBuilder(name).id(id).introducedDate(introduced).discontinuedDate(discontinued)
                .companyId(companyIdl).companyName(companyName).build();
    }

    public static ComputerDTO mapperToDTO(String computerId1, String computerName1, String introducedDate1, String discontinuedDate1, String companyId1, List<Company> list) {
        String computerId = computerId1;
        Long id = (computerId == null || computerId.trim().isEmpty() ? null : Long.parseLong(computerId.trim()));
        String computerName = computerName1;
        String name = (computerName == null || computerName.trim().isEmpty() ? null : computerName.trim());
        String introducedDate = introducedDate1;
        String introduced = (introducedDate == null || introducedDate.trim().isEmpty() ? null : introducedDate.trim());
        String discontinuedDate = discontinuedDate1;
        String discontinued = (discontinuedDate == null || discontinuedDate.trim().isEmpty() ? null  : discontinuedDate.trim());
        String companyId = companyId1;
        Long companyIdl = (companyId == null || companyId.trim().isEmpty() ? null : Long.parseLong(companyId.trim()));
        String companyName = (companyId == null ? null : list.get(Integer.parseInt(companyId.trim())).getCompanyName());
        LOG.info("Trying to add: " + computerId + " " + computerName + " " + introducedDate + " " + discontinuedDate + " " + companyId);
        return new ComputerDTO.ComputerBuilder(name).id(id).introducedDate(introduced).discontinuedDate(discontinued)
                .companyId(companyIdl).companyName(companyName).build();
    }
}
