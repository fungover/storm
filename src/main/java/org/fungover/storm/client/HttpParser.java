package org.fungover.storm.client;

import java.util.*;

public class HttpParser {

    private static final List<String> methods = List.of("GET", "POST", "PUT");


    public static Map<String, String> getRequestHeaders(String headers) {
        List<String> lines = headers.lines().toList();
        String[] firstLine = lines.get(0).split(" ");

        if (!validRequest(firstLine))
            return Map.of();

        Map<String, String> requestHeaders = new HashMap<>(parseFirstLine(firstLine));

        lines.stream().skip(1)
                .map(HttpParser::parseHeader)
                .forEach(header -> requestHeaders.put(header[0], header[1]));

        return requestHeaders;
    }

    private static Map<String, String> parseFirstLine(String[] properties) {
        return Map.of(
                "method", properties[0],
                "path", properties[1],
                "HTTP-version", properties[2]
        );
    }

    private static boolean validRequest(String[] properties) {
        return properties.length == 3 && methods.contains(properties[0]);
    }

    private static String[] parseHeader(String header) {
        String[] properties = header.split(":");
        return new String[]{ properties[0].trim(), properties[1].trim() };
    }

}
