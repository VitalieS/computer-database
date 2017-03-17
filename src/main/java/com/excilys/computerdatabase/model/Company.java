package main.java.com.excilys.computerdatabase.model;

/**
 * This class is the object version of a Company from the Database.
 *
 * @author Vitalie SOVA
 */
public class Company {

    private Long companyId;
    private String companyName;

    /**
     * Instantiates a new company.
     *
     * @param companyId - The company id
     * @param companyName - The company name
     */
    public Company(Long companyId, final String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Gets companyId.
     *
     * @return companyId - The company id
     */
    public Long getId() {
        return companyId;
    }

    /**
     * Gets companyName.
     *
     * @return companyName - The company name
     */
    public String getName() {
        return companyName;
    }

    /**
     * Sets companyId.
     *
     * @param companyId - The company id
     */
    public void setId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Sets companyName.
     *
     * @param companyName - The company name
     */
    public void setName(String companyName) {
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
}