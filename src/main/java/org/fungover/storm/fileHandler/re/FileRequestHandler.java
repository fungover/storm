package org.fungover.storm.fileHandler.re;

import org.fungover.storm.client.HttpParser;
import org.fungover.storm.fileHandler.FormatConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileRequestHandler {

    private FileRequestHandler() {
    }

    public static FileInfo handleRequest(String request) throws IOException {
        var map = HttpParser.getRequestHeaders(request);
        if (map.get("path").equals("/"))
            map.put("path", "index.html");
        var path = Paths.get(getAbsolutePathToResourceFromContext(map, System.getProperty("os.name")));
        byte[] file = Files.readAllBytes(path);
        return new FileInfo(path, file);
    }

    public static byte[][] writeResponse(FileInfo fileInfo) {
        long fileLength = fileInfo.getFile().length;
        Path path = fileInfo.getPath();
        String response = "HTTP/1.1 200 OK \r\nContent-length:" + fileLength +
                "\r\nContent-type:" + FormatConverter.MIME(path) +
                "\r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes(), fileInfo.getFile()};
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

