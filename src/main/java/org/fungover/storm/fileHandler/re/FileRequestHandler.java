package org.fungover.storm.fileHandler.re;

import org.fungover.storm.client.HttpParser;
import org.fungover.storm.fileHandler.FormatConverter;
import org.fungover.storm.fileHandler.re.FilePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileRequestHandler {
    private static byte[] file;
    private static long filesize;

    private FileRequestHandler() {
    }

    public static void handleRequest(String request) throws IOException {
        var path = FilePath.getInstance();
        var map = HttpParser.getRequestHeaders(request);
        if (map.get("path").equals("/"))
            map.put("path", "index.html");
        path.setPath(getAbsolutePathToResourceFromContext(map, System.getProperty("os.name")));
        file = Files.readAllBytes(path.retreive());
        filesize = file.length;
    }


    public static byte[][] writeResponse() {
        String response = "HTTP/1.1 200 OK \r\nContent-length:" + filesize +
                "Content-type:" + FormatConverter.MIME(FilePath.getInstance().retreive()) +
                "\r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes(), file};
    }

    public static Path getPath() {
        return FilePath.getInstance().retreive();
    }

    private static String getAbsolutePathToResourceFromContext(Map<String, String> map, String context) {
        String absolutePath = System.getProperty("user.dir");
        if (context.contains("Windows"))
            absolutePath = absolutePath.concat("\\webroot\\" + map.get("path"));
        else if (context.contains("OS X")) {
            absolutePath = absolutePath.concat("/webroot/" + map.get("path"));
        } else {
            absolutePath = absolutePath.concat("/webroot/" + map.get("path"));
        }

        return absolutePath;
    }

}

