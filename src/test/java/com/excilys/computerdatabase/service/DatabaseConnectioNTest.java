package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.dao.impl.CompanyDAO;
import com.excilys.computerdatabase.persistence.mappers.ResultSetMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring.xml" })
public class DatabaseConnectioNTest {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(DatabaseConnectioNTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CompanyDAO companyDAO;

    @Test
    public void test() {
        try {
            assertNotNull("failed to connect to the database", DataSourceUtils.getConnection(dataSource));
            Statement  statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM computer");
            ArrayList<Computer> computerList = new ArrayList<Computer>();

            while (resultSet.next()) {
                computerList.add(ResultSetMapper.mapperComputer(resultSet));
            }
            LOG.info("Hmmm");
            LOG.info(computerList.toString() + "/n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}