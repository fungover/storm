package org.fungover.storm.filehandler;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FormatConverter {

    private FormatConverter() {
    }

    private static final String IMAGE = "image";
    private static final String TEXT = "text";
    private static final String APPLICATION = "application";
    private static final Map<String, MIME> fileEndingToMIME = new HashMap<>();

    static {
        fileEndingToMIME.put("jpeg", new MIME(IMAGE, "jpeg"));
        fileEndingToMIME.put("jpg", new MIME(IMAGE, "jpeg"));
        fileEndingToMIME.put("png", new MIME(IMAGE, "png"));
        fileEndingToMIME.put("html", new MIME(TEXT, "html"));
        fileEndingToMIME.put("htm", new MIME(TEXT, "html"));
        fileEndingToMIME.put("csv", new MIME(TEXT, "csv"));
        fileEndingToMIME.put("css", new MIME(TEXT, "css"));
        fileEndingToMIME.put("zip", new MIME(APPLICATION, "zip"));
        fileEndingToMIME.put("pdf", new MIME(APPLICATION, "pdf"));
        fileEndingToMIME.put("tiff", new MIME(IMAGE, "tiff"));
        fileEndingToMIME.put("tif", new MIME(IMAGE, "tiff"));
    }

    public static String MIME(String file) {
        String[] result = file.split("\\.");
        String fileEnding = result[result.length - 1];
        MIME mime = Optional.ofNullable(fileEndingToMIME.get(fileEnding))
                .orElse(new MIME(APPLICATION, "octet-stream"));
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


