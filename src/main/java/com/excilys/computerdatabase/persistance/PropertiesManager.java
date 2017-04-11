package com.excilys.computerdatabase.persistance;

import java.io.File;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.LoggerFactory;

public class PropertiesManager {

    public static FileBasedConfiguration config;

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(PropertiesManager.class);

    /**
     * To load the properties file.
     */
    public static void load() {
        final Parameters params = new Parameters();
        //final File propertiesFile = new File("resources/database.properties");
        final File propertiesFile = new File("resources/hikaridatabase.properties");

        LOG.info("Path de mon database.properties is: " + propertiesFile.getAbsolutePath());
        final FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class).configure(params.fileBased().setFile(propertiesFile));
        try {
            PropertiesManager.config = builder.getConfiguration();
        } catch (final ConfigurationException e) {
            e.printStackTrace();
        }
    }

}