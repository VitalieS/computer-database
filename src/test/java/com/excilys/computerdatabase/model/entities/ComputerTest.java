package com.excilys.computerdatabase.model.entities;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Test;

import com.excilys.computerdatabase.persistence.dto.ComputerDTO;

public class ComputerTest {

    /**
     * Test - Creation of Computer Objects
     *
     * @throws SQLException
     */
    @Test
    public void testCreateComputerObject() throws SQLException {
        // Bean pattern - Not implemented anymore. Only builder pattern left.
        /* ComputerDTO c1 = new ComputerDTO("Test Create Bean");
        c1.setComputerId(Long.valueOf(5));
        c1.setCompanyId(Long.valueOf(8));
        assertNotNull(c1);*/

        // Telescope pattern - Not implemented anymore. Only builder pattern left.
        /* ComputerDTO c2 = new ComputerDTO("Test Create Telescope Computer", null, null, Long.valueOf(5));
        assertNotNull(c2); */

        // Builder pattern
        ComputerDTO c3 = new ComputerDTO.ComputerBuilder("Test Create Build Computer").id(Long.valueOf(5)).build();
        assertNotNull(c3);
    }
}
