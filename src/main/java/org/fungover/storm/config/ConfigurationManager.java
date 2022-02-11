package org.fungover.storm.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    public void readConfigurationFile(String filePath) {
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try (FileReader fileReader = new FileReader(filePath)) {
            while ((i = fileReader.read()) != -1) {
                stringBuffer.append((char) i);
            }
        } catch (IOException e) {
            throw new MyConfigurationException(e);
        }

        JsonNode conf;
        try {
            conf = Json.parse(stringBuffer.toString());
        } catch (JsonProcessingException e) {
            throw new MyConfigurationException("Error parsing the Configuration File", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new MyConfigurationException("Error parsing the Configuration file, internal", e);
        }
    }

    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new MyConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
