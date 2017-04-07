package com.excilys.computerdatabase.persistance;

import java.io.File;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesManager {

    public static FileBasedConfiguration config;

    /**
     * To load the properties file.
     */
    public static void load() {
        final Parameters params = new Parameters();
        final File propertiesFile = new File("resources/database.properties");
        System.out.print("Path de mon database.properties is: " + propertiesFile.getAbsolutePath());
        final FileBasedConfigurationBuilder<
        FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.fileBased().setFile(propertiesFile));
        try {
            PropertiesManager.config = builder.getConfiguration();
        } catch (final ConfigurationException e) {
            e.printStackTrace();
        }
    }

}