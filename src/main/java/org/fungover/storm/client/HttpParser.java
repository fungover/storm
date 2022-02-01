package org.fungover.storm.client;

import java.util.*;

public class HttpParser {

    private static final List<String> methods = List.of("GET", "POST", "PUT");
    private static final Map<String, String> requestHeaders = new HashMap<>();

    public static Map<String, String> getRequestHeaders(String headers) {
        requestHeaders.clear();
        List<String> lines = headers.lines().toList();

        String[] firstLine = lines.get(0).split(" ");

        if (!validRequest(firstLine))
            return Map.of();
        parseFirstLine(firstLine);
        lines.stream().skip(1).forEach(HttpParser::parseHeader);

        return requestHeaders;
    }

    private static void parseFirstLine(String[] properties) {
        requestHeaders.put("method", properties[0]);
        requestHeaders.put("path", properties[1]);
        requestHeaders.put("HTTP-version", properties[2]);
    }

    private static boolean validRequest(String[] properties) {
        return properties.length == 3 && methods.contains(properties[0]);
    }

    private static void parseHeader(String header) {
        String[] properties = header.split(":");
        requestHeaders.put(properties[0].trim(), properties[1].trim());
    }

}
