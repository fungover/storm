package org.fungover.storm.fileHandler.re;

import org.fungover.storm.client.HttpParser;
import org.fungover.storm.fileHandler.FormatConverter;
import static org.fungover.storm.fileHandler.re.ResponseCode.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileRequestHandler {

    private String responseCode;

    public FileRequestHandler() {
        responseCode = OK.getCode();
    }

    public FileInfo handleRequest(String request) throws IOException {
        var map = HttpParser.getRequestHeaders(request);
        if (map.get("path").equals("/"))
            map.put("path", "index.html");
        var path = Paths.get(getAbsolutePathToResourceFromContext(map, System.getProperty("os.name")));
        if (Files.notExists(path))
            path = updateToCode404AndPath(map);
        byte[] file = Files.readAllBytes(path);
        return new FileInfo(path, file);
    }

    public byte[][] writeResponse(FileInfo fileInfo) {
        long fileLength = fileInfo.getFile().length;
        Path path = fileInfo.getPath();
        String response = "HTTP/1.1 " + responseCode + " \r\nContent-length:" + fileLength +
                "\r\nContent-type:" + FormatConverter.MIME(path) +
                "\r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes(), fileInfo.getFile()};
    }

    private Path updateToCode404AndPath(Map<String, String> map) {
        Path path;
        responseCode = NOT_FOUND.getCode();
        map.put("path", "404filenotfound.html");
        path = Paths.get(getAbsolutePathToResourceFromContext(map, System.getProperty("os.name")));
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

