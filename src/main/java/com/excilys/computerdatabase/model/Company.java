package com.excilys.computerdatabase.model;

/**
 * @author Vitalie SOVA
 */
public class Company {

    private Long companyId;
    private String companyName;

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
        Company other = (Company) obj;
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
        public Company build() {
            return new Company(this);
        }
    }

    /**
     * @param builder - The ComputerBuilder
     */
    private Company(CompanyBuilder builder) {
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
    }
}