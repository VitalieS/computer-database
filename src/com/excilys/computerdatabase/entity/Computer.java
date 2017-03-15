package com.excilys.computerdatabase.entity;

import java.time.LocalDate;

import com.mysql.jdbc.StringUtils;

public class Computer {

	private Long computerId; // optional
	private String computerName; // required
	private LocalDate introducedDate; // optional
	private LocalDate discontinuedDate; // optional
	private Long manufacturerId; // optional - can't use Company while we can't
									// map objects

	public Computer(final String computerName) {
		super();
		this.computerName = computerName;
	}

	public Computer(long computerId, String computerName, LocalDate introducedDate, LocalDate discontinuedDate,
			long manufacturerId) {
		super();
		this.computerId = computerId;
		this.computerName = computerName;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = manufacturerId;
	}

	/**
	 * @return id
	 */
	public Long getComputerId() {
		return this.computerId;
	}

	/**
	 * @return computerName
	 */
	public String getComputerName() {
		return this.computerName;
	}

	/**
	 * @return introducedDate
	 */
	public LocalDate getIntroducedDate() {
		return this.introducedDate;
	}

	/**
	 * @return discontinuedDate
	 */
	public LocalDate getDiscontinuedDate() {
		return this.discontinuedDate;
	}

	/**
	 * @return manufacturerComputerName
	 */
	public Long getManufacturerId() {
		return this.manufacturerId;
	}

	/**
	 * @param id
	 */
	public void setId(Long computerId) {
		this.computerId = computerId;
	}

	/**
	 * @param computerName
	 */
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	/**
	 * @param introducedDate
	 */
	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	/**
	 * @param discontinuedDate
	 */
	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	/**
	 * @param manufacturer
	 */
	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public void showInfo() {
		System.out.println("Computer [id = " + this.computerId + ", name = " + this.computerName + ", introduced = " + this.introducedDate + ", discontinued = "
				+ this.discontinuedDate + ", company = " + this.manufacturerId + "]");
		/* System.out.println("Computer Info :\n- Id : " + computerId + "\n- Name : " + computerName + "\n- iDate : "
				+ introducedDate + "\n- dDate : " + discontinuedDate + "\n- Manufacturer : " + manufacturerId);*/
	}
	
	public String toString() {
		return "Computer Info :\n- Id : " + computerId + "\n- Name : " + computerName + "\n- iDate : "
				+ introducedDate + "\n- dDate : " + discontinuedDate + "\n- Manufacturer : " + manufacturerId;
	}
	
	 public boolean equals(Object obj) {
		    if (this == obj)
		      return true;
		    
		    if (obj == null)
		      return false;
		    
		    if (getClass() != obj.getClass())
		      return false;
		    
		    Computer other = (Computer) obj;
		    
		    if (discontinuedDate == null) {
		      if (other.discontinuedDate != null)
		        return false;
		    } else if (!discontinuedDate.equals(other.discontinuedDate))
		      return false;
		    
		    if (introducedDate == null) {
		      if (other.introducedDate != null)
		        return false;
		    } else if (!introducedDate.equals(other.introducedDate))
		      return false;
		    
		    if (computerId != other.computerId)
		      return false;
		    
		    /*
		    if (company == null) {
		      if (other.company != null)
		        return false;
		    } else if (!company.equals(other.company))
		      return false;
		    */
		    
		    if (computerName == null) {
		      if (other.computerName != null)
		        return false;
		    } else if (!computerName.equals(other.computerName))
		      return false;
		    
		    return true;
		  }
	 

	public static class ComputerBuilder {

		private Long computerId;
		private String computerName;
		private LocalDate introducedDate;
		private LocalDate discontinuedDate;
		private Long manufacturerId;

		public ComputerBuilder(String computerName) {
			this.computerName = computerName;
		}

		public ComputerBuilder id(Long computerId) {
			this.computerId = computerId;
			return this;
		}

		public ComputerBuilder name(String computerName) {
			this.computerName = computerName;
			return this;
		}


		public ComputerBuilder introducedDate(LocalDate introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public ComputerBuilder discontinuedDate(LocalDate discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}

		public ComputerBuilder company(Long manufacturerId) {
			this.manufacturerId = manufacturerId;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}
	
	private Computer(ComputerBuilder builder) {
		this.computerId = builder.computerId;
		this.computerName = builder.computerName;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.manufacturerId = builder.manufacturerId;
	}
}
