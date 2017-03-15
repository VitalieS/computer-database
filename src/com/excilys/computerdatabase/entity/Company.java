package com.excilys.computerdatabase.entity;

public class Company {

	private Long companyId;
	
	private String companyName;

	public Company(Long companyId, final String companyName) {
		this.companyId = companyId;
		this.companyName = companyName;
	}

	/**
	 * @return companyId
	 */
	public Long getID() {
		return companyId;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return companyName;
	}

	/**
	 * @param compaanyId
	 */
	public void setId(Long companyId) {
		this.companyId = companyId;
	}
	
	/**
	 * @param name
	 */
	public void setName(String companyName) {
		this.companyName = companyName;
	}

	public void showInfo() {
		System.out.println("Company [id = " + companyId + ", name = " + companyName + "]");
		//System.out.println("Company Info :\n- Id : " + companyId + "\n- Name : " + companyName);
	}
}