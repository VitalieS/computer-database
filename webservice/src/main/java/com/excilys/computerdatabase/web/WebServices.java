package com.excilys.computerdatabase.web;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.excilys.computerdatabase.persistence.dto.CompanyDTO;
import com.excilys.computerdatabase.persistence.dto.ComputerDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum WebServices {

	INSTANCE;
	
	private static final int NR_PER_PAGE = 50;
	private static final String URL = "http://localhost:8080/computerdatabase";

	public ComputerDTO getComputerById(long id) {
		WebTarget webTarget = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = webTarget.path("computers/" + String.valueOf(id)).request(MediaType.APPLICATION_JSON);
		return invocationBuilder.get(ComputerDTO.class);
	}

	public List<ComputerDTO> getComputersList() {
		WebTarget target = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = target.path("computers").queryParam("page")
				.queryParam("limit", NR_PER_PAGE).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() != 200) {
			System.out.println(response.getStatus());
			return null;
		} else {
			try {
				return new ObjectMapper().readValue(response.readEntity(String.class),
						new TypeReference<List<ComputerDTO>>() {
						});
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
	}

	public List<CompanyDTO> listCompanies() {
		WebTarget target = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = target.path("companies/").request(MediaType.APPLICATION_JSON);
		try {
			Response response = invocationBuilder.get();
			return new ObjectMapper().readValue(response.readEntity(String.class),
					new TypeReference<List<CompanyDTO>>() {
					});
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void createComputer(ComputerDTO ComputerDTO) {
		WebTarget target = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = target.path("computers").request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(ComputerDTO, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			System.out.println(response.getStatus());
		}
	}

	public void updateComputer(ComputerDTO ComputerDTO) {
		WebTarget target = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = target.path("computers").request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(ComputerDTO, MediaType.APPLICATION_JSON));
		if (response.getStatus() != 200) {
			System.out.println(response.getStatus());
		}
	}

	public void deleteComputer(long id) {
		WebTarget target = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = target.path("computers/" + String.valueOf(id))
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();
		if (response.getStatus() != 200) {
			System.out.println(response.getStatus());
		}
	}

	public void deleteCompany(long id) {
		WebTarget target = ClientBuilder.newClient().target(URL);
		Invocation.Builder invocationBuilder = target.path("companies/" + String.valueOf(id))
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();
	}
}