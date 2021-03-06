package com.excilys.computerdatabase.persistence.dto;

/**
 * This class is the object version of a Computer from the Database.
 *
 * @author Vitalie SOVA
 */
public class ComputerDTO {

    private Long computerId;
    private String computerName;
    private String introducedDate;
    private String discontinuedDate;
    private Long companyId;
    private String companyName;

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
    public String getIntroducedDate() {
        return this.introducedDate;
    }

    /**
     * Gets discontinuedDate.
     *
     * @return discontinuedDate - The discontinued date
     */
    public String getDiscontinuedDate() {
        return this.discontinuedDate;
    }

    /**
     * Gets companyId.
     *
     * @return companyId - The company id
     */
    public Long getCompanyId() {
        return this.companyId;
    }

    /**
     * Gets companyName.
     *
     * @return companyName - The company name
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Sets computerId.
     *
     * @param computerId - The computer id
     */
    public void setComputerId(Long computerId) {
        this.computerId = computerId;
    }

    /**
     * Sets computerName.
     *
     * @param computerName - The computer name
     */
    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    /**
     * Sets introducedDate.
     *
     * @param introducedDate - The introduced date
     */
    public void setIntroducedDate(String introducedDate) {
        this.introducedDate = introducedDate;
    }

    /**
     * Sets discontinuedDate.
     *
     * @param discontinuedDate - The discontinued date
     */
    public void setDiscontinuedDate(String discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
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
        return "Computer [id = " + this.computerId + ", name = "
                + this.computerName + ", introduced = " + this.introducedDate
                + ", discontinued = " + this.discontinuedDate + ", companyId = "
                + this.companyId + ", companyName = " + this.companyName + "]";
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
        if (companyId == null) {
            if (other.companyId != null) {
                return false;
            }
        } else if (!companyId.equals(other.companyId)) {
            return false;
        }
        if (computerName == null) {
            if (other.computerName != null) {
                return false;
            }
        } else if (!computerName.equals(other.computerName)) {
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
        private String introducedDate;
        private String discontinuedDate;
        private Long companyId;
        private String companyName;

        /**
         * Builder constructor. The name of the computer is required.
         *
         * @param computerName - The name of the computer
         */
        public ComputerBuilder(String computerName) {
            this.computerName = computerName;
        }

        /**
         * @param computerId - The computer id
         * @return The computer with the id
         */
        public ComputerBuilder id(Long computerId) {
            this.computerId = computerId;
            return this;
        }

        /**
         * @param introducedDate - The introduced date
         * @return The computer with the introduced date
         */
        public ComputerBuilder introducedDate(String introducedDate) {
            this.introducedDate = introducedDate;
            return this;
        }

        /**
         * @param discontinuedDate - The discontinued date
         * @return The computer with the discontinued date
         */
        public ComputerBuilder discontinuedDate(String discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }

        /**
         * @param companyId - The company id
         * @return The computer with the company id
         */
        public ComputerBuilder companyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        /**
         * @param companyName - The company name
         * @return The computer with the company name
         */
        public ComputerBuilder companyName(String companyName) {
            this.companyName = companyName;
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
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
    }
}
