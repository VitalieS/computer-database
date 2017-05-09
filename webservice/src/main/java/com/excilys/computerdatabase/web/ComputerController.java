package com.excilys.computerdatabase.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.model.Page.SortingBy;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.excilys.computerdatabase.persistence.mappers.ObjectMappers;
import com.excilys.computerdatabase.service.ComputerService;

@RestController
public class ComputerController {
	@Autowired
	private ComputerService computerService;
/*
	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}
*/
	@RequestMapping(value = "/computers", method = RequestMethod.GET)
	@ResponseBody
	public List<ComputerDTO> listComputers(@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "submit", required = false) String submit,
			@RequestParam(value = "sort", required = false) String search,
			@RequestParam(value = "search", required = false) String sort) {
		int numPage = (pageNumber != null) ? Integer.parseInt(pageNumber) : 1;
		int limit = (submit != null) ? Integer.parseInt(submit) : 50;
		int startElementNumber = (numPage - 1) * limit;
		return computerService.getComputerInRangeNb(Long.valueOf(startElementNumber), limit,
				Page.SortingBy.getSort(sort), search);
	}

	@RequestMapping(value = "/computers/count", method = RequestMethod.GET)
	@ResponseBody
	public int getCountOfAllComputers() {
		return computerService.getNumberOfComputers();
	}
	
	@RequestMapping(value = "/computers/pages/count", method = RequestMethod.GET)
	@ResponseBody
	public int getNumberOfPagesComputers() {
		return computerService.getNumberOfPages(50);
	}

	@RequestMapping(value = "/computers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ComputerDTO getComputerById(@PathVariable("id") long id) {
		return ObjectMappers.mapper(computerService.getComputerById(id));
	}

	@RequestMapping(value = "/computers/create", method = RequestMethod.POST)
	@ResponseBody
	public void createComputer(@Valid @RequestBody ComputerDTO ComputerDTO, BindingResult result) {
		Computer computer = ObjectMappers.mapper(ComputerDTO);
		computerService.createComputer(computer);
	}

	@RequestMapping(value = "/computers/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteComputer(@PathVariable Long id) {
		computerService.deleteComputer(id);
	}

	@RequestMapping(value = "/computers/update", method = RequestMethod.PUT)
	@ResponseBody
	public void updateComputer(@Valid @RequestBody ComputerDTO ComputerDTO, BindingResult result) {
		Computer computer = ObjectMappers.mapper(ComputerDTO);
		computerService.updateComputer(computer);
	}

}