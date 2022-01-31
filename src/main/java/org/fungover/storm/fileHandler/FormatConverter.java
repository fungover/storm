package org.fungover.storm.fileHandler;

import java.nio.file.Path;
import java.util.Map;

public class FormatConverter {

    private FormatConverter(){}

    private static Map<String, String> subtypeToType = Map.of(
            "jpeg", "image",
            "png", "image",
            "html", "text",
            "csv", "text",
            "css", "text",
            "zip", "application",
            "pdf", "application"
    );

    public static String MIME(String file) {
        String[] result = file.split("\\.");
        String subtype = result[result.length - 1];
        String type = subtypeToType.get(subtype);
        return type + "/" + subtype;
    }

    public static String MIME(Path filePath) {
        String path = filePath.toString();
        return MIME(path);
    }
}

