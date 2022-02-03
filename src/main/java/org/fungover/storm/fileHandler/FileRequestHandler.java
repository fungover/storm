package org.fungover.storm.fileHandler;

import org.fungover.storm.client.HttpParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileRequestHandler {
    private static Path path;
    private static String headers;
    private static String response;
    private static byte[] file;
    private static long filesize;

    public static void handleRequest(String request) throws IOException {
        headers += request;
        var map = HttpParser.getRequestHeaders(request);
        if (map.get("path").equals("/"))
            map.put("path", "index.html");
        path = Paths.get(getAbsolutePathToResourceFromContext(map, System.getProperty("os.name")));
        file = Files.readAllBytes(path);
        filesize = file.length;
    }


    public static byte[][] writeResponse() {
        response = "HTTP/1.1 200 OK \r\nContent-length:" + filesize +
                "Content-type:" + FormatConverter.MIME(path) +
                "\r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes(), file};
    }

    public static Path getPath(){
        return path;
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
