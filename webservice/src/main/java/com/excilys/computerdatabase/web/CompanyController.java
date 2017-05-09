package com.excilys.computerdatabase.web;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.dto.CompanyDTO;
import com.excilys.computerdatabase.persistence.mappers.ObjectMappers;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
/*
	public void setCompanyService(CompanyService computerService) {
		this.companyService = computerService;
	}
*/	
	@GetMapping("/companies")
	@ResponseBody
	public List<Company> listCompanies() {
		return companyService.getCompaniesList();
	}

	@GetMapping("/companies/{id}")
	@ResponseBody
	public CompanyDTO getCompany(@PathVariable("id") long id) {
		return ObjectMappers.mapper(companyService.getCompanyById(id));

	}

	@DeleteMapping("/companies/{id}")
	@ResponseBody
	public void deleteCompany(@PathVariable Long id) {
		companyService.delete(id);
	}
}