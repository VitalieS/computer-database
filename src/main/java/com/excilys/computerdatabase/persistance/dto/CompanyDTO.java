package com.excilys.computerdatabase.persistance.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the object version of a Company from the Database.
 *
 * @author Vitalie SOVA
 */
public class CompanyDTO {

    private Long companyId;
    private String companyName;

    static final Logger LOG = LoggerFactory.getLogger(ComputerDTO.class);

    /**
     * Instantiates a new company.
     *
     * @param companyId - The company id
     * @param companyName - The company name
     */
    public CompanyDTO(Long companyId, final String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Gets companyId.
     *
     * @return companyId - The company id
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Gets companyName.
     *
     * @return companyName - The company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets companyId.
     *
     * @param companyId - The company id
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Sets companyName.
     *
     * @param companyName - The company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Company [id = " + companyId + ", name = " + companyName + "]";
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
        CompanyDTO other = (CompanyDTO) obj;
        if (companyId == null) {
            if (other.companyId != null) {
                return false;
            }
        } else if (!companyId.equals(other.companyId)) {
            return false;
        }
        if (companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!companyName.equals(other.companyName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (companyId ^ (companyId >>> 32));
        return result;
    }

    /**
     * Builder pattern to instantiate a computer.
     *
     * @return The Company
     */

    public static class CompanyBuilder {
        private Long companyId;
        private String companyName;

        /**
         * @param computerId - The computer id
         * @return The computer with the id
         */
        public CompanyBuilder id(Long computerId) {
            this.companyId = computerId;
            return this;
        }

        /**
         * @param computerName - The computer name
         * @return The computer with the name
         */
        public CompanyBuilder name(String computerName) {
            this.companyName = computerName;
            return this;
        }

        /**
         * @return The computer
         */
        public CompanyDTO build() {
            return new CompanyDTO(this);
        }
    }

    public static CompanyBuilder builder() {
        return new CompanyDTO.CompanyBuilder();
    }

    /**
     * @param builder - The ComputerBuilder
     */
    private CompanyDTO(CompanyBuilder builder) {
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
    }
}