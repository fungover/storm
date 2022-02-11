package org.fungover.storm.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ConfigurationManager {

    private static final Logger LOGGER = LogManager.getLogger(ConfigurationManager.class);
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static void loadConfigurationFile(String filePath) {
        try {
            myCurrentConfiguration = Json.parse(filePath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new MyConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
