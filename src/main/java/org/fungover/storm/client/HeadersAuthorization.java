package org.fungover.storm.client;

import java.util.Base64;
import java.util.Map;

public class HeadersAuthorization {

    private HeadersAuthorization() {}

    public static String[] getUsernameAndPassword(String headers) {
        Map<String, String> headersMap = HttpParser.getRequestHeaders(headers);

        String authValue = headersMap.get("Authorization");

        String[] splitRst = authValue.split(" ");
        String crypted = splitRst[1];

        byte[] decodedBytes = Base64.getDecoder().decode(crypted);
        String decodedString = new String(decodedBytes);

        return decodedString.split(":");
    }
}
