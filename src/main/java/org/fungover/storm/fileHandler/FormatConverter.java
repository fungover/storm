package org.fungover.storm.fileHandler;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class FormatConverter {

    private FormatConverter() {
    }

    private static final Map<String, String> subtypeToType = Map.of(
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
        Optional<String> type = Optional.ofNullable(subtypeToType.get(subtype));
        if (type.isEmpty()) {
            type = Optional.of("application");
            subtype = "octet-stream";
        }

        return type.get() + "/" + subtype;
    }

    public static String MIME(Path filePath) {
        String path = filePath.toString();
        return MIME(path);
    }
}

