package org.fungover.storm.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;

public class Json {

    private Json(){}

    public static Configuration parse(String filePath) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(Paths.get(filePath).toFile(), Configuration.class);
    }
}
