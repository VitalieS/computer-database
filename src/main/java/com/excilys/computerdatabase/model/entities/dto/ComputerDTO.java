package com.excilys.computerdatabase.model.entities.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Vitalie SOVA
 */
public class ComputerDTO {

    private Long computerId; // optional
    private String computerName; // required
    private LocalDate introducedDate; // optional
    private LocalDate discontinuedDate; // optional
    private CompanyDTO company;

    /**
     * Instantiates a new Computer.
     */
    public ComputerDTO() {
        super();
    }

    /**
     * Instantiates a new Computer.
     *
     * @param computerName - The computer name
     */
    public ComputerDTO(String computerName) {
        this.computerName = computerName;
    }

    /**
     * Instantiates a new Computer.
     *
     * @param computerName - The computer name
     * @param introducedDate - The introduced date
     * @param discontinuedDate - The discontinued date
     * @param companyId
     *            - The company id
     */
    public ComputerDTO(String computerName, LocalDate introducedDate,
            LocalDate discontinuedDate, CompanyDTO company) {
        this.computerName = computerName;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.company = company;
    }

    /**
     * Gets computerId.
     *
     * @return computerId - The company id
     */
    public Long getComputerId() {
        return this.computerId;
    }

    /**
     * Gets computerName.
     *
     * @return computerName - The computer name
     */
    public String getComputerName() {
        return this.computerName;
    }

    /**
     * Gets introducedDate.
     *
     * @return introducedDate - The introduced date
     */
    public LocalDate getIntroducedDate() {
        return this.introducedDate;
    }

    /**
     * Gets discontinuedDate.
     *
     * @return discontinuedDate - The discontinued date
     */
    public LocalDate getDiscontinuedDate() {
        return this.discontinuedDate;
    }

    /**
     * Gets companyId.
     *
     * @return companyId - The company id
     */
    public CompanyDTO getCompany() {
        return this.company;
    }

    /**
     * Sets computerId.
     *
     * @param computerId
     *            - The computer id
     */
    public void setComputerId(Long computerId) {
        this.computerId = computerId;
    }

    /**
     * Sets computerName.
     *
     * @param computerName
     *            - The computer name
     */
    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    /**
     * Sets introducedDate.
     *
     * @param introducedDate
     *            - The introduced date
     */
    public void setIntroducedDate(LocalDate introducedDate) {
        this.introducedDate = introducedDate;
    }

    /**
     * Sets discontinuedDate.
     *
     * @param discontinuedDate
     *            - the discontinued date
     */
    public void setDiscontinuedDate(LocalDate discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    /**
     * Sets companyId.
     *
     * @param companyId
     *            - the company id
     */
    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Computer [id = " + this.computerId + ", name = "
                + this.computerName + ", introduced = " + this.introducedDate
                + ", discontinued = " + this.discontinuedDate + ", company = "
                + this.company.getId() + this.company.getName() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ComputerDTO other = (ComputerDTO) obj;
        if (discontinuedDate == null) {
            if (other.discontinuedDate != null) {
                return false;
            }
        } else if (!discontinuedDate.equals(other.discontinuedDate)) {
            return false;
        }
        if (introducedDate == null) {
            if (other.introducedDate != null) {
                return false;
            }
        } else if (!introducedDate.equals(other.introducedDate)) {
            return false;
        }
        if (computerId != other.computerId) {
            return false;
        }
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (computerName == null) {
            if (other.computerName != null) {
                return false;
            }
        } else if (!computerName.equals(other.computerName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (computerId ^ (computerId >>> 32));
        return result;
    }

    /**
     * Builder pattern to instantiate a computer.
     *
     * @return The Computer
     */

    public static class ComputerBuilder {
        private Long computerId;
        private String computerName;
        private LocalDate introducedDate;
        private LocalDate discontinuedDate;
        private CompanyDTO company;

        /**
         * @param computerId - The computer id
         * @return The computer with the id
         */
        public ComputerBuilder id(Long computerId) {
            this.computerId = computerId;
            return this;
        }

        /**
         * @param computerName - The computer name
         * @return The computer with the name
         */
        public ComputerBuilder name(String computerName) {
            this.computerName = computerName;
            return this;
        }

        /**
         * @param introducedDate - The introduced date
         * @return The computer with the introduced date
         */
        public ComputerBuilder introducedDate(LocalDate introducedDate) {
            this.introducedDate = introducedDate;
            return this;
        }

        /**
         * @param discontinuedDate - The discontinued date
         * @return The computer with the discontinued date
         */
        public ComputerBuilder discontinuedDate(LocalDate discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }

        /**
         * @param introducedDate - The introduced date
         * @return The computer with the introduced date
         */
        public ComputerBuilder introducedDate(String introducedDate) {
            DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate introducedDateLD = LocalDate.parse(introducedDate, form);
            this.introducedDate = introducedDateLD;
            return this;
        }

        /**
         * @param discontinuedDate - The discontinued date
         * @return The computer with the discontinued date
         */
        public ComputerBuilder discontinuedDate(String discontinuedDate) {
            DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate discontinuedDateLD = LocalDate.parse(discontinuedDate, form);
            this.discontinuedDate = discontinuedDateLD;
            return this;
        }

        /**
         * @param companyId - The company id
         * @return The computer with the company id
         */
        public ComputerBuilder company(CompanyDTO company) {
            this.company = company;
            return this;
        }

        /**
         * @return The computer
         */
        public ComputerDTO build() {
            return new ComputerDTO(this);
        }
    }

    /**
     * @param builder - The ComputerBuilder
     */
    private ComputerDTO(ComputerBuilder builder) {
        this.computerId = builder.computerId;
        this.computerName = builder.computerName;
        this.introducedDate = builder.introducedDate;
        this.discontinuedDate = builder.discontinuedDate;
        this.company = builder.company;
    }

}
