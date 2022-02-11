package org.fungover.storm.fileHandler.re;

import org.fungover.storm.client.*;
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
        Map<String, String> map = HttpParser.getRequestHeaders(request);
        if (map.get("path").equals("/"))
            map.put("path", "index.html");
        var path = Paths.get(getAbsolutePathToResourceFromContext(map, System.getProperty("os.name")));
        if (Files.notExists(path))
            throw new FileNotFoundException(map);
        byte[] file = Files.readAllBytes(path);
        return new FileInfo(path, file);
    }

    public FileInfo handleError(Map<String, String> parsedRequest, String errorFileName, String code) throws IOException {
        var errorFilePath = updateCodeAndPath(parsedRequest, errorFileName, code);
        byte[] file = Files.readAllBytes(errorFilePath);
        return new FileInfo(errorFilePath, file);
    }

    public byte[][] writeResponse(FileInfo fileInfo) {
        long fileLength = fileInfo.getFile().length;
        Path path = fileInfo.getPath();
        String response = "HTTP/1.1 " + responseCode + " \r\nContent-length:" + fileLength +
            "\r\nContent-type:" + FormatConverter.MIME(path) +
            "\r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes(), fileInfo.getFile()};
    }

    public byte[][] writeResponse(FileInfo fileInfo, String responseCode) {
        long fileLength = fileInfo.getFile().length;
        Path path = fileInfo.getPath();
        String response = "HTTP/1.1 " + responseCode + " \r\nContent-length:" + fileLength +
            "\r\nContent-type:" + FormatConverter.MIME(path) +
            "\r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes(), fileInfo.getFile()};
    }

    public byte[][] writeResponse(String responseCode) {
        String response = "HTTP/1.1 " + responseCode +
            " \r\nConnection: Closed\r\n\r\n";
        return new byte[][]{response.getBytes()};
    }

    public Path updateCodeAndPath(Map<String, String> map, String errorFileName, String code) {
        Path path;
        responseCode = code;
        map.put("path", errorFileName);
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

