package org.fungover.storm.fileHandler;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FormatConverter {

    private FormatConverter() {
    }

    private static final Map<String, MIME> fileEndingToMIME = new HashMap<>();

    static {
        fileEndingToMIME.put("jpeg", new MIME("image", "jpeg"));
        fileEndingToMIME.put("jpg", new MIME("image", "jpeg"));
        fileEndingToMIME.put("png", new MIME("image", "png"));
        fileEndingToMIME.put("html", new MIME("text", "html"));
        fileEndingToMIME.put("htm", new MIME("text", "html"));
        fileEndingToMIME.put("csv", new MIME("text", "csv"));
        fileEndingToMIME.put("css", new MIME("text", "css"));
        fileEndingToMIME.put("zip", new MIME("application", "zip"));
        fileEndingToMIME.put("pdf", new MIME("application", "pdf"));
        fileEndingToMIME.put("tiff", new MIME("image", "tiff"));
        fileEndingToMIME.put("tif", new MIME("image", "tiff"));
    }

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

