package test.java.com.test.excilys.computerdatabase.model.entities;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;

import main.java.com.excilys.computerdatabase.model.entities.Computer;

public class ComputerTest {

    /**
     * Test - Creation of Computer Objects
     *
     * @throws SQLException
     */
    @Test
    public void testCreateComputerObject() throws SQLException {
        // Bean pattern
        Computer c1 = new Computer("Test Create Bean");
        c1.setComputerId(Long.valueOf(5));
        c1.setCompanyId(Long.valueOf(8));
        assertNotNull(c1);

        // Telescope pattern
        Computer c2 = new Computer("Test Create Telescope Computer", null, null,
                Long.valueOf(5));
        assertNotNull(c2);

        // Builder pattern
        Computer c3 = new Computer.ComputerBuilder()
                .name("Test Create Build Computer").id(Long.valueOf(5)).build();
        assertNotNull(c3);

    }
}
