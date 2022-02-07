package org.fungover.storm.fileHandler;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public class FormatConverter {

    private FormatConverter() {
    }

    private static final Map<String, MIME> fileEndingToMIME = Map.of(
            "jpeg", new MIME("image", "jpeg"),
            "png", new MIME("image", "png"),
            "html", new MIME("text", "html"),
            "csv", new MIME("text", "csv"),
            "css", new MIME("text", "css"),
            "zip", new MIME("application", "zip"),
            "pdf", new MIME("application", "pdf")
    );

    public static String MIME(String file) {
        String[] result = file.split("\\.");
        String fileEnding = result[result.length - 1];
        MIME mime = Optional.ofNullable(fileEndingToMIME.get(fileEnding))
                .orElse(new MIME("application", "octet-stream"));
        return mime.type + "/" + mime.subtype;
    }

    public static String MIME(Path filePath) {
        return MIME(filePath.toString());
    }
}

class MIME {
    String type;
    String subtype;

    public MIME(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
    }
}

