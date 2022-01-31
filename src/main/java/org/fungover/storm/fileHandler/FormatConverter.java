package org.fungover.storm.fileHandler;

import java.util.HashMap;
import java.util.Map;

public class FormatConverter {
    private static Map<String, String> subtypeToType = Map.of(
            "jpeg", "image",
            "png", "image",
            "html", "text",
            "csv", "text",
            "css", "text",
            "zip", "application"
    );


    public static String MIME(String input) {
        String[] result = input.split("\\.");
        String subtype = result[1];
        String type = subtypeToType.get(subtype);

        return type + "/" + subtype;
    }
}
