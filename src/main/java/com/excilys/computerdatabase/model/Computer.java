package main.java.com.excilys.computerdatabase.model;

import java.time.LocalDate;

/**
 * This class is the object version of a Computer from the Database.
 *
 * @author Vitalie SOVA
 */
public class Computer extends Entity {

    private Long computerId; // optional
    private String computerName; // required
    private LocalDate introducedDate; // optional
    private LocalDate discontinuedDate; // optional
    private Long companyId; // optional - can't use Company while we can't
    // map objects

    /**
     * Instantiates a new Computer.
     */
    public Computer() {

    }

    /**
     * Instantiates a new Computer.
     *
     * @param computerName
     *            - The computer name
     */
    public Computer(String computerName) {
        this.computerName = computerName;
    }

    /**
     * Instantiates a new Computer.
     *
     * @param computerName
     *            - The computer name
     * @param introducedDate
     *            - The introduced date
     * @param discontinuedDate
     *            - The discontinued date
     * @param companyId
     *            - The company id
     */
    public Computer(String computerName, LocalDate introducedDate,
            LocalDate discontinuedDate, Long companyId) {
        this.computerName = computerName;
        this.introducedDate = introducedDate;
        this.discontinuedDate = discontinuedDate;
        this.companyId = companyId;
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
    public Long getCompanyId() {
        return this.companyId;
    }

    /**
     * Sets computerId.
     *
     * @param computerId
     *            - The computer id
     */
    @Override
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
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Computer [id = " + this.computerId + ", name = "
                + this.computerName + ", introduced = " + this.introducedDate
                + ", discontinued = " + this.discontinuedDate + ", company = "
                + this.companyId + "]";
        /*
         * return "Computer Info :\n- Id : " + computerId + "\n- Name : " +
         * computerName + "\n- iDate : " + introducedDate + "\n- dDate : " +
         * discontinuedDate + "\n- Manufacturer : " + manufacturerId;
         */
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
        Computer other = (Computer) obj;
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
        private Long companyId;

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
         * @param companyId - The company id
         * @return The computer with the company id
         */
        public ComputerBuilder company(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        /**
         * @return The computer
         */
        public Computer build() {
            return new Computer(this);
        }
    }

    /**
     * @param builder - The ComputerBuilder
     */
    private Computer(ComputerBuilder builder) {
        this.computerId = builder.computerId;
        this.computerName = builder.computerName;
        this.introducedDate = builder.introducedDate;
        this.discontinuedDate = builder.discontinuedDate;
        this.companyId = builder.companyId;
    }

}
