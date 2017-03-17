package main.java.com.excilys.computerdatabase.model;

/**
 * @author Vitalie SOVA
 *
 */
public abstract class Entity implements IEntity {

    protected Long id;
    protected String name;

    protected Long getId() {
        return id;
    }

    protected void setComputerId(Long id) {
        this.id = id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}