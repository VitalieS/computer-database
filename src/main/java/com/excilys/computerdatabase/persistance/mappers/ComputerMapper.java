package com.excilys.computerdatabase.persistance.mappers;

import java.time.LocalDate;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistance.dto.ComputerDTO;

public enum ComputerMapper {
    INSTANCE;

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

    public Computer mapper(ComputerDTO c_dto) {
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
}