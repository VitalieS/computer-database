package com.excilys.computerdatabase.persistence.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.dto.CompanyDTO;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;

public class ObjectMappers {

    public static ComputerDTO mapper(Computer c) {
        Long computerId = (c.getComputerId() == null ? null : c.getComputerId());
        String computerName = c.getComputerName();
        String introducedDate = (c.getIntroducedDate() == null ? null : c.getIntroducedDate().toString());
        String discontinuedDate = (c.getDiscontinuedDate() == null ? null : c.getDiscontinuedDate().toString());
        Long companyId = (c.getCompany() == null ? null : (c.getCompany().getCompanyId() == null ? null : c.getCompany().getCompanyId()));
        String companyName = (c.getCompany() == null ? null : (c.getCompany().getCompanyName() == null ? null : c.getCompany().getCompanyName()));
        ComputerDTO c_dto = new ComputerDTO.ComputerBuilder(computerName).id(computerId).introducedDate(introducedDate).discontinuedDate(discontinuedDate).companyId(companyId).companyName(companyName).build();
        return c_dto;
    }

    
    public static Computer mapper(ComputerDTO c_dto) {
        Long computerId = (c_dto.getComputerId() == null ? null : c_dto.getComputerId());
        String computerName = c_dto.getComputerName();
        LocalDate introducedDate = (c_dto.getIntroducedDate() == null ? null : LocalDate.parse(c_dto.getIntroducedDate()));
        LocalDate discontinuedDate = (c_dto.getDiscontinuedDate() == null ? null : LocalDate.parse(c_dto.getDiscontinuedDate()));
        Long companyId = c_dto.getCompanyId();
        String companyName = c_dto.getCompanyName();
        Company company = new Company.CompanyBuilder().id(companyId).name(companyName).build();
        Computer c = new Computer.ComputerBuilder(computerName).id(computerId).introducedDate(introducedDate).discontinuedDate(discontinuedDate).company(company).build();
        return c;
    }

    public static List<ComputerDTO> mapperComputers(List<Computer> computers) {
        List<ComputerDTO> computersEdit = new ArrayList<>();
        for (Computer c : computers) {
            computersEdit.add(mapper(c));
        }
        return computersEdit;
    }

    public static CompanyDTO mapper(Company c) {
        return new CompanyDTO.CompanyBuilder().id(c.getCompanyId()).name(c.getCompanyName()).build();
    }

    public static List<CompanyDTO> mapperCompanies(List<Company> companies) {
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(mapper(c));
        }
        return companiesDTO;
    }
}